package backend.authentication;


import java.nio.charset.Charset;
import java.util.Random;

public class Authenticator {

    public String getSessionID() {
        return sessionID;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    private String username;
    private String password;
    private String sessionID;
    private boolean authenticated;

    public Authenticator(String username, String password){
        this.username = username;
        this.password = password;
    }

    public boolean authenticate(){
        if (username.equals("admin") && password.equals("BoboJeSmutny")) {
            byte[] array = new byte[12];
            new Random().nextBytes(array);
            this.sessionID = new String(array, Charset.forName("UTF-8"));
            this.authenticated = true;
            return true;
        }
        else return false;
    }
}
