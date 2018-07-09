package main.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sepu on 2018-03-20.
 */
public class UserCreatorController {
    @FXML
    Label userLoginLabel;
    @FXML
    TextField userLoginTextField;
    @FXML
    Label userPassLabel;
    @FXML
    TextField userPassTextField;
    @FXML
    Button userAddButton;
    @FXML
    ListView listViewItem;

    public String getUserLogin() {
        return userLoginTextField.getText();
    }

    public String getUserPass() {
        return userPassTextField.getText();
    }

    public void addUser() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        String password = getUserPass();
        Pattern letter = Pattern.compile("[a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher hasLetter = letter.matcher(password);
        Matcher hasDigit = digit.matcher(password);
        Matcher hasSpecial = special.matcher(password);

        if (password.length() >= 8 && hasLetter.find() && hasDigit.find() && hasSpecial.find())
            System.out.println("");
        else
            return;

        String path = System.getProperty("user.home");
        path += "\\AppData\\Local\\bsk\\";
        FileOutputStream fos = new FileOutputStream(new File(path + "users.txt"), true);

        fos.write(getUserLogin().getBytes());
        fos.write("\n".getBytes());
        fos.write(getUserPass().getBytes());
        fos.write("\n".getBytes());
        fos.close();

        MessageDigest md = MessageDigest.getInstance("SHA1");
        byte[] passBytes = getUserPass().getBytes();
        byte[] hash = md.digest(passBytes);
        generateRSAKeyPair(hash);
    }

    public void addUserToList(ListView lv) {
        listViewItem = lv;
        listViewItem.getItems().add(getUserLogin());
    }

    public void generateRSAKeyPair(byte[] pass) throws NoSuchAlgorithmException, IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidKeySpecException, NoSuchPaddingException {
        RSAKeysPair rkp = new RSAKeysPair();
        rkp.saveKeys(getUserLogin(), pass);
    }
}
