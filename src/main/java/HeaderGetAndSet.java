package main.java;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sepu on 2018-05-22.
 */
public class HeaderGetAndSet {

    public void setHeader(File file, String alg, int kSize, String user, int bSize, String mode, String ext, byte[] iv, byte[] skey) throws JAXBException, IOException {
        EncryptedFileHeader efh = new EncryptedFileHeader();
        efh.setAlg(alg);
        efh.setkSize(kSize);
        efh.setbSize(bSize);
        efh.setUser(user);
        efh.setMode(mode);
        efh.setExt(ext);
        efh.setIv(iv);
        efh.setSkey(skey);

        JAXBContext jaxbContext = JAXBContext.newInstance(EncryptedFileHeader.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(efh, file);
    }

    public EncryptedFileHeader getHeader(byte[] header) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(EncryptedFileHeader.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        ByteArrayInputStream bais = new ByteArrayInputStream(header);
        EncryptedFileHeader efh = (EncryptedFileHeader) jaxbUnmarshaller.unmarshal(bais);

        return efh;
    }
}