package problem.asm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import problem.asm.AdapterDetector;
import problem.asm.ClassNode;
import problem.asm.FileGenerator;
import problem.asm.UMLGenerator;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Jeremy on 1/27/2016.
 */
public class AdapterTest {

    private FileGenerator umlGenerator;
    private List<ClassNode> adapters;
    private HashSet<String> set;
    private HashSet<String> testSet;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        umlGenerator = null;
        adapters = null;

    }

    @Test
    public void testAdapter() throws Exception {

        this.set = new HashSet<>();
        this.testSet = new HashSet<>();
        this.testSet.add("AdapterTestClass/Adapter");


        String input = "./src/AdapterTestClass";
        String output = "input_output/new_file.dot";

        umlGenerator = new UMLGenerator(output, input);
        umlGenerator = new AdapterDetector(umlGenerator);

        umlGenerator.generateClassList();
        umlGenerator.generateNodes();
        umlGenerator.getNodes();
//        umlGenerator.write();

        for(ClassNode node : umlGenerator.getNodes()) {
            if(node.getPatternIdentifier() != null) {
                if (node.getPatternIdentifier().equals("\\<\\<Adapter\\>\\>")) {
                    this.set.add(node.getName());
                }
            }
        }

//        assertEquals(1, this.set.size());

        for(String node : this.set) {
            assertTrue(this.testSet.contains(node));
        }

        for(String node : this.testSet) {
            assertTrue(this.set.contains(node));
        }

    }
}