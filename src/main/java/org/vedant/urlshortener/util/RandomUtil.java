package org.vedant.urlshortener.util;

import java.security.SecureRandom;

public class RandomUtil {

    private static final String CHAR_POOL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom random = new SecureRandom();

    private RandomUtil(){}

    public static String randomChar(int length){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++){
            int idx = random.nextInt(CHAR_POOL.length());
            sb.append(CHAR_POOL.charAt(idx));
        }
        return sb.reverse().toString();
    }
}
