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
		//your code here
		//Lossless decompositions do not lose information, the natural join is equal to the 
		//original table.
		//a decomposition is lossless if the common attributes for a superkey for one of the
		//tables.
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
}
