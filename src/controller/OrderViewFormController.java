package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import model.OrderDetails;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrderViewFormController implements Initializable {
    public TableView<Order> tblOrder;
    public TableColumn colOrderId;
    public TableColumn colCustomerId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TextField txtSearch;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Table Load
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        try {
            loadAllOrder();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Load All Orders
    private void loadAllOrder() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM `Order`");
        ObservableList<Order> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new Order(
                            result.getString("OrderID"),
                            result.getString("CustomerID"),
                            result.getString("OrderDate"),
                            result.getString("OrderTime")

                    )
            );
        }
        tblOrder.setItems(obList);
    }

    //Order ID Search
    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtSearch.getText().trim().isEmpty()) {
            loadAllOrder();

        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM `Order` WHERE OrderID=?", txtSearch.getText());
            ObservableList<Order> obList = FXCollections.observableArrayList();

            while (result.next()) {
                obList.add(
                        new Order(
                                result.getString("OrderID"),
                                result.getString("CustomerID"),
                                result.getString("OrderDate"),
                                result.getString("OrderTime")
                        )
                );
            }
            tblOrder.setItems(obList);
        }
    }
}