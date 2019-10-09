package com.example.starter;

import java.util.concurrent.atomic.*;

public class User {

    private static final AtomicInteger COUNTER
    = new AtomicInteger();

    private final int id;

    private String  name;

    private String password;

    private String email;

    private String contact;

    private String salary;

    public User(){
        this.id = COUNTER.getAndIncrement();
    }

    public User(String name, String email, String contact, String password, String salary){
        this.id = COUNTER.getAndIncrement();
        this.name = name;
        this.salary = salary;
        this.email = email;
        this.contact = contact;
        this.password = password;
    }

    public User(Integer id, String name, String salary){
      this.id = id;
      this.name = name;
      this.salary = salary;
  }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public User setName(String name){
        this.name =  name;
        return this;
    }

    public String getEmail(){
        return email;
    }

    public User setEmail(String email){
        this.email = email;
        return this;
    }

    public String getContact(){
        return contact;
    }

    public User setContact(String contact){
        this.contact = contact;
        return this;
    }

    public String getSalary(){
        return salary;
    }

    public User setSalary(String salary){
        this.salary = salary;
        return this;
    }

    public String getPassword(){
        return password;
    }

    public User setPassword(String password){
        this.password = password;
        return this;
    }

}
