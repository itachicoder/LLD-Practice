package pu.com.ay.SolidPrinciples.DependencyInversionPrinciple;

// Faculty interface - the abstraction

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

 interface Faculty {
    String performDuties();
    String getName();
}

// Concrete implementation of Teacher
 class Teacher implements Faculty {
    private final String name;
    
    public Teacher(String name) {
        this.name = name;
    }
    
    @Override
    public String performDuties() {
        return "Teacher " + name + " is teaching students";
    }
    
    @Override
    public String getName() {
        return name;
    }
}

// Concrete implementation of Assistant
 class Assistant implements Faculty {
    private final String name;
    
    public Assistant(String name) {
        this.name = name;
    }
    
    @Override
    public String performDuties() {
        return "Assistant " + name + " is helping teachers";
    }
    
    @Override
    public String getName() {
        return name;
    }
}

// Concrete implementation of Helper
 class Helper implements Faculty {
    private final String name;
    
    public Helper(String name) {
        this.name = name;
    }
    
    @Override
    public String performDuties() {
        return "Helper " + name + " is maintaining the school";
    }
    
    @Override
    public String getName() {
        return name;
    }
}

// Concrete implementation of Secretary
 class Secretary implements Faculty {
    private final String name;
    
    public Secretary(String name) {
        this.name = name;
    }
    
    @Override
    public String performDuties() {
        return "Secretary " + name + " is managing administrative tasks";
    }
    
    @Override
    public String getName() {
        return name;
    }
}

// High-level module that depends on Faculty abstraction
 class Headmaster {
    private final String name;
    private final List<Faculty> facultyMembers;
    
    public Headmaster(String name) {
        this.name = name;
        this.facultyMembers = new ArrayList<>();
    }
    
    public void addFaculty(Faculty faculty) {
        facultyMembers.add(faculty);
    }
    
    public List<String> manageSchool() {
        System.out.println("Headmaster " + name + " is managing the school");
        return facultyMembers.stream()
                .map(Faculty::performDuties)
                .collect(Collectors.toList());
    }
    
    public String getName() {
        return name;
    }
}

// Main class to demonstrate the implementation
public class SchoolSystem {
    public static void main(String[] args) {
        // Create headmaster
        Headmaster headmaster = new Headmaster("Dr. Smith");
        
        // Create different types of faculty
        Faculty mathTeacher = new Teacher("Mr. Johnson");
        Faculty labAssistant = new Assistant("Ms. Davis");
        Faculty maintenance = new Helper("Mr. Brown");
        Faculty adminSecretary = new Secretary("Mrs. Wilson");
        
        // Add faculty members to headmaster's supervision
        headmaster.addFaculty(mathTeacher);
        headmaster.addFaculty(labAssistant);
        headmaster.addFaculty(maintenance);
        headmaster.addFaculty(adminSecretary);
        
        // Manage school and get status reports
        List<String> statusReports = headmaster.manageSchool();
        
        // Print all status reports
        statusReports.forEach(System.out::println);
    }
}