package it.polimi.ingsw.view.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
public class NicknamePopUpController extends ControllerGUI{
    @FXML
    private Text nicknameLable;
    @FXML
    private Text textLable;
    @FXML
    private ImageView playerImageView;
    /**
     * Method to show the nickname and the text.
     * @param nickname the nickname
     * @param txt the text
     */
    public void showNicknameAndText(String nickname,String txt, String path){
        nicknameLable.setText(nickname);
        textLable.setText(txt);
        try {
            Image image = new Image(path);
            playerImageView.setImage(image);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid image path: " + path);

        }


    }



}
