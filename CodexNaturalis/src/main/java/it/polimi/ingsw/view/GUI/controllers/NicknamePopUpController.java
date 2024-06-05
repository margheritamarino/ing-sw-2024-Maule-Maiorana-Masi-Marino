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
    public void showNicknameAndText(String nickname,String txt){
        nicknameLable.setText(nickname);
        textLable.setText(txt);

    }



}
