package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.Database;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class ClearServiceTest {

    private Database db;


    @Before
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
    }

    @Test
    public void clear() throws Exception {
        ClearService cs = new ClearService();
        db.createTables();
        assertTrue(cs.clear());
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }
}