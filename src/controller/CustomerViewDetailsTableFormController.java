package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.Order;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerViewDetailsTableFormController implements Initializable {
    public AnchorPane customerFormContext;
    public TableView <Customer>tblCustomer;
    public TableColumn tblCusId;
    public TableColumn tblCusTitle;
    public TableColumn tblCusName;
    public TableColumn tblCusAddress;
    public TableColumn tblCusCity;
    public TableColumn tblCusProvince;
    public TableColumn tblCusCode;
    public TableColumn tblCusDate;
    public TableColumn tblCusTime;
    public TextField txtSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblCusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        tblCusTitle.setCellValueFactory(new PropertyValueFactory<>("cusTitle"));
        tblCusName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        tblCusAddress.setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        tblCusCity.setCellValueFactory(new PropertyValueFactory<>("cusCity"));
        tblCusProvince.setCellValueFactory(new PropertyValueFactory<>("cusProvince"));
        tblCusCode.setCellValueFactory(new PropertyValueFactory<>("cusPostalCode"));
        tblCusDate.setCellValueFactory(new PropertyValueFactory<>("cusDate"));
        tblCusTime.setCellValueFactory(new PropertyValueFactory<>("cusTime"));

        try {
            loadAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Load All Customers
    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Customer");
        ObservableList<Customer> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new Customer(
                            result.getString("CustomerID"),
                            result.getString("CustomerTitle"),
                            result.getString("CustomerName"),
                            result.getString("CustomerAddress"),
                            result.getString("City"),
                            result.getString("Province"),
                            result.getString("PostCode"),
                            result.getString("Date"),
                            result.getString("Time")
                    )
            );
        }
        tblCustomer.setItems(obList);

    }

    //Customer ID Search
    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtSearch.getText().trim().isEmpty()) {
            loadAllCustomers();

        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE CustomerID=?", txtSearch.getText());
            ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

            while (result.next()) {
                customerObservableList.add(
                        new Customer(
                                result.getString("CustomerID"),
                                result.getString("CustomerTitle"),
                                result.getString("CustomerName"),
                                result.getString("CustomerAddress"),
                                result.getString("City"),
                                result.getString("Province"),
                                result.getString("PostCode"),
                                result.getString("Date"),
                                result.getString("Time")
                        )
                );
            }
            tblCustomer.setItems(customerObservableList);
        }
    }
}
