package com.wildcodeschool.wildandwizard.repository;

import com.wildcodeschool.wildandwizard.entity.Wizard;
import com.wildcodeschool.wildandwizard.util.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WizardRepository {

    private final static String DB_URL = "jdbc:postgresql://localhost:5432/spring_jdbc_quest";
    private final static String DB_USER = "wilder";
    private final static String DB_PASSWORD = "Hellothere.03";

    public void deleteById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD);
            statement = connection.prepareStatement(
                    "DELETE FROM wizard WHERE id=?");
            statement.setLong(1, id);

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to delete data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
    }

    public List<Wizard> findAll() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD);
            statement = connection.prepareStatement(
                    "SELECT * FROM wizard;");
            resultSet = statement.executeQuery();

            List<Wizard> wizards = new ArrayList<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date birthday = resultSet.getDate("birthday");
                String birthPlace = resultSet.getString("birth_place");
                String biography = resultSet.getString("biography");
                boolean muggle = resultSet.getBoolean("is_muggle");
                wizards.add(new Wizard(id, firstName, lastName, birthday, birthPlace, biography, muggle));
            }
            return wizards;
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
