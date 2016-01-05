package problem.asm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wrightjt on 1/5/2016.
 */
public class DesignParserTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFileReading() {
        assertEquals(2, DesignParser.getListOfFiles("./src/TestRunner", "TestRunner").size());
    }
//
    public void testName() {

    }
}