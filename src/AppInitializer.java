import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/views/LoaderForm.fxml"))));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        try {
            Image anotherIcon = new Image(getClass().getResourceAsStream("icon.png"));
            primaryStage.getIcons().add(anotherIcon);
        }catch (NullPointerException e){}
        primaryStage.show();
    }
}
