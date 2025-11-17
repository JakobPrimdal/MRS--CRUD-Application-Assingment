package dk.easv.mrs.GUI.Model;

// Java imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

// Project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.MovieManager;

public class MovieModel {

    private ObservableList<Movie> moviesToBeViewed;

    private MovieManager movieManager = new MovieManager();

    public MovieModel() throws Exception {
        //movieManager = new MovieManager();
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }


    /**
     *
     * @return List of Movie(s) that can be viewed in the Listview
     */
    public ObservableList<Movie> getObservableMovies() {
        return moviesToBeViewed;
    }

    /**
     * Clears Listview and adds searchResults to Listview
     * @param query
     * @throws Exception
     */
    public void searchMovie(String query) throws Exception {
        List<Movie> searchResults = movieManager.searchMovies(query);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResults);
    }

    /**
     * Method for creating a movie down through the layers
     * @param newMovie
     * @return
     */
    public Movie createMovie(Movie newMovie) {
        Movie movieCreated = movieManager.createMovie(newMovie);
        getObservableMovies().add(movieCreated);
        return movieCreated;
    }

    /**
     * Method for updating a movie down through the layers
     * @param movieToBeUpdated
     * @throws Exception
     */
    public void updateMovie(Movie movieToBeUpdated) throws Exception {
        // update movie in DAL layer (Through the layers)
        movieManager.updateMovie(movieToBeUpdated);

        int index = moviesToBeViewed.indexOf(movieToBeUpdated);
        moviesToBeViewed.set(index, movieToBeUpdated);
    }

    /**
     * Method for deleting a movie down through the layers
     * @param selectedMovie
     * @throws Exception
     */
    public void deleteMovie(Movie selectedMovie) throws Exception {
        // Removes movie in DAL layer (through the layers)
        movieManager.deleteMovie(selectedMovie);

        // Update observable list
        moviesToBeViewed.remove(selectedMovie);
    }
}
