package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class UILoader {
    public static void SetUi(String location, AnchorPane allLoaderContext) throws IOException {

        /**----------------------------------FULL CODE
         URL resource = getClass().getResource("../view/" + location + ".fxml");
         Parent load = FXMLLoader.load(resource);
         allLoaderContext.getChildren().clear();
         allLoaderContext.getChildren().add(load);



         URL resource = getClass().getResource("../View/LoginForm.fxml");
         Parent load = FXMLLoader.load(resource);
         Stage window = (Stage) logoutPainContext.getScene().getWindow();
         window.setScene(new Scene(load));

         */
        allLoaderContext.getChildren().clear();
        allLoaderContext.getChildren().add(FXMLLoader.load(UILoader.class.getResource("../views/" + location + ".fxml")));
    }

    public static void LogOutHandler(String path, AnchorPane MainContext) throws IOException {
        /**
         URL resource = UILoader.class.getResource("../View/"+path +".fxml");
         Parent load = FXMLLoader.load(resource);
         Stage window = (Stage) MainContext.getScene().getWindow();
         window.setScene(new Scene(load));
         */
        Stage window = (Stage) MainContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(UILoader.class.getResource("../Views/" + path + ".fxml"))));
    }

    public static void SetUiCloseUnderTheAnchorpane(String Owner, AnchorPane FirstPageAnchorPaneContext) throws IOException {
        /**
         URL resource = UILoader.class.getResource("../view/" + Owner + ".fxml");
         Parent load = FXMLLoader.load(resource);
         FirstPageAnchorPaneContext.getChildren().clear();
         FirstPageAnchorPaneContext.getChildren().add(load);
         */
        FirstPageAnchorPaneContext.getChildren().clear();
        FirstPageAnchorPaneContext.getChildren().add(FXMLLoader.load(UILoader.class.getResource("../views/" + Owner + ".fxml")));
    }

    public static void CashierUI(String pathToUI, AnchorPane OwnerUnderCashier) throws IOException {

        /**
         OwnerUnderCashier.getChildren().clear();
         URL resource = UILoader.class.getResource("../view/"+pathToUI+".fxml");
         Parent load = FXMLLoader.load(resource);
         Stage window = (Stage) OwnerUnderCashier.getScene().getWindow();
         window.setScene(new Scene(load));
         */

        OwnerUnderCashier.getChildren().clear();
        Stage window = (Stage) OwnerUnderCashier.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(UILoader.class.getResource("../views/" + pathToUI + ".fxml"))));


    }

    public static void ManagementPopUpUI(String pathToUI) throws IOException {

        /**
         OwnerUnderCashier.getChildren().clear();
         URL resource = UILoader.class.getResource("../view/"+pathToUI+".fxml");
         Parent load = FXMLLoader.load(resource);
         Stage window = (Stage) OwnerUnderCashier.getScene().getWindow();
         window.setScene(new Scene(load));
         */

        Parent parent = FXMLLoader.load(UILoader.class.getResource("../views/" + pathToUI + ".fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public static void InfoUI(String pathToUI) throws IOException {

        /**
         OwnerUnderCashier.getChildren().clear();
         URL resource = UILoader.class.getResource("../view/"+pathToUI+".fxml");
         Parent load = FXMLLoader.load(resource);
         Stage window = (Stage) OwnerUnderCashier.getScene().getWindow();
         window.setScene(new Scene(load));
         */

        Parent parent = FXMLLoader.load(UILoader.class.getResource("../views/" + pathToUI + ".fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(new Scene(parent));
        stage.show();
    }
}