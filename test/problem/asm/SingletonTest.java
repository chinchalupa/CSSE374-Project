package problem.asm;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hoffmaj2 on 1/12/2016.
 */
public class SingletonTest {

    private FileGenerator umlGenerate;
    private List<INode> singletons;
    private HashSet<String> expected;

    @Before
    public void setUp() throws Exception {
        expected = new HashSet<String>();


    }

    @After
    public void tearDown() throws Exception {
        //nothing useful to do yet
        umlGenerate = null;
        singletons = null;
    }

    /**
     * Test for eager singleton
     * @throws Exception
     */
    @Test
    public void testListOfSingletonsInPackage() throws Exception {

        String readClass = "configurations/singleton_test.json";

        Config.newInstance(readClass);

        umlGenerate = new UMLGenerator();


        umlGenerate.generateClassList();
        umlGenerate.generateNodes();
        umlGenerate.updateNodes();
        umlGenerate.write();

        singletons = new ArrayList<>();
        for(INode cn : umlGenerate.updateNodes()){
            System.out.println(cn.getName());
            for(String pattern : cn.getPatternIdentifier()) {
                if(pattern.equals("\\<\\<Singleton\\>\\>")) {
                    singletons.add(cn);
                }
            }
        }
        //the set of strings representing names of singleton nodes we expect to see
        expected.add("SingletonTestClass/EagerSingleton");
        //can add as many nodes as needed


        HashSet<String> actual = new HashSet<>();
        for(INode cn : singletons){
            actual.add(cn.getName());
        }

        for(String n : actual){
            assertEquals(true, expected.contains(n));
        }//make sure everything we detect is expected

        for(String n : expected){
            assertEquals(true, actual.contains(n));
        }//make sure we expect is detected
    }

    @Test
    public void testOurCode() throws Exception {
        HashSet<String> actual = new HashSet<>();

        singletons = new ArrayList<>();

        String jsonFile = "configurations/our_project.json";
        Config.newInstance(jsonFile);
        umlGenerate = new UMLGenerator();

        umlGenerate.generateClassList();
        umlGenerate.generateNodes();

        umlGenerate.updateNodes();
        umlGenerate.write();

        singletons = new ArrayList<>();
        for(INode cn : umlGenerate.updateNodes()){
            System.out.println(cn.getName());
            if(cn.getPatternIdentifier() != null) {
                for(String pattern : cn.getPatternIdentifier()) {
                    if(pattern.equals("\\<\\<Singleton\\>\\>")) {
                        singletons.add(cn);
                    }
                }
            }
        }

        assertEquals(1, singletons.size());

    }


    @Test
    public void testRuntime() throws Exception {

//        String readClass = "java.lang.Runtime";
//        String saveLocation = "input_output/runtime.dot";
        String jsonFile = "configurations/runtime.json";

        Config.newInstance(jsonFile);

        umlGenerate = new UMLGenerator();

        umlGenerate.generateClassList();
        umlGenerate.generateNodes();
        umlGenerate.updateNodes();
        umlGenerate.write();

        INode node = umlGenerate.updateNodes().get(0);
        boolean containsSingleton = false;
        for(String s : node.getPatternIdentifier()) {
            if(s.equals("\\<\\<Singleton\\>\\>")) {
                containsSingleton = true;
            }
        }
        assertTrue(containsSingleton);

        umlGenerate.write();
    }

    @Test
    public void testDesktop() throws Exception {

        String jsonFile = "configurations/desktop.json";

        Config.newInstance(jsonFile);

        umlGenerate = new UMLGenerator();

        umlGenerate = new SingletonDetector(umlGenerate);

        umlGenerate.generateClassList();
        umlGenerate.generateNodes();

        INode node = umlGenerate.updateNodes().get(0);
        assertTrue(node.getPatternIdentifier().size() == 0);

        umlGenerate.write();
    }

    @Test
    public void testCalendar() throws Exception {
        String readClass = "java.util.Calendar";
        String saveLocation = "input_output/calendar.dot";

        umlGenerate = new UMLGenerator(saveLocation, readClass);

        umlGenerate = new SingletonDetector(umlGenerate);

        umlGenerate.generateClassList();
        umlGenerate.generateNodes();

        INode node = umlGenerate.updateNodes().get(0);
        assertEquals(0, node.getPatternIdentifier().size());

        umlGenerate.write();
    }

    @Test
    public void testFilterInputStream() throws Exception {

        String readClass = "java.io.FilterInputStream";
        String saveLocation = "input_output/filterInputStream.dot";

        umlGenerate = new UMLGenerator(saveLocation, readClass);

        umlGenerate = new SingletonDetector(umlGenerate);

        umlGenerate.generateClassList();
        umlGenerate.generateNodes();

        INode node = umlGenerate.updateNodes().get(0);
        assertEquals(0, node.getPatternIdentifier().size());

        umlGenerate.write();
    }

}