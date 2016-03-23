
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

public class Test {

	@org.junit.Test
	public void depPresBasictest() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t2.add(new Attribute("b"));
		
		fds.add(new FunctionalDependency(t1,new Attribute("a")));

		// tables
		// a
		// b
		// fds
		// a -> a
		assertTrue(FDChecker.checkDepPres(t1, t2, fds));
		
		
		fds.add(new FunctionalDependency(t1, new Attribute("b")));
		// tables
		// a
		// b
		// fds
		// a -> a
		// a -> b
		assertFalse(FDChecker.checkDepPres(t1, t2, fds));
	}

	@org.junit.Test
	public void losslessBasictest() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t2.add(new Attribute("b"));
		
		// tables
		// a
		// b
		// fds
		assertFalse(FDChecker.checkLossless(t1, t2, fds));
		
		t1.add(new Attribute("b"));
		// tables
		// a b
		// b
		// fds
		assertTrue(FDChecker.checkLossless(t1, t2, fds));
	}
	
	@org.junit.Test
	public void depPresFDtest() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t1.add(new Attribute("b"));
		t2.add(new Attribute("b"));
		
		fds.add(new FunctionalDependency(t1,new Attribute("b")));

		// tables
		// a b
		// b
		// fds
		// ab -> b
		assertTrue(FDChecker.checkDepPres(t1, t2, fds));
		
		
		fds.add(new FunctionalDependency(t2, new Attribute("a")));
		// tables
		// a b
		// b
		// fds
		// ab -> b
		// b -> a
		assertTrue(FDChecker.checkDepPres(t1, t2, fds));
	}

	@org.junit.Test
	public void losslesstest() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t1.add(new Attribute("b"));
		t2.add(new Attribute("b"));
		t2.add(new Attribute("c"));
		t2.add(new Attribute("d"));
		
		AttributeSet temp = new AttributeSet();
		temp.add(new Attribute("b"));
		fds.add(new FunctionalDependency(temp,new Attribute("c")));
		// tables
		// a b
		// b c d
		// fds
		// b -> c
		assertFalse(FDChecker.checkLossless(t1, t2, fds));
		
		fds.add(new FunctionalDependency(temp, new Attribute("d")));
		// tables
		// a b
		// b c d
		// fds
		// b -> c
		// b -> d
		assertTrue(FDChecker.checkLossless(t1, t2, fds));
	}
	
	@org.junit.Test
	public void testSuccess() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		
		AttributeSet fd1 = new AttributeSet();
		fd1.add(new Attribute("a"));
		AttributeSet fd2 = new AttributeSet();
		fd2.add(new Attribute("c"));
		fd2.add(new Attribute("d"));
		AttributeSet fd3 = new AttributeSet();
		fd3.add(new Attribute("b"));
		AttributeSet fd4 = new AttributeSet();
		fd4.add(new Attribute("e"));		
		
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t1.add(new Attribute("b"));
		t1.add(new Attribute("c"));
		t2.add(new Attribute("c"));
		t2.add(new Attribute("d"));
		t2.add(new Attribute("e"));

		fds.add(new FunctionalDependency(fd1, new Attribute("b")));
		fds.add(new FunctionalDependency(fd1, new Attribute("c")));
		fds.add(new FunctionalDependency(fd2, new Attribute("e")));
		fds.add(new FunctionalDependency(fd3, new Attribute("d")));
		fds.add(new FunctionalDependency(fd4, new Attribute("a")));
		
		System.out.println(FDChecker.checkLossless(t1, t2, fds));
	}
	
	@org.junit.Test
	public void testSmallFail() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		
		AttributeSet fd1 = new AttributeSet();
		fd1.add(new Attribute("p"));	
		
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("s"));
		t1.add(new Attribute("p"));
		t2.add(new Attribute("p"));
		t2.add(new Attribute("d"));

		fds.add(new FunctionalDependency(fd1, new Attribute("d")));
		
		System.out.println(FDChecker.checkLossless(t1, t2, fds));
	}
}
