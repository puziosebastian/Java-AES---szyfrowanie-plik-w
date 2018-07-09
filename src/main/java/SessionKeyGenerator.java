package main.java;

import javax.crypto.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Sepu on 2018-05-17.
 */
public class SessionKeyGenerator {

    public SecretKey getSKey(int kSize) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
        rand.generateSeed(kSize * 8);
        keygen.init(kSize * 8, rand);
        SecretKey sessionKey = keygen.generateKey();

        return sessionKey;
    }
}
