package ninja.robbert.mayhem.api;

public class RegisterMessage implements InputMessage {
    String name;
    String email;
    String password;

    RegisterMessage() {
        // for jackson
    }

    public RegisterMessage(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
