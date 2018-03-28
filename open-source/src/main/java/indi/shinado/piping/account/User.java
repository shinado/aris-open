package indi.shinado.piping.account;

import indi.shinado.piping.saas.ISObject;

public class User {

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public User(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String id = null;
    public String email;
    public String name;

    public static User from(ISObject obj){
        User user = new User();
        user.email = obj.getString("email");
        user.name = obj.getString("name");
        user.id = obj.getObjectId();

        return user;
    }
}
