package SingletonTestClass;

/**
 * Created by Jeremy on 1/27/2016.
 */
public class EagerSingleton {
    public static EagerSingleton singleton = new EagerSingleton();

    public EagerSingleton getInstance() {
        return singleton;
    }
}
