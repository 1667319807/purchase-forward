package com.hbpu.pojo;

/**
 * @author qiaolu
 * @time 2020/2/28 20:39
 */
public class User {
    private String id;
    private String name;
    private String pwd;
    private String email;
    private String relu;
    private String qq;

    public User() {
    }

    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRelu() {
        return relu;
    }

    public void setRelu(String relu) {
        this.relu = relu;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
