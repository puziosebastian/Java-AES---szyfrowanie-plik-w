package main.java;

import javafx.scene.control.ProgressBar;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public class Ciphering {

    public static void encryptor(String alg, String mode, String ext, byte[] iv, String user, SecretKey skey, File inputFile, File outputFile, ProgressBar progressBar) throws InvalidKeyException, IOException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, JAXBException, ShortBufferException, InvalidKeySpecException {
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(alg + "/" + mode);

        if (mode.charAt(0) == 'E') {
            iv = null;
            cipher.init(Cipher.ENCRYPT_MODE, skey);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, skey, ivspec);
        }

        int blockSize = cipher.getBlockSize();
        int outputSize = cipher.getOutputSize(blockSize);

        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) inputFile.length()];
        inputStream.read(inputBytes);

        byte[] outputBytes = new byte[outputSize];

        float f, a, b = inputBytes.length;

        int i = 0;

        for (; i <= inputBytes.length - blockSize; i = i + blockSize) {
            int outLength = cipher.update(inputBytes, i, blockSize, outputBytes);
            if (i % 10000 == 0) {
                a = i;
                f = a / b;
                //System.out.println(f * 100 + " %");
                progressBar.setProgress(f);
            }
        }
        if (i == inputBytes.length)
            outputBytes = cipher.doFinal();
        else {
            outputBytes = cipher.doFinal(inputBytes, i, inputBytes.length - i);
        }

        RSAKeysPair rkp = new RSAKeysPair();
        PublicKey pubKey = rkp.loadPublicKey(user);
        byte[] encKey = rkp.encryptSKey(pubKey, skey.getEncoded());

        HeaderGetAndSet hgas = new HeaderGetAndSet();
        hgas.setHeader(outputFile, alg, skey.getEncoded().length, user, blockSize, mode, ext, iv, encKey);

        byte[] outputBytess = cipher.doFinal(inputBytes);
        FileOutputStream outputStream = new FileOutputStream(outputFile, true);
        outputStream.write(outputBytess);

        inputStream.close();
        outputStream.close();

    }
}

