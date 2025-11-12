package dk.easv.mrs.DAL;
// Java imports
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Project imports
import dk.easv.mrs.BE.Movie;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.APPEND;


public class MovieDAO_File implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private static final Path MOVIES_FILE_PATH = Path.of(MOVIES_FILE);

    //The @Override annotation is not required, but is recommended for readability
    // and to force the compiler to check and generate error msg. if needed etc.
    //@Override

    /**
     * List of movies type Movie
     * @return null
     * @throws IOException
     */
    public List<Movie> getAllMovies() throws IOException {
        // Read all lines from file
        List<String> lines = Files.readAllLines(MOVIES_FILE_PATH);
        List<Movie> movies = new ArrayList<>();

// Parse each line as movie
        for (String line: lines) {
            String[] separatedLine = line.split(",");

            int id = Integer.parseInt(separatedLine[0]);
            int year = Integer.parseInt(separatedLine[1]);
            String title = separatedLine[2];
            if(separatedLine.length > 3)
            {
                for(int i = 3; i < separatedLine.length; i++)
                {
                    title += "," + separatedLine[i];
                }
            }
            Movie movie = new Movie(id, year, title);
            movies.add(movie);
        }

        return movies;
    }

    /**
     * Creates Movie
     * @param newMovie
     * @return null
     * @throws Exception
     */
    @Override
    public Movie createMovie(Movie newMovie) throws Exception {
        int newId = -1;
        for (Movie movie:getAllMovies())
            newId = movie.getId();
        newId++;


        String newMovieLine = newId+","+newMovie.getYear()+","+newMovie.getTitle();
        Files.write(MOVIES_FILE_PATH, (newMovieLine+"\r\n").getBytes(), APPEND);

        return new Movie(newId, newMovie.getYear(), newMovie.getTitle());
    }

    /**
     * Updates movie
     * @param movie
     * @throws Exception
     */
    @Override
    public void updateMovie(Movie movie) throws Exception {

        try
        {
            // Read all movie lines in list
            List<String> movies = Files.readAllLines(MOVIES_FILE_PATH);

            // Iterate through all lines and look for the right one (movie)
            for (int i = 0; i < movies.size(); i++)
            {
                // Split each line into atomic parts
                String[] separatedLine = movies.get(i).split(",");

                // Make sure we have a valid movie with all parts
                if(separatedLine.length == 3) {
                    // individual movie items
                    int id = Integer.parseInt(separatedLine[0]);

                    // Check if the id is equal to movie.getId()
                    if (id == movie.getId()) {
                        String updatedMovieLine = movie.getId() + "," + movie.getYear() + "," + movie.getTitle();
                        movies.set(i, updatedMovieLine);
                        break;
                    }
                }
            }

            // Create new temp file
            Path tempPathFile = Paths.get(MOVIES_FILE+ "_TEMP");
            Files.createFile(tempPathFile);

            // For all lines...
            for (String line: movies)
                Files.write(tempPathFile, (line + "\r\n").getBytes(),APPEND);

            // Overwrite the old file with temp file
            Files.copy(tempPathFile, MOVIES_FILE_PATH, REPLACE_EXISTING);
            Files.deleteIfExists(tempPathFile);

        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("An error occurred");
        }
    }

    /**
     * Deletes Movie
     * @param movie
     * @throws Exception
     */
    @Override
    public void deleteMovie(Movie movie) throws Exception {
        try
        {
            // Read all movie lines in list
            List<String> movies = Files.readAllLines(MOVIES_FILE_PATH);
            movies.remove(movie.getId() + "," + movie.getYear() + "," + movie.getTitle());


            // Create new temp file
            Path tempPathFile = Paths.get(MOVIES_FILE+ "_TEMP");
            Files.createFile(tempPathFile);

            // For all lines...
            for (String line: movies)
                Files.write(tempPathFile, (line + "\r\n").getBytes(),APPEND);

            // Overwrite the old file with temp file
            Files.copy(tempPathFile, MOVIES_FILE_PATH, REPLACE_EXISTING);
            Files.deleteIfExists(tempPathFile);


        } catch (IOException e) {
            throw new Exception("An error occurred");
        }
    }
}