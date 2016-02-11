package problem.asm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by Jeremy on 1/27/2016.
 */
public class DecoratorDetectorTest {

    private FileGenerator umlGenerator;
    private List<ClassNode> adapters;
    private HashSet<String> set;
    private HashSet<String> testSet;

    private String output;
    private String input;

    @Before
    public void setUp() throws Exception {
        output = "input_output/new_file.dot";
        input = "./src/problem/asm";
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDecorator() throws Exception {
        this.set = new HashSet<>();
        this.testSet = new HashSet<>();
        this.testSet.add("problem/asm/DecoratorDetector");
        this.testSet.add("problem/asm/SingletonDetector");
        this.testSet.add("problem/asm/UMLDecorator");
        this.testSet.add("problem/asm/AdapterDetector");

        umlGenerator = new UMLGenerator(output, input);
        umlGenerator = new DecoratorDetector(umlGenerator);

        umlGenerator.generateClassList();
        umlGenerator.generateNodes();
        umlGenerator.updateNodes();
        umlGenerator.write();

        for(INode node : umlGenerator.updateNodes()) {
            if(node.getPatternIdentifier() != null) {
                if (node.getPatternIdentifier().equals("\\<\\<Decorator\\>\\>")) {
                    this.set.add(node.getName());
                    System.out.println(node.getName());
                }
            }
        }

        for(String node : this.set) {
            assertTrue(this.testSet.contains(node));
        }

        for(String node : this.testSet) {
            assertTrue(this.set.contains(node));
        }
    }

    @Test
    public void testForDecoratorInOutput() throws Exception {

        Config.newInstance("configurations/our_project.json");

        umlGenerator = new UMLGenerator();
        umlGenerator.generateClassList();
        umlGenerator.generateNodes();
        umlGenerator.updateNodes();
        umlGenerator.write();

        InputStream stream = new FileInputStream(output);
        Scanner scanner = new Scanner(stream);

        String s = "";
        while(scanner.hasNextLine()) {
            s += scanner.nextLine() + "\n";
        }
        System.out.println(s);

        int index = s.indexOf("\\<\\<Decorator\\>\\>");
        int count = 0;
        while(index != -1) {
            count++;
            s = s.substring(index + 1);
            index = s.indexOf("\\<\\<Decorator\\>\\>");
        }

        assertEquals(4, count);
    }
}