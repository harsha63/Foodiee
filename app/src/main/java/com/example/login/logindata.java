package com.example.login;

public class logindata {
    
    public String emailssss;
    public String password;
    public String name;
    public String contact;


    public logindata(String email, String password) {
    }

    public logindata(String name, String contact, String email, String password) {
        this.emailssss = email;
        this.password = password;
        this.contact = contact;
        this.name = name;

    }

    public String getEmail() {
        return emailssss;
    }

    public void setEmail(String email) {
        this.emailssss = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
