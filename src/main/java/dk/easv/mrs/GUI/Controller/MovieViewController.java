package dk.easv.mrs.GUI.Controller;

// Java imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

// Project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;
import dk.easv.mrs.GUI.Model.RatingModel;


public class MovieViewController implements Initializable {

    @FXML
    private TextField txtfieldNewMovie;
    @FXML
    private Label lblTotalMovies;
    @FXML
    private TextField txtMovieSearch;
    @FXML
    private TableView<Movie> tblMovies;
    @FXML
    private TableColumn<Movie, String> colTitle;
    @FXML
    private TableColumn<Movie, Integer> colYear;

    private MovieModel movieModel;
    private RatingModel ratingModel;
    private String movieTitle;
    private int movieYear;


    public MovieViewController() {
        try {
            movieModel = new MovieModel();
            ratingModel = new RatingModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
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
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        tblMovies.setItems(movieModel.getObservableMovies());

        lblTotalMovies.setText("Total Movies: "+tblMovies.getItems().size());

        tblMovies.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, selectedMovie) ->
        {
            if (selectedMovie != null) {
                txtfieldNewMovie.setText(selectedMovie.getTitle() + ", " + selectedMovie.getYear());
            }
        });

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
                lblTotalMovies.setText("Total Movies: "+tblMovies.getItems().size());
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

    /**
     *  Event handler for handling the action of adding a movie through all layers
     * @param actionEvent
     */
    @FXML
    private void btnAddMovie(ActionEvent actionEvent) {
        String[] input = txtfieldNewMovie.getText().split(",");

        movieTitle = input[0];
        movieYear = Integer.parseInt(input[1].trim());

        Movie movieCreated = new Movie(-1, movieYear, movieTitle);
        movieModel.createMovie(movieCreated);


    }

    /**
     * Event handler to handle updating a movie through all layers
     * @param actionEvent
     */
    @FXML
    private void btnUpdate(ActionEvent actionEvent) {
        String[] updatedMovie = txtfieldNewMovie.getText().split(",");
        String updatedTitle = updatedMovie[0];
        int updatedYear = Integer.valueOf(updatedMovie[1].trim());

        Movie selectedMovie = tblMovies.getSelectionModel().getSelectedItem();

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

    /**
     *  Event handler for handling deleting a movie through all layers
     * @param actionEvent
     */
    @FXML
    private void btnDelete(ActionEvent actionEvent){
        Movie selectedMovie = tblMovies.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            try {
                // Delete movie in DAL layer (through the layers)
                movieModel.deleteMovie(selectedMovie);
            } catch (Exception err) {
                displayError(err);
            }
        }
    }

    /**
     *  Event handler for handling the action of seeing the ratings of a specified movie
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    private void btnSeeRatings(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/RatingView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Ratings");
            stage.setScene(scene);

            //stage.initModality(Modality.APPLICATION_MODAL); // Does do that only one instance of the window can be open at a time
            stage.show();

            // Call fill-method through the instance of the fxmlLoader's controller
            RatingsViewController controller = fxmlLoader.getController();
            controller.fillListView(getSelectedMovie());
        } catch (Exception err) {
            displayError(err);
        }

    }

    public Movie getSelectedMovie() {
        Movie selectedMovie = tblMovies.getSelectionModel().getSelectedItem();
        return selectedMovie;
    }






}
