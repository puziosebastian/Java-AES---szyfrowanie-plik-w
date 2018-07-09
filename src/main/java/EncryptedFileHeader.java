package main.java;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Sepu on 2018-05-17.
 */
@XmlRootElement(name = "EncryptedFileHeader")
//@XmlType(propOrder = {"Algorithm", "KeySize", "BlockSize", "CipherMode", "FileExtension", "IV", "SessionKey"})
public class EncryptedFileHeader {

    @XmlElements(@XmlElement(name = "Algorithm"))
    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    @XmlElements(@XmlElement(name = "KeySize"))
    public int getkSize() {
        return kSize;
    }

    public void setkSize(int kSize) {
        this.kSize = kSize;
    }

    @XmlElements(@XmlElement(name = "BlockSize"))
    public int getbSize() {
        return bSize;
    }

    public void setbSize(int bSize) {
        this.bSize = bSize;
    }

    @XmlElements(@XmlElement(name = "User"))
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @XmlElements(@XmlElement(name = "CipherMode"))
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @XmlElements(@XmlElement(name = "FileExtension"))
    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @XmlElements(@XmlElement(name = "IV"))
    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    @XmlElements(@XmlElement(name = "SessionKey"))
    public byte[] getSkey() {
        return skey;
    }

    public void setSkey(byte[] skey) {
        this.skey = skey;
    }

    private String alg;
    private int kSize;
    private int bSize;
    private String user;
    private String mode;
    private String ext;
    private byte[] iv;
    private byte[] skey;
}