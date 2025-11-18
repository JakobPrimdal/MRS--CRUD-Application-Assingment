package dk.easv.mrs.BLL;

// Project imports
import dk.easv.mrs.BE.Rating;
import dk.easv.mrs.DAL.DB.RatingDAO_DB;
import dk.easv.mrs.DAL.IRatingDataAccess;

// Java imports
import java.util.List;

public class RatingManager {

    private IRatingDataAccess ratingDAO;

    public RatingManager() {
        ratingDAO = new RatingDAO_DB();
    }

    public List<Rating> getAllRatings() throws Exception {
        return ratingDAO.getAllRatings();
    }

}
