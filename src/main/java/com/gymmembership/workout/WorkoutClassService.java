package com.gymmembership.workout;

import java.util.ArrayList;

public class WorkoutClassService {

    // DAO used to communicate with the workout_classes table
    private WorkoutClassDAO workoutClassDao;

    // Creates the DAO when the service is created
    public WorkoutClassService() {
        this.workoutClassDao = new WorkoutClassDAO();
    }

    // Validates workout class information before saving it to the database
    public void createWorkoutClass(WorkoutClass workoutClass) {

        if (workoutClass == null) {
            System.out.println("Workout class not found.");
            return;
        }

        if (workoutClass.getTrainerId() <= 0) {
            System.out.println("Trainer ID must be an integer higher than 0.");
            return;
        }

        if (workoutClass.getClassName() == null ||
                workoutClass.getClassName().isBlank()) {
            System.out.println("Class name cannot be empty.");
            return;
        }

        if (workoutClass.getDescription() == null ||
                workoutClass.getDescription().isBlank()) {
            System.out.println("Description cannot be empty.");
            return;
        }

        if (workoutClass.getClassTime() == null) {
            System.out.println("Class time cannot be empty.");
            return;
        }

        if (workoutClass.getClassDate() == null) {
            System.out.println("Class date cannot be empty.");
            return;
        }

        // Send the validated workout class to the DAO to be saved
        workoutClassDao.createWorkoutClass(workoutClass);
    }

    // Retrieves all workout classes from the database
    public ArrayList<WorkoutClass> getAllWorkoutClasses() {
        return workoutClassDao.getAllWorkoutClasses();
    }

    // Retrieves all workout classes belonging to a specific trainer
    public ArrayList<WorkoutClass> getAllClassesByTrainer(int trainerId) {

        if (trainerId <= 0) {
            System.out.println("Trainer ID must be an integer higher than 0.");
            return new ArrayList<>();
        }

        return workoutClassDao.getAllClassesByTrainer(trainerId);
    }

    // Validates updated class information before updating the database
    public void updateWorkoutClass(WorkoutClass workoutClass) {

        if (workoutClass == null) {
            System.out.println("Workout class not found.");
            return;
        }

        if (workoutClass.getClassId() <= 0) {
            System.out.println("Class ID must be an integer higher than 0.");
            return;
        }

        // Check that the class exists before attempting to update it
        WorkoutClass existingClass =
                workoutClassDao.getClassByID(workoutClass.getClassId());

        if (existingClass == null) {
            System.out.println("Workout class not found.");
            return;
        }

        if (workoutClass.getTrainerId() <= 0) {
            System.out.println("Trainer ID must be an integer higher than 0.");
            return;
        }

        if (workoutClass.getClassName() == null ||
                workoutClass.getClassName().isBlank()) {
            System.out.println("Class name cannot be empty.");
            return;
        }

        if (workoutClass.getDescription() == null ||
                workoutClass.getDescription().isBlank()) {
            System.out.println("Description cannot be empty.");
            return;
        }

        if (workoutClass.getClassTime() == null) {
            System.out.println("Class time cannot be empty.");
            return;
        }

        if (workoutClass.getClassDate() == null) {
            System.out.println("Class date cannot be empty.");
            return;
        }

        // Send the validated changes to the DAO
        workoutClassDao.updateWorkoutClass(workoutClass);
    }

    // Deletes a workout class using its class ID
    public void deleteWorkoutClass(int classId) {

        if (classId <= 0) {
            System.out.println("Class ID must be an integer higher than 0.");
            return;
        }

        // Check that the class exists before attempting to delete it
        WorkoutClass workoutClass = workoutClassDao.getClassByID(classId);

        if (workoutClass == null) {
            System.out.println("Workout class not found.");
            return;
        }

        workoutClassDao.deleteWorkoutClass(classId);
    }

    // Retrieves one workout class using its class ID
    public WorkoutClass getClassByID(int classId) {
        return workoutClassDao.getClassByID(classId);
    }
}