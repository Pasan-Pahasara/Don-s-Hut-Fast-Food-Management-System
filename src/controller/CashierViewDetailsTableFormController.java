package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cashier;
import model.Order;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class CashierViewDetailsTableFormController implements Initializable {
    public TableView<Cashier> tblEmployee;
    public TableColumn colEmpId;
    public TableColumn colEmpName;
    public TableColumn colEmpPassword;
    public TableColumn colEmpNic;
    public TableColumn colEmpContact;
    public TableColumn colEmpDescription;
    public TableColumn colEmpAddress;
    public TableColumn colEmpSalary;
    public TextField txtSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //-------------------------------Table Load---------------------------------//

        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colEmpPassword.setCellValueFactory(new PropertyValueFactory<>("empPassword"));
        colEmpNic.setCellValueFactory(new PropertyValueFactory<>("empNic"));
        colEmpContact.setCellValueFactory(new PropertyValueFactory<>("empContact"));
        colEmpDescription.setCellValueFactory(new PropertyValueFactory<>("empDescription"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
        colEmpSalary.setCellValueFactory(new PropertyValueFactory<>("empSalary"));


        try {
            loadAllCashier();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    //Load All Cashiers
    private void loadAllCashier() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Cashier");
        ObservableList<Cashier> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new Cashier(
                            result.getString("CashierID"),
                            result.getString("CashierName"),
                            result.getString("CashierPsw"),
                            result.getString("CashierNIC"),
                            result.getString("CashierCNumber"),
                            result.getString("CashierDescription"),
                            result.getString("CashierAddress"),
                            result.getDouble("CashierSalary")

                    )
            );
        }
        tblEmployee.setItems(obList);
    }

    //Cashier ID Search
    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtSearch.getText().trim().isEmpty()) {
            loadAllCashier();

        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM Cashier WHERE CashierID=?", txtSearch.getText());
            ObservableList<Cashier> cashierObservableList = FXCollections.observableArrayList();

            while (result.next()) {
                cashierObservableList.add(
                        new Cashier(
                                result.getString("CashierID"),
                                result.getString("CashierName"),
                                result.getString("CashierPsw"),
                                result.getString("CashierNIC"),
                                result.getString("CashierCNumber"),
                                result.getString("CashierDescription"),
                                result.getString("CashierAddress"),
                                result.getDouble("CashierSalary")
                        )
                );
            }
            tblEmployee.setItems(cashierObservableList);
        }
    }
}
