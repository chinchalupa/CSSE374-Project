package problem.asm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import problem.asm.file_elements.INode;
import problem.asm.structures.FileGenerator;

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

		FileGenerator u;
		try {
			u = TestHelper.setUp(toTest);
//			System.out.println("SETUP");
		} catch (Exception e) {
			return false;
		}
		List<INode> l = u.updateNodes();
		System.out.println(l.size());
		Set<String> ss = new HashSet<>();
		for(INode node : l){
			for(String identifier : node.getPatternIdentifier()) {
				if(identifier.equals(parameter)) {
//					System.out.println("TEST NAME: " + node.getName());
					ss.add(node.getName());
				}
			}
		}
		System.out.println(ss.size() + " " + expected.size());
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
//		expected.add("");
		
		assertTrue(runTest(toTest,expected));
	}
	
	@Test
	public void testl5() throws Exception {
		String toTest = "configurations/lab_2_1.json";
		Set<String> expected = new HashSet<String>();
		expected.add("java/io/FilterInputStream");
		expected.add("java/io/FilterOutputStream");
		expected.add("lab21/EncryptionOutputStream");
		expected.add("lab21/DecryptionInputStream");
		
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
//		expected.add("");
		
		assertTrue(runTest(toTest,expected));
	}
}