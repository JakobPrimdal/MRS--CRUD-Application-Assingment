package dk.easv.mrs.BLL;

// Project imports
import dk.easv.mrs.BE.User;
import dk.easv.mrs.DAL.DB.UserDAO_DB;
import dk.easv.mrs.DAL.IUserDataAccess;

// Java imports
import java.util.List;

public class UserManager {

    private IUserDataAccess userDAO;

    public UserManager() {
        userDAO = new UserDAO_DB();
    }

    public List<User> getAllUsers() throws Exception {
        return userDAO.getAllUsers();
    }
}
