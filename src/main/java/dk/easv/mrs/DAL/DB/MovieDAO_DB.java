package dk.easv.mrs.DAL.DB;

// Project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.DAL.IMovieDataAccess;

// Java imports
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess {

    private DBConnector databaseConnector; // = new DBConnector();

    public MovieDAO_DB() {
        try {
            databaseConnector = new DBConnector();
        } catch (IOException e) {
            throw new RuntimeException("DatabaseConnector failed");
        }
    }

    public List<Movie> getAllMovies() throws Exception {
        // Create return data structure
        ArrayList<Movie> allMovies = new ArrayList<>();


        // Create a connection
        try (Connection connection = databaseConnector.getConnection()) {
            // Create SQL command
            String sql = "SELECT * FROM Movie";
            // Create some kind of statement
            Statement statement = connection.createStatement();

            // Do relevant treatment of statement
            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()) {
                    int id = resultSet.getInt("Id");
                    int year = resultSet.getInt("Year");
                    String title = resultSet.getString("Title");

                    Movie newMovie = new Movie(id, year, title);
                    allMovies.add(newMovie);
                }
            }
            return allMovies;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not get movies from database", ex);
        }
    }

    public Movie createMovie(Movie newMovie) {
        return newMovie;
    }

    public void updateMovie(Movie movie) {

    }

    public void deleteMovie(Movie movie) {

    }


}
