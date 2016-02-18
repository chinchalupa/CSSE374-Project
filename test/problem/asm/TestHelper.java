package problem.asm;

import problem.asm.structures.Config;
import problem.asm.structures.FileGenerator;
import problem.asm.structures.UMLGenerator;

/**
 * Created by jared on 2/3/2016.
 */
public class TestHelper {

	public static FileGenerator setUp(String confpath) throws Exception {

		Config.newInstance(confpath);
		FileGenerator umlGenerator = new UMLGenerator();

		umlGenerator.generateClassList();
		umlGenerator.generateNodes();
		umlGenerator.updateNodes();
		umlGenerator.write();
		
		return umlGenerator;
	}
	
	
	
}