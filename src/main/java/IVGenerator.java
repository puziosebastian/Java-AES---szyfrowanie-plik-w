package main.java;

import java.io.IOException;
import java.security.SecureRandom;

/**
 * Created by Sepu on 2018-05-17.
 */
public class IVGenerator {

    public static byte[] getIV() throws IOException {
        SecureRandom srandom = new SecureRandom();
        byte[] iv = new byte[16];
        srandom.nextBytes(iv);

        return iv;
    }
}
