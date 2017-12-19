package com.example.harikumar.firebase;

public class UserInformation {

    public String name;
    public String rollno;
    public String bus;
    public String booking;

    public UserInformation(){

    }

    public UserInformation(String name, String rollno, String bus,String booking) {
        this.name = name;
        this.rollno = rollno;
        this.bus = bus;
        this.booking=booking;
    }
}
