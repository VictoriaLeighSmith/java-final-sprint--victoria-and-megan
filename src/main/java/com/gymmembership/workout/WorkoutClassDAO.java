package com.gymmembership.workout;

import com.gymmembership.database.DatabaseConnection;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WorkoutClassDAO {

    // Inserts a new workout class into the workout_classes table
    public void createWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        String query = "INSERT INTO workout_classes (trainer_id, class_name, description, class_date, class_time) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            // Set the values for the new workout class
            statement.setInt(1, workoutClass.getTrainerId());
            statement.setString(2, workoutClass.getClassName());
            statement.setString(3, workoutClass.getDescription());
            statement.setDate(4, java.sql.Date.valueOf(workoutClass.getClassDate()));
            statement.setTime(5, java.sql.Time.valueOf(workoutClass.getClassTime()));

            statement.executeUpdate();
        }
    }

    // Retrieves all workout classes from the database
    public ArrayList<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        String query = "SELECT * FROM workout_classes";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<WorkoutClass> workoutClasses = new ArrayList<>();

                while (resultSet.next()) {
                    WorkoutClass workoutClass = buildWorkoutClassObject(resultSet);
                    workoutClasses.add(workoutClass);
                }

                return workoutClasses;
            }
        }
    }

    // Retrieves all workout classes assigned to a specific trainer
    public ArrayList<WorkoutClass> getAllClassesByTrainer(int trainerId) throws SQLException {
        String query = "SELECT * FROM workout_classes WHERE trainer_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            // Use the provided trainer ID to filter the query
            statement.setInt(1, trainerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<WorkoutClass> workoutClassesByTrainer = new ArrayList<>();

                while (resultSet.next()) {
                    WorkoutClass workoutClass = buildWorkoutClassObject(resultSet);
                    workoutClassesByTrainer.add(workoutClass);
                }

                return workoutClassesByTrainer;
            }
        }
    }

    // Updates an existing workout class using its class ID
    public void updateWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        String query = "UPDATE workout_classes SET trainer_id = ?, class_name = ?, description = ?, class_date = ?, class_time = ? WHERE class_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            // Set the updated values for the workout class
            statement.setInt(1, workoutClass.getTrainerId());
            statement.setString(2, workoutClass.getClassName());
            statement.setString(3, workoutClass.getDescription());
            statement.setDate(4, java.sql.Date.valueOf(workoutClass.getClassDate()));
            statement.setTime(5, java.sql.Time.valueOf(workoutClass.getClassTime()));

            // Use the class ID to determine which database row to update
            statement.setInt(6, workoutClass.getClassId());

            statement.executeUpdate();
        }
    }

    // Deletes a workout class from the database using its class ID
    public boolean deleteWorkoutClass(int classId) throws SQLException {
        String query = "DELETE FROM workout_classes WHERE class_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            // Specify which workout class should be deleted
            statement.setInt(1, classId);

            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;
        }
    }

    // Retrieves a single workout class using its class ID
    public WorkoutClass getClassByID(int classId) throws SQLException {
        String query = "SELECT * FROM workout_classes WHERE class_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setInt(1, classId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    WorkoutClass workoutClass = buildWorkoutClassObject(resultSet);
                    return workoutClass;
                }
            }
        }
        return null;
    }

    // Helper method to build workout class object
    private WorkoutClass buildWorkoutClassObject(ResultSet resultSet) throws SQLException {
        WorkoutClass workoutClass = new WorkoutClass();

        workoutClass.setClassId(resultSet.getInt("class_id"));
        workoutClass.setTrainerId(resultSet.getInt("trainer_id"));
        workoutClass.setClassName(resultSet.getString("class_name"));
        workoutClass.setDescription(resultSet.getString("description"));
        workoutClass.setClassDate(resultSet.getDate("class_date").toLocalDate());
        workoutClass.setClassTime(resultSet.getTime("class_time").toLocalTime());

        return workoutClass;
    }
}