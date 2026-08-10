package com.gymmembership.workout;

import com.gymmembership.database.DatabaseConnection;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WorkoutClassDAO {

    // Inserts a new workout class into the workout_classes table
    public void createWorkoutClass(WorkoutClass workoutClass) {

        String query = "INSERT INTO workout_classes "
                + "(trainer_id, class_name, description, class_date, class_time) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);

            // Set the values for the new workout class
            statement.setInt(
                    1,
                    workoutClass.getTrainerId());

            statement.setString(
                    2,
                    workoutClass.getClassName());

            statement.setString(
                    3,
                    workoutClass.getDescription());

            statement.setDate(
                    4,
                    java.sql.Date.valueOf(workoutClass.getClassDate()));

            statement.setTime(
                    5,
                    java.sql.Time.valueOf(workoutClass.getClassTime()));

            statement.execute();

            statement.close();
            con.close();

            System.out.println(
                    "Workout class saved to database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieves all workout classes from the database
    public ArrayList<WorkoutClass> getAllWorkoutClasses() {

        ArrayList<WorkoutClass> workoutClasses = new ArrayList<>();

        String query = "SELECT * FROM workout_classes";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);

            ResultSet rs = statement.executeQuery();

            // Convert each database row into a WorkoutClass object
            while (rs.next()) {
                int classId = rs.getInt("class_id");
                int trainerId = rs.getInt("trainer_id");
                String className = rs.getString("class_name");
                String description = rs.getString("description");
                java.sql.Date classDate = rs.getDate("class_date");
                java.sql.Time classTime = rs.getTime("class_time");

                WorkoutClass newClass = new WorkoutClass(
                        classId,
                        trainerId,
                        className,
                        description,
                        classDate.toLocalDate(),
                        classTime.toLocalTime()
                );

                // Add the object to the list that will be returned
                workoutClasses.add(newClass);
            }

            rs.close();
            statement.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workoutClasses;
    }

    // Retrieves all workout classes assigned to a specific trainer
    public ArrayList<WorkoutClass> getAllClassesByTrainer(int trainerId) {

        ArrayList<WorkoutClass> workoutClassesByTrainer = new ArrayList<>();

        String query = "SELECT * FROM workout_classes WHERE trainer_id = ?";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);

            // Use the provided trainer ID to filter the query
            statement.setInt(1, trainerId);

            ResultSet rs = statement.executeQuery();

            // Convert each matching database row into a WorkoutClass object
            while (rs.next()) {
                int classId = rs.getInt("class_id");
                int classTrainerId = rs.getInt("trainer_id");
                String className = rs.getString("class_name");
                String description = rs.getString("description");
                java.sql.Date classDate = rs.getDate("class_date");
                java.sql.Time classTime = rs.getTime("class_time");

                WorkoutClass newClass = new WorkoutClass(
                        classId,
                        classTrainerId,
                        className,
                        description,
                        classDate.toLocalDate(),
                        classTime.toLocalTime()
                );

                workoutClassesByTrainer.add(newClass);
            }

            rs.close();
            statement.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workoutClassesByTrainer;
    }

    // Updates an existing workout class using its class ID
    public void updateWorkoutClass(WorkoutClass workoutClass) {

        String query = "UPDATE workout_classes "
                + "SET trainer_id = ?, class_name = ?, description = ?, class_date = ?, class_time = ? "
                + "WHERE class_id = ?";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);

            // Set the updated values for the workout class
            statement.setInt(
                    1,
                    workoutClass.getTrainerId());

            statement.setString(
                    2,
                    workoutClass.getClassName());

            statement.setString(
                    3,
                    workoutClass.getDescription());

            statement.setDate(
                    4,
                    java.sql.Date.valueOf(workoutClass.getClassDate()));

            statement.setTime(
                    5,
                    java.sql.Time.valueOf(workoutClass.getClassTime()));

            // Use the class ID to determine which database row to update
            statement.setInt(
                    6,
                    workoutClass.getClassId());

            statement.execute();

            statement.close();
            con.close();

            System.out.println(
                    "Workout updated successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Deletes a workout class from the database using its class ID
    public void deleteWorkoutClass(int classId) {

        String query = "DELETE FROM workout_classes "
                + "WHERE class_id = ?";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);

            // Specify which workout class should be deleted
            statement.setInt(
                    1,
                    classId);

            statement.execute();

            statement.close();
            con.close();

            System.out.println(
                    "Workout deleted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieves a single workout class using its class ID
    public WorkoutClass getClassByID(int classId) {

        WorkoutClass workoutClass = null;

        String query = "SELECT * FROM workout_classes "
                + "WHERE class_id = ?";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(
                    1,
                    classId);

            ResultSet rs = statement.executeQuery();

            // If the class exists, convert the database row into a WorkoutClass object
            if (rs.next()) {
                int foundClassId = rs.getInt("class_id");
                int trainerId = rs.getInt("trainer_id");
                String className = rs.getString("class_name");
                String description = rs.getString("description");
                java.sql.Date classDate = rs.getDate("class_date");
                java.sql.Time classTime = rs.getTime("class_time");

                workoutClass = new WorkoutClass(
                        foundClassId,
                        trainerId,
                        className,
                        description,
                        classDate.toLocalDate(),
                        classTime.toLocalTime()
                );
            }

            rs.close();
            statement.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Returns the WorkoutClass if found, otherwise returns null
        return workoutClass;
    }
}