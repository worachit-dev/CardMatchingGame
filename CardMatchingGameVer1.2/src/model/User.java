package model;

public class User {

    private final String username;
    private final String password;
    

    public User(String user, String password) {
        this.username = user;
        this.password = password;
    }

    public String getUser()    { return username; }
    public String getPassword()  { return password; }
    
}