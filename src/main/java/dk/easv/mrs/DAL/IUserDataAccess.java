package dk.easv.mrs.DAL;

// Project imports
import dk.easv.mrs.BE.User;

// Java imports
import java.util.List;

public interface IUserDataAccess {

    List<User> getAllUsers();

}
