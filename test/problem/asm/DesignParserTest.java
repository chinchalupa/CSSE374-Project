package problem.asm;

import org.junit.After;
import org.junit.Before;

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

    public void testFileReading() {
        assertEquals(9, DesignParser.getListOfFiles().size());
    }

    public void testName() {

    }
}