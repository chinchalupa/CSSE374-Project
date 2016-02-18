package problem.asm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import problem.asm.file_elements.NodeMethod;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wrightjt on 1/13/2016.
 */
public class NodeMethodTest {

    private NodeMethod nodeMethod;
    private List<String> arguments;

    @Before
    public void setUp() throws Exception {
        this.arguments = new ArrayList<>();
        this.arguments.add("String");
        this.arguments.add("Integer");
        this.nodeMethod = new NodeMethod("testMethod", "void", this.arguments, "public", null);
    }

    @After
    public void tearDown() throws Exception {
        this.nodeMethod = null;
        this.arguments = null;
    }

    @Test
    public void testGetArgs() throws Exception {
        List<String> arguments = this.nodeMethod.getArgs();
        assertEquals(2, arguments.size());
    }

    @Test
    public void testGetSecurity() throws Exception {
        assertEquals("public", this.nodeMethod.getSecurity());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("testMethod", this.nodeMethod.getName());
    }

    @Test
    public void testGetReturnType() throws Exception {
        assertEquals("void", this.nodeMethod.getReturnType());
    }
}