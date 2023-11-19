import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {
    private double x=0;
    private double y=0;
    @Override
    public void start(Stage stage) throws Exception { 

        FXMLLoader loader=new FXMLLoader(getClass().getResource("./views/Login.fxml"));
        Parent root=loader.load();
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
    }
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
