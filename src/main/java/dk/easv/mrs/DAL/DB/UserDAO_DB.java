package dk.easv.mrs.DAL.DB;

import dk.easv.mrs.BE.User;
import dk.easv.mrs.DAL.IUserDataAccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO_DB implements IUserDataAccess {

    private DBConnector databaseConnector;

    public UserDAO_DB() {
        try {
            databaseConnector = new DBConnector();
        } catch (IOException e) {
            throw new RuntimeException("DatabaseConnecter failed");
        }
    }

    /**
     * @return List of type User queried from DB
     */
    public List<User> getAllUsers() {
        // Create return data structure
        ArrayList allUsers = new ArrayList<>();

        // Create a connection // 'try-with-resources'
        try (Connection connection = databaseConnector.getConnection()) {
            // Create SQL command
            String sql = "SELECT * FROM " + "[" + "User" + "]"; // <--------------------------------------------- If it fails to get Users, then it might be here that it fails, because of User / [User]
            // Create a statement that we later can send to the database
            Statement statement = connection.createStatement();

            // Executes the sql-command // Sends the sql-command to the database
            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()) {
                    // Map DB row to User object
                    int id = resultSet.getInt("Id");
                    String name = resultSet.getString("name");

                    User newUser = new User(id, name);
                    allUsers.add(newUser);
                }
            }
            return allUsers;

        } catch (SQLException ex) {
            ex.printStackTrace();
            //throw new Exception("Could not get users from database");
        }

        return allUsers; //
    }
}
