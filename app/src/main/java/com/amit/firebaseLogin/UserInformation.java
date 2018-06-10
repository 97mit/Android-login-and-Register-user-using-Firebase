package com.amit.firebaseLogin;

public class UserInformation {
    public String name;
    public String phone;
    public String DOB;
    public UserInformation(){

    }

    public UserInformation(String name, String phone,String DOB) {
        this.name = name;
        this.phone = phone;
        this.DOB = DOB;
    }
}
