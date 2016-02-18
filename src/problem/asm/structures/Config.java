package problem.asm.structures;

import com.sun.istack.internal.NotNull;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Jeremy on 2/2/2016.
 */
public class Config extends Observable {

    private static Config instance = new Config("configurations/our_project.json");

    private JSONParser parser;
    private JSONObject jsonObject;
    private List<String> allclasses;
    private File configuration;

    private Config(String s) {
        this.setNewJsonObject(s);
        this.allclasses = this.getFormattedListOfClasses();
        this.configuration = new File(s);

    }

    public static Config newInstance(String s) {
        if (instance != null) {
            instance.setNewJsonObject(s);
            instance.allclasses = instance.getFormattedListOfClasses();
            instance.configuration = new File(s);
            instance.notifyOthers();
        }
        return instance;
    }

    public static Config getInstance() {
        return instance;
    }

    public String getPackage() {
        String pkg = null;
        if(getStringList("packages").size() > 0) {
            pkg = getStringList("packages").get(0);
            pkg = pkg.replace("./src/", "").replace("/", ".").replace(".java", "");
        }
        return pkg;
    }

    public void notifyOthers() {
        this.setChanged();
        this.notifyObservers();
    }

    private void setNewJsonObject(String fileLocation) {
        try {
            parser = new JSONParser();
            this.jsonObject = (JSONObject) parser.parse(new FileReader(fileLocation));
            this.setChanged();
            this.notifyObservers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Determines if the class in question is a part of the package.
     * @param classInQuestion - The class in question.
     * @return - True if the class is in the package.
     */
    public static boolean inPackageConfiguration(String classInQuestion) {

        String formattedClass = classInQuestion.replace("/", ".");
        Config config = Config.getInstance();

        return config.getFormattedListOfClasses().contains(formattedClass);
    }

    public String getDotFileOutputLocation() {
        String outputLocation = (String) jsonObject.get("dot_output");
        return outputLocation;
    }


    /**
     * Recurse down the file structure to get all the files necessary.
     * @param directory - The directory to start at.
     * @param files - The files to view.
     * @return - The list of files.
     */
    public List<File> recurseFilesInDirectory(File directory, List<File> files) {
        if(directory.isFile()) {
            files.add(directory);
            return files;
        }
        else if (directory.isDirectory()) {
            for (File newFile : directory.listFiles()) {
                recurseFilesInDirectory(newFile, files);
            }
        }
        return files;
    }

    public List<String> getFormattedListOfClasses() {
        List<String> files = new ArrayList<>();

        // Get all the packages.
        List<String> packages = this.getStringList("packages");
        if(packages != null) {
            for(String s : packages) {
                File file = new File(s);
                List<File> directoryFiles = recurseFilesInDirectory(file, new ArrayList<>());
                for(File packageFile : directoryFiles) {
                    String path = packageFile.getPath().replace(".\\src\\", "").replace("\\", ".").replace(".java", "");
                    files.add(path);
                }
            }
        }

        // Get all the class strings.
        List<String> classes = this.getStringList("classes");
        if(classes != null) {
            files.addAll(classes);
        }

        // Get all files that should be removed.
        List<String> excludes = this.getStringList("excludes");
        if(excludes != null) {
            files.removeAll(excludes);
        }

        return files;
    }

    public void addToStringList(String key, String addIn) {
        List<String> excludes = this.getStringList(key);
        if(excludes == null) {
            excludes = new ArrayList<>();
        }
        excludes.add(addIn);
        this.jsonObject.put(key, excludes);
        writeObject();
    }

    private void writeObject() {
        try {
            FileWriter writer = new FileWriter(this.getConfigurationLocation().getAbsolutePath());
            writer.write(this.jsonObject.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeFromStringList(String key, String removedValue) {
        List<String> list = this.getStringList(key);
        list.remove(removedValue);
        this.jsonObject.put(key, list);
        writeObject();
    }

    public void removeListFromStringList(String key, List<String> removedValues) {
        List<String> list = this.getStringList(key);
        list.removeAll(removedValues);
        this.jsonObject.put(key, list);
        writeObject();
    }

    /**
     * Returns the object at the key as a list of strings.
     * @param key - The key to search the json for.
     * @return - The returned String list.
     */
    public List<String> getStringList(@NotNull String key) {
        if(this.jsonObject.get(key) != null) {
            return (List<String>) this.jsonObject.get(key);
        }
        return null;
    }

    public long getAdapterMinimumCount() {
        JSONObject adapterSettings = (JSONObject) this.jsonObject.get("adapterSettings");
        return (long) adapterSettings.get("minimumMethodCalls");
    }

    public String getImageLocation() {
        String imageLocation = (String) this.jsonObject.get("png_output");
        return imageLocation;
    }

    public File getConfigurationLocation() {
        return this.configuration;
    }
}
