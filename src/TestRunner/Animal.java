package TestRunner;

/**
 * Created by wrightjt on 1/5/2016.
 */
public abstract class Animal {
    protected String desc;
    private boolean isTelephone;
    public String name;

    public Animal(String name, boolean isTelephone, String desc) {
        this.name = name;
        this.isTelephone = isTelephone;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isTelephone() {
        return isTelephone;
    }

    public void setIsTelephone(boolean isTelephone) {
        this.isTelephone = isTelephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
