package dk.easv.mrs.DAL;
// Java imports
import java.util.ArrayList;
import java.util.List;

// Project imports
import dk.easv.mrs.BE.Movie;


public class MovieDAO_Mock implements IMovieDataAccess {

    private List<Movie> allMovies;

    public MovieDAO_Mock()
    {
        allMovies = new ArrayList<>();
        allMovies.add(new Movie(1, 1991,"Terminator 2"));
        allMovies.add(new Movie(2, 2001,"Harry Potter and the Sorcerer´s Stone"));
        allMovies.add(new Movie(3, 2010, "Inception"));
    }

    /**
     * @return list of movies of type Movie
     */
    @Override
    public List<Movie> getAllMovies() {
        return allMovies;
    }

    /**
     * Creates new Movie
     * @param title
     * @param year
     * @return null
     * @throws Exception
     */
    @Override
    public Movie createMovie(Movie newMovie) throws Exception {
        return null;
    }

    /**
     * Updates Movie
     * @param movie
     * @throws Exception
     */
    @Override
    public void updateMovie(Movie movie) throws Exception {

    }

    /**
     * Deletes Movie
     * @param movie
     * @throws Exception
     */
    @Override
    public void deleteMovie(Movie movie) throws Exception {

    }

}
