package Singleton;

/**
 * Created by Jeremy on 1/26/2016.
 */
public class SingletonTest {
    static SingletonTest test;

    public SingletonTest() {
    }

    public SingletonTest getSingleton() {
        if(test == null) {
            test = new SingletonTest();
        }
        return test;
    }
}
