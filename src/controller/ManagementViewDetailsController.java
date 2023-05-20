package controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import util.UILoader;

import java.io.IOException;

public class ManagementViewDetailsController {
    public AnchorPane mainPane1;


    public void customerDetailsOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("CustomerViewDetailsTableForm",mainPane1);
    }

    public void orderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("OrderViewDetailsTableForm",mainPane1);
    }

    public void itemDetailsOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("ItemViewDetailsTableForm",mainPane1);
    }

    public void cashierDetailsOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("CashierViewDetailsTableForm",mainPane1);
    }
}
