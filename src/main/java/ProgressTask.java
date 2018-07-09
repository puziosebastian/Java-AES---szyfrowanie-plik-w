package main.java;

import javafx.concurrent.Task;

import javax.crypto.Cipher;

public class ProgressTask<V> extends Task<V> {
    @Override
    protected V call() throws Exception {
        return null;
    }

    int i;
    byte[] inputBytes;
    int blockSize;
    Cipher cipher;
    byte[] outputBytes;
    float a;
    float f;
    float b;

    public ProgressTask(int i, byte[] inputBytes, int blockSize, Cipher cipher, byte[] outputBytes, float a, float f, float b) {
        this.i = i;
        this.inputBytes = inputBytes;
        this.blockSize = blockSize;
        this.cipher = cipher;
        this.outputBytes = outputBytes;
        this.a = a;
        this.f = f;
        this.b = b;
    }
}
