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
public class DecoratorTest {
	private static final String parameter = "\\<\\<Decorator\\>\\>";

	@Before
	public void setUp() throws Exception {
		//do nothing
	}

	@After
	public void tearDown() throws Exception {
		//do nothing
	}

	private boolean runTest(String toTest, Set<String> expected) {
		UMLGenerator u;
		try {
			u = TestHelper.setUp(toTest);
		} catch (Exception e) {
			return false;
		}
		List<ClassNode> l = u.getNodes();
		Set<String> ss = new HashSet<String>();
		for(ClassNode n : l){
			if(n.getPatternIdentifier().equals(parameter)){
				ss.add(n.getName());
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
		expected.add("");
		
		assertTrue(runTest(toTest,expected));
	}
	
	@Test
	public void testl5() throws Exception {
		String toTest = "configurations/lab_2_1.json";
		Set<String> expected = new HashSet<String>();
		expected.add("FileInputStream");
		expected.add("FileOutputStream");
		expected.add("EncryptedInputStream");
		expected.add("EncryptedOutputStream");
		
		assertTrue(runTest(toTest,expected));
	}
	
	@Test
	public void testosw() throws Exception {
		String toTest = "configurations/testosw.json";
		Set<String> expected = new HashSet<String>();
		expected.add("");
		
		assertTrue(runTest(toTest,expected));
	}
	
	@Test
	public void testma() throws Exception {
		String toTest = "configurations/testma.json";
		Set<String> expected = new HashSet<String>();
		expected.add("");
		
		assertTrue(runTest(toTest,expected));
	}
}