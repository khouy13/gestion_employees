package controller.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClose;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField userName;

    @FXML
    private PasswordField userPassword;

    @FXML

    private Connection connect;
    private ResultSet result;
    private PreparedStatement prepare;
    
    @FXML
    void close(ActionEvent event) {
               System.exit(0);
    }
    private double x=0;
    private double y=0;
    @FXML
    void login(ActionEvent event) {
                String sql="Select * FROM admin WHERE username=? and password=?";
                connect= Database.connectDB();
                try {
                    prepare=connect.prepareStatement(sql);
                    prepare.setString(1,userName.getText());
                    prepare.setString(2,userPassword.getText());
                    result=prepare.executeQuery();
                    Alert alert;
                    if(userName.getText().isEmpty() || userPassword.getText().isEmpty()){
                       alert=new Alert(AlertType.ERROR);
                       alert.setTitle("ERROR MESSAGE");
                       alert.setHeaderText("");
                       alert.setContentText("fill all filieds");
                       alert.showAndWait();
                    }else{
                        
                        if(result.next()){
                       GetData.adminName=userName.getText();
                        
                        //   alert=new Alert(AlertType.INFORMATION);
                        //   alert.setTitle("Information Message");
                        //   alert.setHeaderText("");
                        //   alert.setContentText("successffuly Login");
                        //   alert.showAndWait();
                           loginBtn.getScene().getWindow().hide();
                           Parent root=FXMLLoader.load(getClass().getResource("../views/Home.fxml"));
                           Stage stage=new Stage();

                           Scene scene=new Scene(root);
                           scene.getStylesheets().add("./css/css.css");
                           root.setOnMousePressed((MouseEvent e)->{
                               x=e.getSceneX();
                               y=e.getSceneY();
                           });
                           root.setOnMouseDragged((MouseEvent e)->{
                               stage.setX(e.getSceneX()-x);
                               stage.setY(e.getSceneY()-y);
                               stage.setOpacity(.8);
                           });
                           root.setOnMouseReleased((MouseEvent e)->{
                           stage.setOpacity(1);
                           });
                           stage.initStyle(StageStyle.TRANSPARENT);
                           stage.setScene(scene);
                           stage.show();
  
                        }else{
                          alert=new Alert(AlertType.ERROR);
                          alert.setTitle("ERROR MESSAGE");
                          alert.setHeaderText("");
                          alert.setContentText("wrong username/password");
                          alert.showAndWait();
                        }
                    }
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        Alert alert=new Alert(AlertType.ERROR);
                          alert.setTitle("ERROR MESSAGE");
                          alert.setHeaderText("");
                          alert.setContentText(e.getMessage());
                          alert.showAndWait();
                }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }    
}
