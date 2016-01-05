package problem.asm;

import TestRunner.Dog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wrightjt on 1/5/2016.
 */
public class DesignParserTest {

    private dotClass dClass;
    private List<String> testArgs;

    @Before
    public void setUp() throws Exception {
        dClass = new dotClass("TestName", new ArrayList<>(), new ArrayList<>());
        testArgs = new ArrayList<>();
        testArgs.add("OneArg");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFileReading() {
        assertEquals(2, DesignParser.getListOfFiles("./src/TestRunner", "TestRunner").size());
    }

    @Test
    public void testClass() throws IOException {
        assertEquals("TestName", dClass.getName());
    }

    @Test
    public void methodTests() {

        dotMethod dMethod = new dotMethod("public", "void", "method", testArgs);
        dClass.addMethod(dMethod);
        assertEquals(1, dClass.getMethods().size());
        assertEquals("OneArg", dClass.getMethods().get(dClass.getMethods().size() - 1).getArgs().get(0));
    }

    @Test
    public void fieldTests() {
        dotField dField = new dotField("void", "bestField");
        dClass.addField(dField);
        assertEquals("bestField", dField.getName());
    }

    @Test
    public void testClassOutput() {
        assertEquals("node [shape = \"record\" ]\nClassTTestName [label = \"{TestName||}\" ]", dClass.dotString());
    }

    @Test
    public void testExtends() {
        dotExtends dExtends = new dotExtends("Small", "Super");
        assertEquals("edge [arrowhead = \"empty\" style = solid ]\nClassTSuper -> ClassTSmall", dExtends.dotString());
    }
}