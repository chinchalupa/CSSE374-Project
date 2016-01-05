package TestRunner;

/**
 * Created by wrightjt on 1/5/2016.
 */
public class Dog extends Animal {

    public Dog(String name, boolean isTelephone, String desc) {
        super(name, isTelephone, desc);
    }

    private void praiseDog() {
        System.out.println("GOOD BOY!");
    }

    public void fetch() {
        System.out.println("GO FETCH!");
    }
}
