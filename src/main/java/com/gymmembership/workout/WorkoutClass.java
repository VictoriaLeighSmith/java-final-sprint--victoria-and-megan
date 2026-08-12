package com.gymmembership.workout;

import java.time.LocalDate;
import java.time.LocalTime;

// Represents a workout class stored in the gym management system
public class WorkoutClass {

    private int classId;
    private int trainerId;
    private String className;
    private String description;
    private LocalDate classDate;
    private LocalTime classTime;

    // No-argument constructor
    public WorkoutClass() {

    }

    // Creates a WorkoutClass object using all class details
    public WorkoutClass(int classId, int trainerId, String className,
                        String description, LocalDate classDate, LocalTime classTime) {
        this.classId = classId;
        this.trainerId = trainerId;
        this.className = className;
        this.description = description;
        this.classDate = classDate;
        this.classTime = classTime;
    }

    public WorkoutClass(int trainerId, String className, String description, LocalDate classDate, LocalTime classTime) {
        this.trainerId = trainerId;
        this.className = className;
        this.description = description;
        this.classDate = classDate;
        this.classTime = classTime;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getClassDate() {
        return classDate;
    }

    public void setClassDate(LocalDate classDate) {
        this.classDate = classDate;
    }

    public LocalTime getClassTime() {
        return classTime;
    }

    public void setClassTime(LocalTime classTime) {
        this.classTime = classTime;
    }

    // Returns a readable representation of the workout class
    @Override
    public String toString() {
        return "WorkoutClass{" +
                "classId=" + classId +
                ", trainerId=" + trainerId +
                ", className='" + className + '\'' +
                ", description='" + description + '\'' +
                ", classDate=" + classDate +
                ", classTime=" + classTime +
                '}';
    }
}