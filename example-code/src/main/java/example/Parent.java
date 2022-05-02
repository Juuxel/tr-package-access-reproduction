package example;

public class Parent {
    protected final String secret;

    public Parent(String secret) {
        this.secret = secret;
    }

    public void printSecret() {
        System.out.println(secret);
    }

    public static void main(String[] args) {
        var parent = new Parent("hello");
        new Child(parent).printSecret();
    }
}
