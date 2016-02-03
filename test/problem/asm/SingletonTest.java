package problem.asm;

import java.awt.*;
import java.io.FilterInputStream;
import java.util.ArrayList;
import java.util.Calendar;
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
    private List<ClassNode> singletons;
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

        String readClass = "./src/SingletonTestClass";
        String saveLocation = "input_output/new_file.dot";

//        Config config

        umlGenerate = new UMLGenerator(saveLocation, readClass);

        umlGenerate = new SingletonDetector(umlGenerate);

        umlGenerate.generateClassList();
        umlGenerate.generateNodes();

        umlGenerate.getNodes();
        umlGenerate.write();

        singletons = new ArrayList<ClassNode>();
        for(ClassNode cn : umlGenerate.getNodes()){
            System.out.println(cn.getName());
            if(cn.getPatternIdentifier().equals("\\<\\<Singleton\\>\\>")){
                singletons.add(cn);
            }
        }
        //the set of strings representing names of singleton nodes we expect to see
        expected.add("SingletonTestClass/EagerSingleton");
        //can add as many nodes as needed

        HashSet<String> actual = new HashSet<String>();
        for(ClassNode cn : singletons){
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

        String readClass = "./src/problem/asm";
        String saveLocation = "input_output/new_file.dot";

        umlGenerate = new UMLGenerator(saveLocation, readClass);

        umlGenerate = new SingletonDetector(umlGenerate);

        umlGenerate.generateClassList();
        umlGenerate.generateNodes();

        umlGenerate.getNodes();
        umlGenerate.write();

        singletons = new ArrayList<ClassNode>();
        for(ClassNode cn : umlGenerate.getNodes()){
            System.out.println(cn.getName());
            if(cn.getPatternIdentifier() != null) {
                if (cn.getPatternIdentifier().equals("\\<\\<Singleton\\>\\>")) {
                    singletons.add(cn);
                }
            }
        }

        assertEquals(0, singletons.size());
    }


    @Test
    public void testRuntime() throws Exception {

        String readClass = "java.lang.Runtime";
        String saveLocation = "input_output/runtime.dot";

        umlGenerate = new UMLGenerator(saveLocation, readClass);

        umlGenerate = new SingletonDetector(umlGenerate);

        umlGenerate.generateClassList();
        umlGenerate.generateNodes();

        ClassNode node = umlGenerate.getNodes().get(0);
        assertEquals(true, node.getPatternIdentifier().equals("\\<\\<Singleton\\>\\>"));

        umlGenerate.write();
    }

    @Test
    public void testDesktop() throws Exception {

        String readClass = "java.awt.Desktop";
        String saveLocation = "input_output/desktop.dot";

        umlGenerate = new UMLGenerator(saveLocation, readClass);

        umlGenerate = new SingletonDetector(umlGenerate);

        umlGenerate.generateClassList();
        umlGenerate.generateNodes();

        ClassNode node = umlGenerate.getNodes().get(0);
        assertNull(node.getPatternIdentifier());

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

        ClassNode node = umlGenerate.getNodes().get(0);
        assertNull(node.getPatternIdentifier());

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

        ClassNode node = umlGenerate.getNodes().get(0);
        assertNull(node.getPatternIdentifier());

        umlGenerate.write();
    }

}