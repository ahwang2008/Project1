package service;

import java.security.SecureRandom;

import dataAccess.AuthTokenDao;

/**
 * Created by Hwang on 5/27/2017.
 */

public class Service {

    protected long expPeriod = AuthTokenDao.getAuthTokenTimeout();

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();
    int len = 8;

    public String generateRandomToken(){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }
}
