package com.example.test.objects;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private String email;
    private boolean assgined;
    private List<String> exclusions = new ArrayList<>();
    private String assignee_name;
    private String assignee_email;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
        this.assgined = false;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean getAssigned() {
        return this.assgined;
    }

    public void setAssgined(boolean assgined) {
        this.assgined = assgined;
    }

    public String getAssigneeName() {
        return this.assignee_name;
    }

    public String getAssigneeEmail() {
        return this.assignee_email;
    }

    public void setAssigneeName(String assignee_name) {
        this.assignee_name = assignee_name;
    }

    public void setAssigneeEmail(String assignee_email) {
        this.assignee_email = assignee_email;
    }

    public void addExclusion(String exclusion_name) {
        exclusions.add(exclusion_name);
    }

    public List<String> getExclusions() {
        return this.exclusions;
    }

    public int getExclusionsSize() {
        return this.exclusions.size();
    }
    
    @Override
    public String toString() {        
        return this.getName() + " " + this.getEmail() + " " + this.getAssigneeName() + " " + this.getAssigneeEmail();
    }
}
