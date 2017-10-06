package dataAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class DatabaseTest {

    private Database db;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
    }

    @Test
    public void createTables() throws Exception {
        assertTrue(db.createTables());
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }
}