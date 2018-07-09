package main.java;

import java.io.*;

/**
 * Created by Sepu on 2018-05-24.
 */
public class ReadContentFromFile {

    public byte[][] splitContent(File file, String content) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        int len = 0;
        byte[] buffer = new byte[1024];
        byte[] tmpBuffer = new byte[512];
        byte[] newLine = {10};

        String tmpString = br.readLine();
        buffer = tmpString.getBytes();
        len += (buffer.length + newLine.length);
        buffer = concatenateByteArrays(buffer, newLine);

        while (!(tmpString = br.readLine()).contains(content)) {
            tmpBuffer = tmpString.getBytes();
            len += (tmpBuffer.length + newLine.length);
            buffer = concatenateByteArrays(buffer, tmpBuffer);
            buffer = concatenateByteArrays(buffer, newLine);
        }
        tmpBuffer = tmpString.getBytes();
        len += tmpBuffer.length;
        buffer = concatenateByteArrays(buffer, tmpBuffer);
        buffer = concatenateByteArrays(buffer, newLine);
        fis.close();

        len++;

        FileInputStream fis2 = new FileInputStream(file);
        byte[] encContent = new byte[(int) (file.length() - len)];
        fis2.skip(len);
        fis2.read(encContent);
        fis2.close();

        byte[][] ret = new byte[2][];
        ret[0] = buffer;
        ret[1] = encContent;

        return ret;
    }

    byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
