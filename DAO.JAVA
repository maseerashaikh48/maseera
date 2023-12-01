package com.project.dao;

import com.modules.entities.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    private Connection connection;

    public StudentDao(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        this.connection = connection;
    }

    public void createStudent(Student student) throws SQLException {
        String query = "INSERT INTO studentpi (StudentId, FirstName, LastName, Password, Dob , Contact, Gender, Email, Address) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        	 preparedStatement.setString(1, student.getStudentId());
        	preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setString(4, student.getPassword());
            preparedStatement.setString(5, student.getDob());
            preparedStatement.setString(6, student.getContact());
            preparedStatement.setString(7, student.getGender());
            preparedStatement.setString(8, student.getEmail());
            preparedStatement.setString(9, student.getAddress());

            preparedStatement.executeUpdate();
        }
    }

    public Student getStudentById(String studentId) throws SQLException {
        String query = "SELECT * FROM studentpi WHERE StudentID = ?";
        Student student = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    student = mapResultSetToStudent(resultSet);
                }
            }
        }

        return student;
    }

    public Student validateStudent(String studentId, String password) throws SQLException {
        String query = "SELECT * FROM studentpi WHERE StudentID = ? AND Password = ?";
        Student student = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    student = mapResultSetToStudent(resultSet);
                }
            }
        }

        return student;
    }

    public boolean updateStudent(Student student) throws SQLException {
        String query = "UPDATE studentpi SET FirstName=?, LastName=?, Password=?, dob=?, Contact=?, Gender=?, Email=?, Address=? WHERE StudentID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getPassword());
            preparedStatement.setString(4, student.getDob());
            preparedStatement.setString(5, student.getContact());
            preparedStatement.setString(6, student.getGender());
            preparedStatement.setString(7, student.getEmail());
            preparedStatement.setString(8, student.getAddress());
            preparedStatement.setString(9, student.getStudentId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public void deleteStudent(String studentId) throws SQLException {
        String query = "DELETE FROM studentpi WHERE StudentID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM studentpi";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Student student = mapResultSetToStudent(resultSet);
                students.add(student);
            }
        }

        return students;
    }

    private Student mapResultSetToStudent(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setStudentId(resultSet.getString("StudentID"));
        student.setFirstName(resultSet.getString("FirstName"));
        student.setLastName(resultSet.getString("LastName"));
        student.setPassword(resultSet.getString("Password"));
        student.setDob(resultSet.getString("DOB"));
        student.setContact(resultSet.getString("Contact"));
        student.setGender(resultSet.getString("Gender"));
        student.setEmail(resultSet.getString("Email"));
        student.setAddress(resultSet.getString("Address"));
        return student;
    }
}
