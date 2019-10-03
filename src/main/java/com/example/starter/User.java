package com.example.starter;

import java.util.concurrent.atomic.*;

public class User {

    private static final AtomicInteger COUNTER
    = new AtomicInteger();

    private final int id;

    private String  name;

    private String salary;

    public User(){
        this.id = COUNTER.getAndIncrement();
    }

    public User(String name, String salary){
        this.id = COUNTER.getAndIncrement();
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

    public String getSalary(){
        return salary;
    }

    public User setSalary(String salary){
        this.salary = salary;
        return this;
    }
}
