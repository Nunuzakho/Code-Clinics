package wethinkcode.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 3.3
 */
public class Finder {

    private final Connection connection;

    /**
     * Create an instance of the Finder object using the provided database connection
     *
     * @param connection The JDBC connection to use
     */
    public Finder(Connection connection) {
        this.connection = connection;
    }

    /**
     * 3.3 (part 1) Complete this method
     * <p>
     * Finds all genres in the database
     *
     * @return a list of `Genre` objects
     * @throws SQLException the query failed
     */
    public List<Genre> findAllGenres() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM Genres";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        ResultSet set = statement.getResultSet();
        while(set.next()){
            genres.add(new Genre(set.getString("code"), set.getString("description")));

        }

        return genres;
    }

    /**
     * 3.3 (part 2) Complete this method
     * <p>
     * Finds all genres in the database that have specific substring in the description
     *
     * @param pattern The pattern to match
     * @return a list of `Genre` objects
     * @throws SQLException the query failed
     */
    public List<Genre> findGenresLike(String pattern) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * from Genres WHERE description like\"%"+pattern+"%\"";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        ResultSet set = statement.getResultSet();
        while(set.next()){
            genres.add(new Genre(set.getString("code"), set.getString("description")));

        }

        return genres;
    }

    /**
     * 3.3 (part 3) Complete this method
     * <p>
     * Finds all books with their genres
     *
     * @return a list of `BookGenreView` objects
     * @throws SQLException the query failed
     */
    public List<BookGenreView> findBooksAndGenres() throws SQLException {
        List<BookGenreView> bookGenreViewList = new ArrayList<>();

        String sql = "SELECT b.title, g.description FROM Books b, Genres g WHERE b.genre_code = g.code";


        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        ResultSet set = statement.getResultSet();

        while(set.next()){
            bookGenreViewList.add(new BookGenreView(set.getString(1), set.getString(2)));
        }

        return bookGenreViewList;
    }

    /**
     * 3.3 (part 4) Complete this method
     * <p>
     * Finds the number of books in a genre
     *
     * @return the number of books in the genre
     * @throws SQLException the query failed
     */
    public int findNumberOfBooksInGenre(String genreCode) throws SQLException {
        int count = 0;
        String sql = "Select * FROM Books b, Genres g WHERE b.genre_code = g.code AND g.code = \""+genreCode+"\"";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        ResultSet set = statement.getResultSet();

        while(set.next()){
            count++;
        }

        return count;
    }
}
