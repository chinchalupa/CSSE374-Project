package problem.asm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wrightjt on 1/13/2016.
 */
public class NodeFieldTest {

    private NodeField nodeField;

    @After
    public void tearDown() throws Exception {
        this.nodeField = null;
    }

    @Before
    public void setUp() throws Exception {
        this.nodeField = new NodeField("Name", "void", "");

    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Name", this.nodeField.getName());
    }

    @Test
    public void testGetReturnType() throws Exception {
        assertEquals("void", this.nodeField.getReturnType());
    }
}