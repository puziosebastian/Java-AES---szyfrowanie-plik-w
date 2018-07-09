package main.java;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sun.awt.shell.ShellFolder;

import javax.crypto.*;
import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class MainController {
    @FXML
    MenuItem newUserMenuItem;

    @FXML
    MenuItem openMenuItem;
    @FXML
    MenuItem exitButtonItem;

    @FXML
    ToggleGroup cipherMode;
    @FXML
    RadioMenuItem checkMenuItemECB;
    @FXML
    RadioMenuItem checkMenuItemCBC;
    @FXML
    RadioMenuItem checkMenuItemCFB;
    @FXML
    RadioMenuItem checkMenuItemOFB;

    @FXML
    ToggleGroup keySize;
    @FXML
    Menu keySizeMenu;
    @FXML
    RadioMenuItem checkMenuItem16;
    @FXML
    RadioMenuItem checkMenuItem24;
    @FXML
    RadioMenuItem checkMenuItem32;

    @FXML
    ListView userListItem;
    @FXML
    Label fileNameLabelItem;
    @FXML
    Label filePathLabelItem;
    @FXML
    ImageView fileImageItem;
    @FXML
    TextField fileNameTextField;
    @FXML
    ProgressBar progressBar;

    public void initialize() throws IOException {
        loadUsersList();
    }

    public void openFileChooser() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(Main.stage);
        String fileName = selectedFile.getName();
        String filePath = selectedFile.getAbsolutePath();
        setFileImage(selectedFile);
        setFileNameLabel(fileName);
        setPathNameLabel(filePath);
        progressBar.setProgress(0.0);
    }

    @FXML
    private void exitButtonAction() {
        Stage stage = Main.stage;
        stage.close();
    }

    public void openUserCreator() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/UserCreatorView.fxml"));

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("BSK - Use creator");
            stage.setScene(new Scene(root, 300, 300));
            stage.setResizable(false);
            stage.show();

            UserCreatorController uc = loader.getController();
            uc.addUserToList(userListItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUsersList() throws IOException {
        File file = new File(System.getProperty("user.home") + "\\AppData\\Local\\bsk\\" + "users.txt");
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String tmp;
        while ((tmp = br.readLine()) != null) {
            userListItem.getItems().add(tmp);
            br.readLine();
        }

        userListItem.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void ENCInit() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException, InvalidKeySpecException, InvalidAlgorithmParameterException, ShortBufferException, JAXBException {
        String filePath = filePathLabelItem.getText();
        String pathToSlash = filePath.substring(0, filePath.lastIndexOf('\\') + 1);

        String alg = "AES";

        RadioMenuItem modeItem = (RadioMenuItem) cipherMode.getSelectedToggle();
        String mode = modeItem.getText() + "/PKCS5Padding";

        String fileExt = filePath.substring(filePath.lastIndexOf('.'), filePath.length());

        IVGenerator ivg = new IVGenerator();
        byte[] iv = ivg.getIV();

        String user = userListItem.getSelectionModel().getSelectedItem().toString();

        RadioMenuItem kSizeItem = (RadioMenuItem) keySize.getSelectedToggle();
        int kSize = Integer.parseInt(kSizeItem.getText());
        SessionKeyGenerator skg = new SessionKeyGenerator();
        SecretKey key = skg.getSKey(kSize);

        File inputFile = new File(filePath);
        File outputFile = new File(pathToSlash + fileNameTextField.getText());

        Ciphering.encryptor(alg, mode, fileExt, iv, user, key, inputFile, outputFile, progressBar);
    }

    public void DECWindows() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/DECView.fxml"));

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("BSK - Use login and password");
            stage.setScene(new Scene(root, 300, 300));
            stage.setResizable(false);
            stage.show();

            DECController dc = loader.getController();
            dc.setNameLabel(filePathLabelItem.getText());
            dc.setNameField(fileNameTextField.getText());
            dc.addProgressBar(progressBar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFileNameLabel(String fileName) {
        fileNameLabelItem.setText(fileName);
    }

    public void setPathNameLabel(String filePath) {
        filePathLabelItem.setText(filePath);
    }

    public void setFileImage(File file) throws FileNotFoundException {
        ShellFolder sf = ShellFolder.getShellFolder(file);
        Icon icon = new ImageIcon(sf.getIcon(true), sf.getFolderType());

        BufferedImage bufferedImage = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        icon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);

        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        fileImageItem.setImage(image);
    }
}
