package com.gymmembership.workout;
import com.gymmembership.user.User;
import com.gymmembership.user.UserDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class WorkoutClassService {

    // DAO used to communicate with the workout_classes table
    private final WorkoutClassDAO workoutClassDao = new WorkoutClassDAO();
    private final UserDAO userDao = new UserDAO();

    // Validates workout class information before saving it to the database
    public void createWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        if (workoutClass == null) {
            throw new IllegalArgumentException("Workout class must be provided.");
        }

        validateTrainer(workoutClass.getTrainerId());

        if (workoutClass.getClassName() == null ||
                workoutClass.getClassName().isBlank()) {
            throw new IllegalArgumentException("Class name cannot be empty.");
        }

        if (workoutClass.getDescription() == null ||
                workoutClass.getDescription().isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }

        if (workoutClass.getClassTime() == null) {
            throw new IllegalArgumentException("Class time cannot be empty.");
        }

        if (workoutClass.getClassDate() == null) {
            throw new IllegalArgumentException("Class date cannot be empty.");
        }

        // Send the validated workout class to the DAO to be saved
        workoutClassDao.createWorkoutClass(workoutClass);
    }


    // Retrieves all workout classes from the database
    public ArrayList<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        return workoutClassDao.getAllWorkoutClasses();
    }

    // Retrieves all workout classes belonging to a specific trainer
    public ArrayList<WorkoutClass> getAllClassesByTrainer(int trainerId) throws SQLException {
        if (trainerId <= 0) {
            throw new IllegalArgumentException("Trainer ID must be greater than 0.");
        }

        return workoutClassDao.getAllClassesByTrainer(trainerId);
    }

    // Validates updated class information before updating the database
    public void updateWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        if (workoutClass == null) {
            throw new IllegalArgumentException("Workout class must be provided.");
        }

        if (workoutClass.getClassId() <= 0) {
            throw new IllegalArgumentException("Class ID must be greater than 0.");
        }

        // Check that the class exists before attempting to update it
        WorkoutClass existingClass = workoutClassDao.getClassByID(workoutClass.getClassId());

        if (existingClass == null) {
            throw new IllegalArgumentException("Workout class not found.");
        }

        validateTrainer(workoutClass.getTrainerId());

        if (workoutClass.getClassName() == null ||
                workoutClass.getClassName().isBlank()) {
            throw new IllegalArgumentException("Class name cannot be empty.");
        }

        if (workoutClass.getDescription() == null ||
                workoutClass.getDescription().isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }

        if (workoutClass.getClassTime() == null) {
            throw new IllegalArgumentException("Class time cannot be empty.");
        }

        if (workoutClass.getClassDate() == null) {
            throw new IllegalArgumentException("Class date cannot be empty.");
        }

        // Send the validated changes to the DAO
        workoutClassDao.updateWorkoutClass(workoutClass);
    }

    // Overloaded update workout class method for trainers that checks to make sure logged in user's ID is the same as the trainer ID of the class they're trying to update
    public void updateWorkoutClass(WorkoutClass workoutClass, int trainerId) throws SQLException {
        if (workoutClass == null) {
            throw new IllegalArgumentException("Workout class must be provided.");
        }

        if (workoutClass.getClassId() <= 0) {
            throw new IllegalArgumentException("Class ID must be greater than 0.");
        }

        if (trainerId <= 0) {
            throw new IllegalArgumentException("Trainer ID must be greater than 0.");
        }

        WorkoutClass existingClass = workoutClassDao.getClassByID(workoutClass.getClassId());

        if (existingClass == null) {
            throw new IllegalArgumentException("Workout class not found.");
        }

        if (existingClass.getTrainerId() != trainerId) {
            throw new IllegalArgumentException("You can only update your own workout classes.");
        }

        updateWorkoutClass(workoutClass);
    }

    // Deletes a workout class using its class ID
    public void deleteWorkoutClass(int classId) throws SQLException {
        if (classId <= 0) {
            throw new IllegalArgumentException("Class ID must be greater than 0.");
        }

        // Check that the class exists before attempting to delete it
        WorkoutClass workoutClass = workoutClassDao.getClassByID(classId);

        if (workoutClass == null) {
            throw new IllegalArgumentException("Workout class not found.");
        }

        boolean deleted = workoutClassDao.deleteWorkoutClass(classId);

        if (!deleted) {
            throw new IllegalStateException("Workout class could not be deleted.");
        }
    }

    // Overloaded delete workout class method for trainers that checks to make sure logged in user's ID is the same as the trainer ID of the class they're trying to delete
    public void deleteWorkoutClass(int classId, int trainerId) throws SQLException {
        if (classId <= 0) {
            throw new IllegalArgumentException("Class ID must be greater than 0.");
        }

        if (trainerId <= 0) {
            throw new IllegalArgumentException("Trainer ID must be greater than 0.");
        }

        WorkoutClass workoutClass = workoutClassDao.getClassByID(classId);

        if (workoutClass == null) {
            throw new IllegalArgumentException("Workout class not found.");
        }

        if (workoutClass.getTrainerId() != trainerId) {
            throw new IllegalArgumentException("You can only delete your own workout classes.");
        }

        deleteWorkoutClass(classId);
    }

    // Retrieves one workout class using its class ID
    public WorkoutClass getClassByID(int classId) throws SQLException {
        if (classId <= 0) {
            throw new IllegalArgumentException("Class ID must be greater than 0.");
        }

        WorkoutClass workoutClass = workoutClassDao.getClassByID(classId);

        if (workoutClass == null) {
            throw new IllegalArgumentException("Workout class not found.");
        }

        return workoutClass;
    }

    // Method to validate trainer - this allows us to ensure that a trainer and ID match up when creating a workout class/assigning trainer
    private void validateTrainer(int trainerId) throws SQLException {
        if (trainerId <= 0) {
            throw new IllegalArgumentException("Trainer ID must be greater than 0.");
        }

        User trainer = userDao.getByID(trainerId);

        if (trainer == null) {
            throw new IllegalArgumentException("Trainer not found.");
        }

        if (!trainer.getRole().equalsIgnoreCase("TRAINER")) {
            throw new IllegalArgumentException("User with ID " + trainerId + " is not a trainer.");
        }
    }
}