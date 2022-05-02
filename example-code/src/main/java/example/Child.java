package example;

public class Child extends Parent {
    public Child(Parent parent) {
        super(parent.secret);
    }
}
