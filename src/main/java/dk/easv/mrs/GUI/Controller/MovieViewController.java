package dk.easv.mrs.GUI.Controller;

// Java imports
import com.sun.jdi.IntegerValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

// Project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;


public class MovieViewController implements Initializable {

    @FXML
    private TextField txtfieldNewMovie;
    @FXML
    private Label lblTotalMovies;
    @FXML
    private TextField txtMovieSearch;
    @FXML
    private ListView<Movie> lstMovies;

    private MovieModel movieModel;
    private String movieTitle;
    private int movieYear;


    public MovieViewController() throws Exception {
        movieModel = new MovieModel();
    }

    /**
     *
     * Loads the listview and tries to use the searchMovie method
     *
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        lstMovies.setItems(movieModel.getObservableMovies());
        lblTotalMovies.setText("Total Movies: "+lstMovies.getItems().size());

        lstMovies.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, selectedMovie) ->
        {
            if (selectedMovie != null) {
                txtfieldNewMovie.setText(selectedMovie.getTitle() + ", " + selectedMovie.getYear());
            }
        });

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
                lblTotalMovies.setText("Total Movies: "+lstMovies.getItems().size());
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

    }

    /**
     * Displays error to the user
     * @param t
     */
    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    @FXML
    private void btnAddMovie(ActionEvent actionEvent) {
        String[] input = txtfieldNewMovie.getText().split(",");

        movieTitle = input[0];
        movieYear = Integer.parseInt(input[1].trim());

        Movie movieCreated = new Movie(-1, movieYear, movieTitle);
        movieModel.createMovie(movieCreated);

        //movieModel.createMovie(movieTitle, movieYear);


    }

    @FXML
    private void btnUpdate(ActionEvent actionEvent) {
        String[] updatedMovie = txtfieldNewMovie.getText().split(",");
        String updatedTitle = updatedMovie[0];
        int updatedYear = Integer.valueOf(updatedMovie[1].trim());

        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            // Update title and year based on textField input
            selectedMovie.setTitle(updatedTitle);
            selectedMovie.setYear(updatedYear);

            try {
                // Update movie in DAL layer (Through the layers)
                movieModel.updateMovie(selectedMovie);
            } catch (Exception err) {
                displayError(err);
            }

            //lstMovies.refresh(); // <-- Works without this line
        }

    }

    @FXML
    private void btnDelete(ActionEvent actionEvent){
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            try {
                // Delete movie in DAL layer (through the layers)
                movieModel.deleteMovie(selectedMovie);
            } catch (Exception err) {
                displayError(err);
            }
        }
    }
}
