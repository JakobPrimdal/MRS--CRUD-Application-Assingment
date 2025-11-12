package dk.easv.mrs.BLL;

// Java imports
import java.util.List;

// Project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.util.MovieSearcher;
import dk.easv.mrs.DAL.IMovieDataAccess;
import dk.easv.mrs.DAL.MovieDAO_File;


public class MovieManager {

    private MovieSearcher movieSearcher = new MovieSearcher();
    private IMovieDataAccess movieDAO;

    public MovieManager() {
        movieDAO = new MovieDAO_File();
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

    public Movie createMovie(Movie newMovie){
        try {
            Movie movieCreated = movieDAO.createMovie(newMovie);
            return movieCreated;
        } catch (Exception e) {
            System.out.println("ERROR: createMovie failed in MovieManager");
            throw new RuntimeException(e);
        }
    }

    public void updateMovie(Movie movieToBeUpdated) throws Exception {
        movieDAO.updateMovie(movieToBeUpdated);
    }

    public void deleteMovie(Movie selectedMovie) throws Exception {
        movieDAO.deleteMovie(selectedMovie);
    }
}
