package problem.asm;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Jeremy on 2/17/2016.
 */
public class ConfigTest {

    @Test
    public void testRecurseFilesInDirectory() throws Exception {
        Config config = Config.newInstance("configurations/our_project.json");
        List<File> files = config.recurseFilesInDirectory(new File("./src/problem"), new ArrayList<>());
        assertEquals(54, files.size());
    }

    @Test
    public void testExcludes() throws Exception {
        Config config = Config.newInstance("configurations/excludes_test.json");
        List<String> allowedClasses = config.getFormattedListOfClasses();
        assertEquals(3, allowedClasses.size());
    }

    @Test
    public void testAddAndRemove() throws Exception {
        Config config = Config.newInstance("configurations/excludes_test.json");
        config.addToStringList("excludes", "composite.Item");
        assertEquals(2, config.getFormattedListOfClasses().size());
        assertEquals(2, config.getStringList("excludes").size());
        config.removeFromStringList("excludes", "composite.Item");
        assertEquals(3, config.getFormattedListOfClasses().size());
        assertEquals(1, config.getStringList("excludes").size());
    }

    @Test
    public void testAddAndRemoveNoExclude() throws Exception {
        Config config = Config.newInstance("configurations/no_excludes_test.json");
        config.addToStringList("excludes", "composite.Item");
        config = Config.newInstance("configurations/no_excludes_test.json");

        assertEquals(3, config.getFormattedListOfClasses().size());
        assertEquals(1, config.getStringList("excludes").size());

        config.addToStringList("excludes", "composite.CompositeItem");
        config = Config.newInstance("configurations/no_excludes_test.json");

        assertEquals(2, config.getFormattedListOfClasses().size());
        assertEquals(2, config.getStringList("excludes").size());

        config.removeListFromStringList("excludes", config.getStringList("excludes"));

    }
}