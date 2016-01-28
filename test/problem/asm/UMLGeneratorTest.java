package problem.asm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by Jeremy on 1/28/2016.
 */
public class UMLGeneratorTest {

    String inputFile;
    String outputFile;

    UMLGenerator umlGenerator;

    @Before
    public void setUp() throws Exception {
        inputFile = "./src/AdapterTestClass";
        outputFile = "input_output/test.dot";
        umlGenerator=  new UMLGenerator(outputFile, inputFile);
        umlGenerator.generateClassList();

    }

    @After
    public void tearDown() throws Exception {
        inputFile = null;
        outputFile = null;
        umlGenerator = null;
    }

    @Test
    public void testClassListSize() throws Exception {
        assertEquals(3, umlGenerator.getClasses().size());
    }

    @Test
    public void testClassNames() throws Exception {
        HashSet<String> classNames = new HashSet<>();
        classNames.add("AdapterTestClass.Adaptee");
        classNames.add("AdapterTestClass.Adapter");
        classNames.add("AdapterTestClass.Template");

        for(String className : umlGenerator.getClasses()) {
            assertTrue(classNames.contains(className));
        }
    }

    @Test
    public void emptyNodeList() {
        assertTrue(umlGenerator.getNodes().isEmpty());
    }

    @Test
    public void fillNodeList() throws Exception {
        umlGenerator.generateNodes();
        assertFalse(umlGenerator.getNodes().isEmpty());
    }

    @Test
    public void nodeListClassListSameSize() throws Exception {
        umlGenerator.generateNodes();
        assertEquals(umlGenerator.getNodes().size(), umlGenerator.getClasses().size());
    }

    @Test
    public void UMLGeneratorIntegrationTest() throws Exception {
        umlGenerator.generateNodes();
        umlGenerator.write();

        InputStream stream = new FileInputStream(outputFile);
        Scanner scanner = new Scanner(stream);

        String s = "";
        while(scanner.hasNextLine()) {
            s += scanner.nextLine() + "\n";
        }
    }
}