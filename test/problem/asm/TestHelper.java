package problem.asm;

/**
 * Created by jared on 2/3/2016.
 */
public class TestHelper {

	public static UMLGenerator setUp(String confpath) throws Exception {
		
		Config config = Config.newInstance(confpath);
		FileGenerator umlGenerator = new UMLGenerator();

		if (config.shouldDetectSingletons()) {
			umlGenerator = new SingletonDetector(umlGenerator);
		}

		if (config.shouldDetectDecorators()) {
			umlGenerator = new DecoratorDetector(umlGenerator);
		}

		if (config.shouldDetectAdatpers()) {
			umlGenerator = new AdapterDetector(umlGenerator);
		}

		umlGenerator.generateClassList();
		umlGenerator.generateNodes();
		umlGenerator.getNodes();
		umlGenerator.write();
		
		return (UMLGenerator) umlGenerator;
	}
	
	
	
}