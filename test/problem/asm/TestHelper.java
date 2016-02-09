package problem.asm;

/**
 * Created by jared on 2/3/2016.
 */
public class TestHelper {

	public static FileGenerator setUp(String confpath) throws Exception {
		
		FileGenerator umlGenerator = new UMLGenerator(confpath);

		umlGenerator.generateClassList();
		umlGenerator.generateNodes();
		umlGenerator.updateNodes();
		umlGenerator.write();
		
		return umlGenerator;
	}
	
	
	
}