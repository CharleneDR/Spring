package com.wildcodeschool.wildandwizard.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wildcodeschool.wildandwizard.entity.Wizard;
import com.wildcodeschool.wildandwizard.util.JdbcUtils;

public class WizardRepository {

    private final static String DB_URL = "jdbc:postgresql://localhost:5432/spring_jdbc_quest";
    private final static String DB_USER = "wilder";
    private final static String DB_PASSWORD = "Hellothere.03";

    public Wizard update(Long id, String firstName, String lastName, Date birthday,
            String birthPlace, String biography, boolean muggle) {

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD);
            statement = connection.prepareStatement(
                    "UPDATE wizard SET first_name=?, last_name=?, birthday=?, birth_place=?, biography=?, is_muggle=? WHERE id=?");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setDate(3, birthday);
            statement.setString(4, birthPlace);
            statement.setString(5, biography);
            statement.setBoolean(6, muggle);
            statement.setLong(7, id);

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to update data");
            }
            return new Wizard(id, firstName, lastName, birthday,
                    birthPlace, biography, muggle);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    public Wizard findById(Long id) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD);
            statement = connection.prepareStatement(
                    "SELECT * FROM wizard WHERE id = ?;");
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date birthday = resultSet.getDate("birthday");
                String birthPlace = resultSet.getString("birth_place");
                String biography = resultSet.getString("biography");
                boolean muggle = resultSet.getBoolean("is_muggle");
                return new Wizard(id, firstName, lastName, birthday, birthPlace, biography, muggle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }
}
