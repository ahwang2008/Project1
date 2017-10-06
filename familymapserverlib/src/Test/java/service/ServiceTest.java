package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.Database;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 5/30/2017.
 */
public class ServiceTest {

    @Test
    public void generateRandomToken() throws Exception {
        Service service = new Service();
        String token = service.generateRandomToken();
        String token1 = service.generateRandomToken();
        assertNotEquals(token, token1);
    }
}