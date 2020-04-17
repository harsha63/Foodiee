package com.example.login;

public class logindata {
    
    String email;
    String password;

    public logindata() {
        this.email = email;
        this.password = password;
    }

    public logindata(String email, String password) {
    }

    public logindata(String name, String contact, String email, String password) {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
