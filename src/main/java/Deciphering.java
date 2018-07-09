package main.java;

import javafx.scene.control.ProgressBar;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Sepu on 2018-05-17.
 */
public class Deciphering {

    static void decryptor(File inputFile, File outputFile, String pathToSlash, String login, String pass, ProgressBar progressBar) throws InvalidKeyException, IOException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, JAXBException, ShortBufferException, InvalidKeySpecException {
        ReadContentFromFile rcff = new ReadContentFromFile();
        byte[][] buffer = rcff.splitContent(inputFile, "</EncryptedFileHeader>");

        HeaderGetAndSet hgas = new HeaderGetAndSet();
        EncryptedFileHeader efh = hgas.getHeader(buffer[0]);

        String user = efh.getUser();
        if (!user.equals(login))
            return;

        File file = new File(System.getProperty("user.home") + "\\AppData\\Local\\bsk\\" + "users.txt");
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String tmp;
        while ((tmp = br.readLine()) != null) {
            if (login.equals(tmp)) {
                tmp = br.readLine();
                if (!tmp.equals(pass))
                    return;
            }
        }

        String alg = efh.getAlg();

        MessageDigest md = MessageDigest.getInstance("SHA1");
        byte[] passBytes = pass.getBytes();
        byte[] hash = md.digest(passBytes);
        SecretKeySpec passKey = new SecretKeySpec(hash, 0, 16, "AES");

        String mode = efh.getMode();
        byte[] skeyByte = efh.getSkey();
        int bSize = efh.getbSize();

        String ext = efh.getExt();
        int kSize = efh.getkSize();
        Cipher cipher = Cipher.getInstance(alg + "/" + mode);

        RSAKeysPair rkp = new RSAKeysPair();
        PrivateKey privKey = rkp.loadPrivateKey(user, passKey);
        byte[] decKey = rkp.decryptSKey(privKey, skeyByte);
        SecretKeySpec skey = new SecretKeySpec(decKey, 0, decKey.length, alg);

        if (mode.charAt(0) == 'E') {
            cipher.init(Cipher.DECRYPT_MODE, skey);
        } else {
            byte[] iv = efh.getIv();
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, skey, ivspec);
        }

        FileOutputStream fos = new FileOutputStream(outputFile);
        buffer[1] = cipher.doFinal(buffer[1]);
        fos.write(buffer[1]);
        fos.close();
        progressBar.setProgress(1.0);

        outputFile.renameTo(new File(pathToSlash + outputFile.getName() + ext));
    }
}