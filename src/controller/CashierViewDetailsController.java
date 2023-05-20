package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import util.UILoader;

import java.io.IOException;
import java.net.URL;

public class CashierViewDetailsController {
    public AnchorPane mainPane;

    public void customerDetailsOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("CustomerViewDetailsTableForm",mainPane);
    }

    public void itemDetailsOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("ItemViewDetailsTableForm",mainPane);
    }
}
