package AdapterTestClass;

/**
 * Created by Jeremy on 1/27/2016.
 */
public class Adapter implements Template {
    public Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }


    @Override
    public void adapt() {
        adaptee.output();
    }
}
