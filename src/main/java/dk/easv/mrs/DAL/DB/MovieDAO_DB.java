package dk.easv.mrs.DAL.DB;

// Project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.DAL.IMovieDataAccess;

// Java imports
import java.io.IOException;
import java.sql.*;
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

    /**
     * @return List of type Movie queried from DB
     */
    public List<Movie> getAllMovies() throws Exception {
        // Create return data structure
        ArrayList<Movie> allMovies = new ArrayList<>();


        // Create a connection // 'try-with-resources'
        try (Connection connection = databaseConnector.getConnection()) {
            // Create SQL command
            String sql = "SELECT * FROM Movie";

            // Create a statement that we later can send to the database
            Statement statement = connection.createStatement();

            // executes the sql-command // Sends the sql-command to the database
            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()) {
                    // Map DB row to Movie object
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

    /**
     * Creates new Movie object in DB
     * @param newMovie
     * @return createdMovie
     * @throws Exception
     */
    public Movie createMovie(Movie newMovie) throws Exception{
        String sql = "INSERT INTO dbo.Movie (Title, Year) VALUES (?,?);";

        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Bind parameters
            stmt.setString(1, newMovie.getTitle());
            stmt.setInt(2, newMovie.getYear());

            // Run the specified SQL statement
            stmt.executeUpdate();

            // Get the generated ID from DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = -1;

            if (rs.next())
                id = rs.getInt(1);

            // Create movie object and send up the layers
            Movie createdMovie = new Movie(id, newMovie.getYear(), newMovie.getTitle());

            return createdMovie;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not create movie", ex);
        }

    }

    /**
     * Updates title and year of specified Movie in DB
     * @param movie
     * @throws Exception
     */
    public void updateMovie(Movie movie) throws Exception{

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE Movie SET Title = ?, Year = ? WHERE Id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, movie.getTitle());
                statement.setInt(2, movie.getYear());
                statement.setInt(3, movie.getId());

                statement.executeUpdate();
            }

        }
        catch (SQLException ex) {
            throw new Exception("Could not update movie", ex);
        }

    }

    /**
     * Deletes Movie from DB
     * @param movie
     * @throws Exception
     */
    public void deleteMovie(Movie movie) throws Exception{
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "DELETE FROM Movie WHERE Id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, movie.getId());

                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new Exception("Could not delete Movie", ex);
        }
    }


}
