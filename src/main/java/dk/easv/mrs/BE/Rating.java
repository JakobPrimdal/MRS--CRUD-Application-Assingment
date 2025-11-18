package dk.easv.mrs.BE;

public class Rating {

    private int movieId = -1;
    private int userId = -1;
    private int score = -1;

    public Rating(int movieId, int userId, int score) {
        setMovieId(movieId);
        setUserId(userId);
        setScore(score);
    }

    private void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    private void setUserId(int userId) {
        this.userId = userId;
    }

    private void setScore(int score) {
        this.score = score;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

}
