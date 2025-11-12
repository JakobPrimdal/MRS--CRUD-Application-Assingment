package dk.easv.mrs.DAL;

// Java imports
import dk.easv.mrs.BE.Movie;

// Project imports
import java.util.List;

public interface IMovieDataAccess {

    List<Movie> getAllMovies() throws Exception;

    Movie createMovie(Movie newMovie) throws Exception;

    void updateMovie(Movie movie) throws Exception;

    void deleteMovie(Movie movie) throws Exception;

}
