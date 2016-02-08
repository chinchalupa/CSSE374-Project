package problem.asm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by jared on 2/2/2016.
 */
public class CompositeTest {
	private static final String parameter = "\\<\\<Composite\\>\\>";

	@Before
	public void setUp() throws Exception {
		//do nothing
	}

	@After
	public void tearDown() throws Exception {
		//do nothing
	}

	private boolean runTest(String toTest, Set<String> expected) {
		FileGenerator u;
		try {
			u = TestHelper.setUp(toTest);
		} catch (Exception e) {
			return false;
		}
		List<INode> l = u.updateNodes();
		Set<String> ss = new HashSet<String>();
		for(INode node : l){
			for(String identifier : node.getPatternIdentifier()) {
				if(identifier.equals(parameter)) {
					ss.add(node.getName());
				}
			}
		}
		for(String s : ss){
			assertTrue(expected.contains(s));
		}
		for(String s : expected){
			assertTrue(ss.contains(s));
		}
		return true;
	}
	
	@Test
	public void testlisr() throws Exception {
		String toTest = "configurations/testisr.json";
		Set<String> expected = new HashSet<String>();

		assertTrue(runTest(toTest,expected));
	}
	
	@Test
	public void testl5() throws Exception {
		String toTest = "configurations/lab_2_1.json";
		Set<String> expected = new HashSet<String>();

		assertTrue(runTest(toTest,expected));
	}
	
	@Test
	public void testosw() throws Exception {
		String toTest = "configurations/testosw.json";
		Set<String> expected = new HashSet<String>();

		assertTrue(runTest(toTest,expected));
	}
	
	@Test
	public void testma() throws Exception {
		String toTest = "configurations/testma.json";
		Set<String> expected = new HashSet<String>();

		assertTrue(runTest(toTest,expected));
	}
}