package dk.easv.mrs.BLL.util;
// Java imports
import java.util.ArrayList;
import java.util.List;

// Project imports
import dk.easv.mrs.BE.Movie;

public class MovieSearcher {

    /**
     * Compares a given movie to List of Movie
     * @param searchBase
     * @param query
     * @return List of searchResult
     */
    public List<Movie> search(List<Movie> searchBase, String query) {
        List<Movie> searchResult = new ArrayList<>();

        for (Movie movie : searchBase) {
            if(compareToMovieTitle(query, movie) || compareToMovieYear(query, movie))
            {
                searchResult.add(movie);
            }
        }

        return searchResult;
    }

    /**
     * Compares Movie to given year
     * @param query
     * @param movie
     * @return movies with the given year production date
     */
    private boolean compareToMovieYear(String query, Movie movie) {
        return Integer.toString(movie.getYear()).contains(query);
    }

    /**
     *
     * @param query
     * @param movie
     * @return true if the specified movie exists in the list of movies
     */
    private boolean compareToMovieTitle(String query, Movie movie) {
        return movie.getTitle().toLowerCase().contains(query.toLowerCase());
    }

}
