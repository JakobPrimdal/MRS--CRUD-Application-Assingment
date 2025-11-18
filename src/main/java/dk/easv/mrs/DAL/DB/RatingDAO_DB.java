package dk.easv.mrs.DAL.DB;

// Project imports
import dk.easv.mrs.BE.Rating;
import dk.easv.mrs.DAL.IRatingDataAccess;

// Java imports
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RatingDAO_DB implements IRatingDataAccess
{

    private DBConnector databaseConnector;

    public RatingDAO_DB() {
        try {
            databaseConnector = new DBConnector();
        } catch (IOException e) {
            throw new RuntimeException("DatabaseConnecter failed");
        }
    }

    /**
     * @return List of type Rating queried from DB
     * @throws Exception
     */
    public List<Rating> getAllRatings() throws Exception {
        // Create return data structure
        ArrayList<Rating> allRatings = new ArrayList<>();

        // Create a connection // 'try-with-resources'
        try (Connection connection = databaseConnector.getConnection()) {
            // Create SQL command
            String sql = "SELECT * FROM Rating";

            // Create a statement that we later can send to the database
            Statement statement = connection.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()) {
                    // Map DB row to Rating object
                    int movieId = resultSet.getInt("MovieId");
                    int userId = resultSet.getInt("UserId");
                    int score = resultSet.getInt("Score");

                    Rating newRating = new Rating(movieId, userId, score);

                    allRatings.add(newRating);
                }
            }
            return allRatings;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not get ratings from database", ex);
        }


    }





}
