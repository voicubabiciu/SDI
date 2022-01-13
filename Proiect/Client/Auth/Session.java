package Proiect.Client.Auth;

public class Session {

    private String token;
    private static Session instance;

    private Session() {

    }

    synchronized public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
