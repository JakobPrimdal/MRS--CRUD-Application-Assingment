package dk.easv.mrs.GUI.Model;

// Project imports
import dk.easv.mrs.BE.Rating;
import dk.easv.mrs.BE.User;
import dk.easv.mrs.BLL.RatingManager;
import dk.easv.mrs.BLL.UserManager;

// Java imports
import java.util.List;

public class RatingModel {

    private RatingManager ratingManager = new RatingManager();
    private UserManager userManager = new UserManager();

    public List<Rating> getAllRatings() throws Exception {
        return ratingManager.getAllRatings();
    }

    public List<User> getAllUsers() throws Exception {
        return userManager.getAllUsers();
    }

    /**
     * @param userId
     * @return String name of a specified user by int userId
     * @throws Exception
     */
    public String getUserNameById(int userId) throws Exception {
        for(User u : getAllUsers()) {
            if (u.getId() == userId) {
                return u.getName();
            }
        }
        return null;
    }






}
