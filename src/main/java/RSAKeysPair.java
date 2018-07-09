package main.java;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Sepu on 2018-05-17.
 */
public class RSAKeysPair {

    public KeyPair RSAKeysGenerator() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();

        return kp;
    }

    public byte[] encryptSKey(PublicKey pubKey, byte[] sKey) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        return cipher.doFinal(sKey);
    }

    public byte[] decryptSKey(PrivateKey privKey, byte[] encryptedSKey) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privKey);

        return cipher.doFinal(encryptedSKey);
    }

    public void saveKeys(String login, byte[] pass) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidKeySpecException, NoSuchPaddingException, IOException {
        KeyPair kp = RSAKeysGenerator();
        SecretKeySpec passKey = new SecretKeySpec(pass, 0, 16, "AES");

        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, passKey);
        byte[] prvKey = kp.getPrivate().getEncoded();
        byte[] encPrvKey = c.doFinal(prvKey);

        FileOutputStream fos1 = new FileOutputStream(System.getProperty("user.home") + "\\AppData\\Local\\bsk\\PublicKeys\\" + login + ".pub");
        FileOutputStream fos2 = new FileOutputStream(System.getProperty("user.home") + "\\AppData\\Local\\bsk\\PrivateKeys\\" + login + ".priv");
        fos1.write(kp.getPublic().getEncoded());
        fos2.write(encPrvKey);
        fos1.close();
        fos2.close();
    }

    public PublicKey loadPublicKey(String login) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        FileInputStream fis = new FileInputStream(System.getProperty("user.home") + "\\AppData\\Local\\bsk\\PublicKeys\\" + login + ".pub");
        byte[] publicKeyBytes = new byte[1024];
        fis.read(publicKeyBytes);
        fis.close();

        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        return publicKey;
    }

    public PrivateKey loadPrivateKey(String login, SecretKeySpec pass) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        File f = new File(System.getProperty("user.home") + "\\AppData\\Local\\bsk\\PrivateKeys\\" + login + ".priv");
        FileInputStream fis = new FileInputStream(f);
        byte[] privateKeyBytes = new byte[(int) f.length()];
        fis.read(privateKeyBytes);
        fis.close();

        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, pass);
        byte[] decPrivKey = c.doFinal(privateKeyBytes);

        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(decPrivKey));

        return privateKey;
    }
}
