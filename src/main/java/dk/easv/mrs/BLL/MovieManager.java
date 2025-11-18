package dk.easv.mrs.BLL;

// Java imports
import java.util.List;

// Project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.util.MovieSearcher;
import dk.easv.mrs.DAL.DB.MovieDAO_DB;
import dk.easv.mrs.DAL.IMovieDataAccess;


public class MovieManager {

    private MovieSearcher movieSearcher = new MovieSearcher();
    private IMovieDataAccess movieDAO;

    public MovieManager() {
        movieDAO = new MovieDAO_DB();
        //movieDAO = new MovieDAO_File();
    } // Sets the class the data comes from (Standard from MovieDAO_Mock

    /**
     * @return allMovies
     * @throws Exception
     */
    public List<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    /**
     * @param query
     * @return List of Movie(s) as List searchResult
     * @throws Exception
     */
    public List<Movie> searchMovies(String query) throws Exception {
        List<Movie> allMovies = getAllMovies();
        List<Movie> searchResult = movieSearcher.search(allMovies, query);
        return searchResult;
    }

    /**
     * Method for creating a movie down through the layers
     * @param newMovie
     * @return
     */
    public Movie createMovie(Movie newMovie){
        try {
            Movie movieCreated = movieDAO.createMovie(newMovie);
            return movieCreated;
        } catch (Exception e) {
            System.out.println("ERROR: createMovie failed in MovieManager");
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for updating a movie down through the layers
     * @param movieToBeUpdated
     * @throws Exception
     */
    public void updateMovie(Movie movieToBeUpdated) throws Exception {
        movieDAO.updateMovie(movieToBeUpdated);
    }

    /**
     *  Method for deleting a movie down through the layers
     * @param selectedMovie
     * @throws Exception
     */
    public void deleteMovie(Movie selectedMovie) throws Exception {
        movieDAO.deleteMovie(selectedMovie);
    }
}
