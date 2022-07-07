package Model;

import java.io.Serializable;

public class User implements Serializable {

    private int userId;

    private String userName;

    private String password;

    private int roleId;

    public User() {
    }

    public User(int id, String userName, String password, int role) {
        userId = id;
        this.userName = userName;
        this.password = password;
        this.roleId = role;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return roleId;
    }

    public void setRole(int role) {
        this.roleId = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
