package main.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by User on 2018-05-11.
 */
public class DECController {

    @FXML
    TextField logItem;
    @FXML
    TextField passItem;
    @FXML
    Label fileNameLabel;
    @FXML
    Label fileNameText;
    @FXML
    Button confirmButton;
    @FXML
    ProgressBar progressBar;

    public void DECInit() throws IOException, NoSuchAlgorithmException, ShortBufferException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, JAXBException, IllegalBlockSizeException {
        String filePath = fileNameLabel.getText();
        String pathToSlash = filePath.substring(0, filePath.lastIndexOf('\\') + 1);

        File inputFile = new File(filePath);
        File outputFile = new File(pathToSlash + fileNameText.getText());

        Deciphering.decryptor(inputFile, outputFile, pathToSlash, logItem.getText(), passItem.getText(), progressBar);
    }

    public void setNameLabel(String label) {
        fileNameLabel.setText(label);
    }

    public void setNameField(String field) {
        fileNameText.setText(field);
    }

    public void addProgressBar(ProgressBar pb){
        progressBar = pb;
    }
}
