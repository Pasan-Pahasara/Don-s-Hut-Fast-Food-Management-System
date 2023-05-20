package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OrderDetails;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrderDetailsViewFormController implements Initializable {
    public TableView<OrderDetails> tblOrderDetails;
    public TableColumn colOrderId;
    public TableColumn colItemCode;
    public TableColumn colItemName;
    public TableColumn colCustomerId;
    public TableColumn colQty;
    public TableColumn colDiscount;
    public TableColumn colPrice;
    public TextField txtSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Table Load
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            loadAllOrderDetails();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Load All Orders
    private void loadAllOrderDetails() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM `Order Details`");
        ObservableList<OrderDetails> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new OrderDetails(
                            result.getString("OrderID"),
                            result.getString("ItemCode"),
                            result.getString("Description"),
                            result.getString("CustomerID"),
                            result.getInt("OrderQty"),
                            result.getDouble("Discount"),
                            result.getDouble("Price")
                    )
            );
        }
        tblOrderDetails.setItems(obList);
    }

    //Search
    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (txtSearch.getText().trim().isEmpty()) {
            loadAllOrderDetails();

        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM `Order Details` WHERE OrderID=?", txtSearch.getText());
            ObservableList<OrderDetails> obList = FXCollections.observableArrayList();

            while (result.next()) {
                obList.add(
                        new OrderDetails(
                                result.getString("OrderID"),
                                result.getString("ItemCode"),
                                result.getString("Description"),
                                result.getString("CustomerID"),
                                result.getInt("OrderQty"),
                                result.getDouble("Discount"),
                                result.getDouble("Price")
                        )
                );
            }
            tblOrderDetails.setItems(obList);
        }
    }
}