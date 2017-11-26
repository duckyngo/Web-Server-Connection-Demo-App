package opennlp.duckyapp.com.webserverconnect;

import java.io.Serializable;

public class Accounts implements Serializable{
    String acc_user;
    String acc_pwd;

    public Accounts(String acc_user, String acc_pwd) {
        this.acc_user = acc_user;
        this.acc_pwd = acc_pwd;
    }
}
