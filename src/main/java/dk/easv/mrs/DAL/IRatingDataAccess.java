package dk.easv.mrs.DAL;

// Project imports
import dk.easv.mrs.BE.Rating;

// Java imports
import java.util.List;

public interface IRatingDataAccess {

    List<Rating> getAllRatings() throws Exception;

}

