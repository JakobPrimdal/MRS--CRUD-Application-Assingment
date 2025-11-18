package dk.easv.mrs.GUI.Controller;

// Project imports
import dk.easv.mrs.BE.Movie;

// Java imports
import dk.easv.mrs.BE.Rating;
import dk.easv.mrs.GUI.Model.RatingModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;


public class RatingsViewController {

    @FXML
    private ListView<String> ratingsListView;

    private RatingModel ratingModel;


    public RatingsViewController() {
        try {
            ratingModel = new RatingModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *  Fills ratingsListView with ratings (Strings) of a specified Movie
     * @param selectedMovie
     * @throws Exception
     */
    public void fillListView(Movie selectedMovie) throws Exception {

        if (ratingsListView == null)
            System.out.println("Næh");

        for (Rating r : ratingModel.getAllRatings()) {
            if (ratingsListView != null) {
                if (r.getMovieId() == selectedMovie.getId()) {
                    String rating = selectedMovie.getTitle()+" - Score: "+r.getScore()+", given by " + ratingModel.getUserNameById(r.getUserId());
                    ratingsListView.getItems().add(rating);
                }
            }
        }
    }

}
