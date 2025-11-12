package dk.easv.mrs.BE;

public class Movie {

    private int id;
    private String title;
    private int year;

    public Movie(int id, int year, String title) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of this Movie
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of this Movie
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return id + ": " + title + " ("+year+")"
     */
    @Override
    public String toString()
    {
        return id + ": " + title + " ("+year+")";
    }
}
