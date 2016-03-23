import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FDChecker {

	/**
	 * Checks whether a decomposition of a table is dependency
	 * preserving under the set of functional dependencies fds
	 * 
	 * @param t1 one of the two tables of the decomposition
	 * @param t2 the second table of the decomposition
	 * @param fds a complete set of functional dependencies that apply to the data
	 * 
	 * @return true if the decomposition is dependency preserving, false otherwise
	 **/
	public static boolean checkDepPres(AttributeSet t1, AttributeSet t2, Set<FunctionalDependency> fds) {
		//your code here
		//a decomposition is dependency preserving, if local functional dependencies are
		//sufficient to enforce the global properties
		//To check a particular functional dependency a -> b is preserved, 
		//you can run the following algorithm
		//result = a
		//while result has not stabilized
		//	for each table in the decomposition
		//		t = result intersect table 
		//		t = closure(t) intersect table
		//		result = result union t
		//if b is contained in result, the dependency is preserved
		return false;
	}

	/**
	 * Checks whether a decomposition of a table is lossless
	 * under the set of functional dependencies fds
	 * 
	 * @param t1 one of the two tables of the decomposition
	 * @param t2 the second table of the decomposition
	 * @param fds a complete set of functional dependencies that apply to the data
	 * 
	 * @return true if the decomposition is lossless, false otherwise
	 **/
	public static boolean checkLossless(AttributeSet t1, AttributeSet t2, Set<FunctionalDependency> fds) {
		Map<Attribute, Integer> attToInt = new HashMap();	//maps attributes to indexes (columns) of a 2D array
		Map<Integer, Attribute> intToAtt = new HashMap();   //maps indexes (columns) of 2D array to attributes
		AttributeSet combined = new AttributeSet();			//combines the attr of each relation into one set
		combined.addAll(t1);
		combined.addAll(t2);
		
		int i = 0;
		for(Attribute a : combined) {
			attToInt.put(a, i);
			intToAtt.put(i, a);
			i++;
		}
		
		//Array of the two relations
		AttributeSet[] relations = new AttributeSet[2];
		relations[0] = t1;
		relations[1] = t2;
		
		//Table tracking valid attributes. Rows are the decomposed relations. Columns are attributes.
		boolean[][] tableau = new boolean[2][combined.size()];
		
		//For each relation, mark each attribute (column) as true if the attribute is in the relation. 
		for(int r = 0; r < 2; r++) {
			for(Attribute a : relations[r]) {
				tableau[r][attToInt.get(a)] = true;
			}
		}
		
		//This utilizes the CHASE ALGORITHM, see https://en.wikipedia.org/wiki/Chase_(algorithm)
		boolean no_change = false;
		while(!no_change) {
			boolean[][] temp = cloneArray(tableau);
			/*
			 * For each functional dependency:
			 * The following must be true in order to change the cell corresponding to the attribute of 
			 * the RHS of a FD:
			 *	1. Both rows must have true for the columns relating to the LHS of a FD
			 * 	2. Only one row must have true for the columns relating to the RHS of a FD
			 */
			for(FunctionalDependency f : fds) {
				//indexes corresponds to the tableau columns associated with the attributes of the LHS
				//for FD f. 
				ArrayList<Integer> indexes = new ArrayList<Integer>();
				AttributeSet left = f.left;
				Attribute right = f.right;
				for(Attribute a : left) {
					indexes.add(attToInt.get(a));
				}
				
				//If distinguished is true, then we can change the columns associated with the attributes of 
				//the RHS for FD f to true. 
				boolean distinguished = true;
				for(int j = 0; j < indexes.size(); j++) {
					//Check that both rows of the attr columns on the LHS for this FD is true
					distinguished = distinguished && tableau[0][indexes.get(j)] && tableau[1][indexes.get(j)];
				}
				
				//Check that only one of the rows of the attr columns on the RHS for this FD is true
				if((tableau[0][attToInt.get(right)] && !tableau[1][attToInt.get(right)]) || 
				   (!tableau[0][attToInt.get(right)] && tableau[1][attToInt.get(right)])) {
					distinguished = distinguished && true;
				}
				
				//if distinguished is true, then for this FD, we can set the RHS attribute column 
				//to all be true
				if(distinguished) {
					tableau[0][attToInt.get(f.right)] = true;
					tableau[1][attToInt.get(f.right)] = true;
				}
			}
			
			//Continue this process of checking all FDs until the table no longer changes.
			if(equivalent(tableau, temp)) 
				no_change = true;	
		}
		
		//If one of the rows of the table has all true, then the decomposition is lossless. 
		for(int r = 0; r < 2; r++) {
			boolean lossless = true;
			for(int c = 0; c < combined.size(); c++) {
				lossless = lossless && tableau[r][c]; 
			}
			if(lossless)
				return true;
		}
		
		return false;
	}

	//recommended helper method
	//finds the total set of attributes implied by attrs
	public static AttributeSet closure(AttributeSet attrs, Set<FunctionalDependency> fds) {
		AttributeSet closure = new AttributeSet();
		closure = attrs;
		boolean no_change = true;
		while (no_change) {
			boolean closure_changed = false;
			for (FunctionalDependency fd : fds) {
				if(leftInClosure(fd.left, closure)) {
					if(!closure.contains(fd.right)){
						closure.add(fd.right);
						closure_changed = true;
					}
				}
			}
			no_change = closure_changed;
		}
		return closure;
	}
	
	public static boolean leftInClosure(AttributeSet left, AttributeSet closure) {
		for(Attribute latt : left) {
			if(!closure.contains(latt)) {
				return false;
			}
		}
		return true;
	}
	
	public static void printTable(boolean[][] disp, Map<Attribute,Integer> mapper) {
		
		System.out.println("------------------------------------------");
		
		for(Map.Entry<Attribute, Integer> entry : mapper.entrySet()) {
			System.out.print(" [" + entry.getKey() + ", " + entry.getValue() + "] ");
		}
		System.out.println();
		
		for(int r = 0; r < disp.length; r++) {
			for(int c = 0; c < disp[r].length; c++) {
				System.out.print(" [" + disp[r][c] + "]");
			}
			System.out.println();
		}
		
		System.out.println("------------------------------------------");
	}
	
	public static boolean equivalent(boolean[][] tb1, boolean[][] tb2) {
		for(int r = 0; r < tb1.length; r++) {
			for(int c = 0; c < tb1[r].length; c++) {
				if(tb1[r][c] != tb2[r][c])
					return false;
			}
		}
		return true;
	}
	
	public static boolean[][] cloneArray(boolean[][] orig) {
	    int length = orig.length;
	    boolean[][] target = new boolean[length][orig[0].length];
	    for (int i = 0; i < length; i++) {
	        System.arraycopy(orig[i], 0, target[i], 0, orig[i].length);
	    }
	    return target;
	}
}
