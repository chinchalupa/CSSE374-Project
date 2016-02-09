package problem.asm;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 2/2/2016.
 */
public class Config {

    private static Config instance = null;

    private JSONParser parser;
    private JSONObject jsonObject;

    private Config(String s) {
        parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader(s));
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        jsonObject = (JSONObject) obj;
    }

    public static Config newInstance(String s) {
        instance = new Config(s);
        return instance;
    }

    public static Config getInstance() {
        return instance;
    }

    public String getPackage() {
        String pkg = getPackageList().get(0);
        pkg = pkg.replace("./src/", "").replace("/", ".").replace(".java", "");
        return pkg;
    }

    public static boolean inPackageConfiguration(String classInQuestion) {
        String incomingClass = classInQuestion.replace("/", ".");
        if(Config.getInstance().getClassList() != null) {
            for (String cls : Config.getInstance().getClassList()) {
                if (cls.equals(incomingClass)) {
                    return true;
                }
            }
        }

        if(Config.getInstance().getPackageList() != null) {
           if(incomingClass.contains(Config.getInstance().getPackage())) {
               return true;
           }
        }

//TODO: Leave uncommented if you want a summarized document that is legible
        if(Config.getInstance().getPackageList() != null) {
            for(String pkg : Config.getInstance().getPackageList()) {
                File directory = new File(pkg);

                for (File file : directory.listFiles()) {
                    String name = file.getName().substring(0, file.getName().indexOf("."));
                    if (name.equals(incomingClass)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public String getDotFileOutputLocation() {
        String outputLocation = (String) jsonObject.get("dot_output");
        return outputLocation;
    }

    public List<String> getPackageList() {
        JSONArray packageValue = (JSONArray) jsonObject.get("packages");
        if(packageValue != null) {
            List<String> packages = new ArrayList<>();
            packages.addAll(packageValue);
            return packages;
        }
        return null;
    }

    public List<String> getClassList() {
        JSONArray packageValue = (JSONArray) jsonObject.get("classes");
        if(packageValue != null) {
            List<String> packages = new ArrayList<>();
            packages.addAll(packageValue);
            return packages;
        }
        return null;
    }

    public List<String> getClassesAndPackageClassesList() {
        List<String> files = new ArrayList<>();

        if(this.getPackageList() != null) {
            for (String s : this.getPackageList()) {
                File directory = new File(s);
                File packageFiles[] = directory.listFiles();
                for (File packageFile : packageFiles) {
                    files.add(packageFile.getPath().replace(".\\src\\", "").replace("\\", ".").replace(".java", ""));
                }
            }
        }

        if(this.getClassList() != null) {
            for (String s : this.getClassList()) {
                files.add(s.replace("\\", "."));
            }
        }
        return files;
    }

    public ArrayList<String> detectedPatterns() {
        ArrayList<String> patterns = (ArrayList<String>) this.jsonObject.get("detectors");
        return patterns;
    }
}
