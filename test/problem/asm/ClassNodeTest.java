package problem.asm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wrightjt on 1/12/2016.
 */
public class ClassNodeTest {

    private ClassNode classNode;
    private ClassNode classNodeWithType;

    @Before
    public void setUp() throws Exception {
        this.classNode = new ClassNode("ClassNodeName");
        this.classNodeWithType = new ClassNode("ClassNodeName", "type");
    }

    @After
    public void tearDown() throws Exception {
        this.classNode = null;
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("ClassNodeName", this.classNode.getName());
    }

    @Test
    public void testGetType() throws Exception {
        assertEquals(null, this.classNode.getType());
        assertEquals("type", this.classNodeWithType.getType());
    }

    @Test
    public void testGetMethods() throws Exception {
        assertEquals(0, this.classNode.getMethods().size());
    }

    @Test
    public void testGetFields() throws Exception {
        assertEquals(0, this.classNode.getMethods().size());
    }

    @Test
    public void testAddField() throws Exception {
        this.classNode.addField(new NodeField("name", "String"));
        assertEquals(1, this.classNode.getFields().size());
    }

    @Test
    public void testAddMethod() throws Exception {
//        this.classNode.addMethod(new NodeMethod("name", "void", null, "public", ""));
//        assertEquals(1, this.classNode.getMethods().size());
    }
}