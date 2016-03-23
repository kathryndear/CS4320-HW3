import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ClosureTest {

	@Test
	public void test_basic() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		AttributeSet t3 = new AttributeSet();
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t2.add(new Attribute("b"));
		t3.add(new Attribute("b"));
		t3.add(new Attribute("c"));

		fds.add(new FunctionalDependency(t1, new Attribute("a")));
		fds.add(new FunctionalDependency(t1, new Attribute("b")));
		fds.add(new FunctionalDependency(t2, new Attribute("c")));
		fds.add(new FunctionalDependency(t3, new Attribute("d")));
		
		AttributeSet temp = new AttributeSet();
		temp = FDChecker.closure(t1, fds);
		for (Attribute att : temp) {
			System.out.println(att.name);
		}
		
	}

}
