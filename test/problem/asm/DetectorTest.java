//package problem.asm;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
///**
// * Created by hoffmaj2 on 1/12/2016.
// */
//public class DetectorTest {
//
//    private FileGenerator umlGenerator;
//    private List<ClassNode> decorators;
//
//    @Before
//    public void setUp() throws Exception {
//        String readClass = "input_output/DecoratorTestDir";
//        String saveLocation = "input_output/new_file.dot";
//        umlGenerator = new UMLGenerator(saveLocation, readClass);
//
//        umlGenerator = new DecoratorDetector(umlGenerator);
//
//        umlGenerator.generateClassList();
//        umlGenerator.generateNodes();
//
//        decorators = new ArrayList<ClassNode>();
//        for(ClassNode cn : umlGenerator.getNodes()){
//        	if(cn.getPatternIdentifier().equals("Decorator")){
//        		decorators.add(cn);
//        	}
//        }
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        //nothing useful to do yet
//    }
//
//    @Test
//    public void testListOfdecoratorsInPackage() throws Exception {
//    	HashSet<String> expected = new HashSet<String>();
//    	//the set of strings representing names of singleton nodes we expect to see
//    	expected.add("SampleDecorator");
//    	//can add as many nodes as needed
//
//    	HashSet<String> actual = new HashSet<String>();
//        for(ClassNode cn : decorators){
//        	actual.add(cn.getName());
//        }
//
//        for(String n : actual){
//        	assertEquals(true, expected.contains(n));
//        }//make sure everything we detect is expected
//
//        for(String n : expected){
//        	assertEquals(true, actual.contains(n));
//        }//make sure we expect is detected
//    }
//}