package Model;

import java.io.Serializable;

public class User implements Serializable {

    private int userId;

    private String userName;

    private String password;

    private int roleId;

    double budget;

    private String address;

    private String phone;

    public User() {
    }


    public User(int userId, String userName, String password, String address, String phone, double budget, int roleId) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.budget = budget;
        this.roleId = roleId;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
