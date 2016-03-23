import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class DepPreserveTest {

	@Test
	public void test_classexample() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		AttributeSet t3 = new AttributeSet();
		AttributeSet t4 = new AttributeSet();
		AttributeSet t5 = new AttributeSet();
		
		t1.add(new Attribute("C"));
		t1.add(new Attribute("S"));
		t1.add(new Attribute("J"));
		t1.add(new Attribute("D"));
		t1.add(new Attribute("Q"));
		t1.add(new Attribute("V"));
		
		t2.add(new Attribute("S"));
		t2.add(new Attribute("D"));
		t2.add(new Attribute("P"));
		
		t3.add(new Attribute("J"));
		t3.add(new Attribute("P"));
		
		t4.add(new Attribute("S"));
		t4.add(new Attribute("D"));
		
		t5.add(new Attribute("C"));
		t5.add(new Attribute("S"));
		t5.add(new Attribute("J"));
		t5.add(new Attribute("D"));
		t5.add(new Attribute("P"));
		t5.add(new Attribute("Q"));
		t5.add(new Attribute("V"));
		
		
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		fds.add(new FunctionalDependency(t3, new Attribute("C")));
		fds.add(new FunctionalDependency(t4, new Attribute("P")));
		fds.add(new FunctionalDependency(t5, new Attribute("C")));
		fds.add(new FunctionalDependency(t5, new Attribute("S")));
		fds.add(new FunctionalDependency(t5, new Attribute("J")));
		fds.add(new FunctionalDependency(t5, new Attribute("D")));
		fds.add(new FunctionalDependency(t5, new Attribute("P")));
		fds.add(new FunctionalDependency(t5, new Attribute("Q")));
		fds.add(new FunctionalDependency(t5, new Attribute("V")));

		
		assertFalse(FDChecker.checkDepPres(t1, t2, fds));
	}
	
	@Test
	public void test2(){
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		AttributeSet t3 = new AttributeSet();
		AttributeSet t4 = new AttributeSet();
		
		t1.add(new Attribute("FypID"));
		t1.add(new Attribute("StuID"));
		
		t2.add(new Attribute("ProfID"));
		
		t3.add(new Attribute("StuID"));
		t3.add(new Attribute("ProfID"));
		
		t4.add(new Attribute("FypID"));
		
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		fds.add(new FunctionalDependency(t1, new Attribute("ProfID")));
		fds.add(new FunctionalDependency(t2, new Attribute("FypID")));
		
		assertFalse(FDChecker.checkDepPres(t1, t2, fds));
		assertFalse(FDChecker.checkDepPres(t2, t1, fds));
		
		assertFalse(FDChecker.checkDepPres(t3, t4, fds));
		assertFalse(FDChecker.checkDepPres(t4, t3, fds));
	}
	
	@Test
	public void test3(){
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		AttributeSet t3 = new AttributeSet();
		AttributeSet t4 = new AttributeSet();
		AttributeSet a = new AttributeSet();
		AttributeSet b = new AttributeSet();
		AttributeSet c = new AttributeSet();
		AttributeSet d = new AttributeSet();
		
		a.add(new Attribute("A"));
		b.add(new Attribute("B"));
		c.add(new Attribute("C"));
		d.add(new Attribute("D"));
		
		t1.add(new Attribute("A"));
		t1.add(new Attribute("B"));
		t1.add(new Attribute("C"));
		
		t2.add(new Attribute("C"));
		t2.add(new Attribute("D"));
		
		t3.add(new Attribute("A"));
		t3.add(new Attribute("C"));
		t3.add(new Attribute("D"));
		
		t4.add(new Attribute("B"));
		t4.add(new Attribute("C"));
		
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		fds.add(new FunctionalDependency(a, new Attribute("B")));
		fds.add(new FunctionalDependency(b, new Attribute("C")));
		fds.add(new FunctionalDependency(c, new Attribute("D")));
		
		
		assertTrue(FDChecker.checkDepPres(t1, t2, fds));
		assertFalse(FDChecker.checkDepPres(a, t2, fds));
		assertFalse(FDChecker.checkDepPres(t3, t4, fds));
		assertFalse(FDChecker.checkDepPres(t4, t3, fds));
		assertFalse(FDChecker.checkDepPres(a, t2, fds));
		
	}
	
	@Test
	public void test3b(){
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		AttributeSet t3 = new AttributeSet();
		AttributeSet t4 = new AttributeSet();
		AttributeSet a = new AttributeSet();
		AttributeSet b = new AttributeSet();
		AttributeSet c = new AttributeSet();
		
		a.add(new Attribute("A"));
		b.add(new Attribute("B"));
		c.add(new Attribute("C"));
		
		t1.add(new Attribute("A"));
		t1.add(new Attribute("B"));
		t1.add(new Attribute("C"));
		
		t2.add(new Attribute("C"));
		t2.add(new Attribute("D"));
		
		t3.add(new Attribute("A"));
		t3.add(new Attribute("C"));
		t3.add(new Attribute("D"));
		
		t4.add(new Attribute("B"));
		t4.add(new Attribute("C"));
		
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		fds.add(new FunctionalDependency(a, new Attribute("B")));
		fds.add(new FunctionalDependency(b, new Attribute("C")));
		fds.add(new FunctionalDependency(c, new Attribute("D")));
		
		assertTrue(FDChecker.checkDepPres(t2, t1, fds));
	}
	
	@Test
	public void test3c(){
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		AttributeSet t3 = new AttributeSet();
		AttributeSet t4 = new AttributeSet();
		AttributeSet a = new AttributeSet();
		AttributeSet b = new AttributeSet();
		AttributeSet c = new AttributeSet();
		
		a.add(new Attribute("A"));
		b.add(new Attribute("B"));
		c.add(new Attribute("C"));
		
		t1.add(new Attribute("A"));
		t1.add(new Attribute("B"));
		t1.add(new Attribute("C"));
		
		t2.add(new Attribute("C"));
		t2.add(new Attribute("D"));
		
		t3.add(new Attribute("A"));
		t3.add(new Attribute("C"));
		t3.add(new Attribute("D"));
		
		t4.add(new Attribute("B"));
		t4.add(new Attribute("C"));
		
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		fds.add(new FunctionalDependency(a, new Attribute("B")));
		fds.add(new FunctionalDependency(b, new Attribute("C")));
		fds.add(new FunctionalDependency(c, new Attribute("D")));

		assertFalse(FDChecker.checkDepPres(t3, t4, fds));
		assertFalse(FDChecker.checkDepPres(t4, t3, fds));
		assertFalse(FDChecker.checkDepPres(a, t2, fds));

		
	}
	
	@Test
	public void test3d(){
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		AttributeSet t3 = new AttributeSet();
		AttributeSet t4 = new AttributeSet();
		AttributeSet a = new AttributeSet();
		AttributeSet b = new AttributeSet();
		AttributeSet c = new AttributeSet();
		
		a.add(new Attribute("A"));
		b.add(new Attribute("B"));
		c.add(new Attribute("C"));
		
		t1.add(new Attribute("A"));
		t1.add(new Attribute("B"));
		t1.add(new Attribute("C"));
		
		t2.add(new Attribute("C"));
		t2.add(new Attribute("D"));
		
		t3.add(new Attribute("A"));
		t3.add(new Attribute("C"));
		t3.add(new Attribute("D"));
		
		t4.add(new Attribute("B"));
		t4.add(new Attribute("C"));
		
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		fds.add(new FunctionalDependency(a, new Attribute("B")));
		fds.add(new FunctionalDependency(b, new Attribute("C")));
		fds.add(new FunctionalDependency(c, new Attribute("D")));

		assertFalse(FDChecker.checkDepPres(a, t2, fds));
		assertFalse(FDChecker.checkDepPres(t2, a, fds));
		
	}
	
	

}

