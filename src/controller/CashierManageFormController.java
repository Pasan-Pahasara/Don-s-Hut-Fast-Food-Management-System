package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodTextRun;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Cashier;
import model.OrderDetails;
import util.CrudUtil;
import util.Notification;
import util.ValidationUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CashierManageFormController implements Initializable {
    public AnchorPane employeeFormContext;
    public TableView<Cashier> tblEmployee;
    public TextField txtSearch;
    public TextField txtEmpID;
    public TextField txtEmpName;
    public TextField txtEmpPassword;
    public TextField txtEmpNic;
    public TextField txtEmpContact;
    public TextField txtEmpDescription;
    public TextField txtEmpAddress;
    public TextField txtEmpSalary;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public TableColumn colEmpId;
    public TableColumn colEmpName;
    public TableColumn colEmpPassword;
    public TableColumn colEmpNic;
    public TableColumn colEmpContact;
    public TableColumn colEmpDescription;
    public TableColumn colEmpAddress;
    public TableColumn colEmpSalary;
    LinkedHashMap<TextField, Pattern> itemMap = new LinkedHashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(true);
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colEmpPassword.setCellValueFactory(new PropertyValueFactory<>("empPassword"));
        colEmpNic.setCellValueFactory(new PropertyValueFactory<>("empNic"));
        colEmpContact.setCellValueFactory(new PropertyValueFactory<>("empContact"));
        colEmpDescription.setCellValueFactory(new PropertyValueFactory<>("empDescription"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
        colEmpSalary.setCellValueFactory(new PropertyValueFactory<>("empSalary"));

        try {
            loadAllEmployees();
            tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    setData(newValue);
                    btnDelete.setDisable(false);
                    btnUpdate.setDisable(false);
                    btnSave.setDisable(true);
                }
            });
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Add Pattern and Text to the Map
        //Create a Pattern and Compile it to Use
        Pattern CashierIDPattern = Pattern.compile("^(E00)-[0-9]{2,5}$");//true
        Pattern CashierNamePattern = Pattern.compile("^([A-Z a-z]{5,40})$");//true
        Pattern UserPasswordPattern = Pattern.compile("^([A-Z a-z]{5,15}[0-9]{1,10})$");//true
        Pattern NICPattern = Pattern.compile("^([0-9]{12}|[0-9v]{10})$");//true
        Pattern ContactNoPattern = Pattern.compile("^(07(0|1|2|4|5|6|7|8)[0-9]{7})$");//true
        Pattern DescriptionPattern = Pattern.compile("^([A-Z a-z]{5,15})$");//true
        Pattern AddressPattern = Pattern.compile("^([A-Za-z]{4,10})$");//true
        Pattern SalaryPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{1,2})?$");//true

        itemMap.put(txtEmpID,CashierIDPattern);
        itemMap.put(txtEmpName,CashierNamePattern);
        itemMap.put(txtEmpPassword,UserPasswordPattern);
        itemMap.put(txtEmpNic,NICPattern);
        itemMap.put(txtEmpContact,ContactNoPattern);
        itemMap.put(txtEmpDescription,DescriptionPattern);
        itemMap.put(txtEmpAddress,AddressPattern);
        itemMap.put(txtEmpSalary,SalaryPattern);

        btnSave.setOnMouseClicked(event -> {
            saveBtnOnAction();
        });
    }

    //Load All Cashiers
    private void loadAllEmployees() throws SQLException, ClassNotFoundException {
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

    //Save Cashier Details
    public void saveBtnOnAction() {
        Cashier cashier = new Cashier(
                txtEmpID.getText(),
                txtEmpName.getText(),
                txtEmpPassword.getText(),
                txtEmpNic.getText(),
                txtEmpContact.getText(),
                txtEmpDescription.getText(),
                txtEmpAddress.getText(),
                Double.parseDouble(txtEmpSalary.getText())
        );

        try {
            if (CrudUtil.execute("INSERT INTO Cashier VALUES (?,?,?,?,?,?,?,?)",
                    cashier.getEmpId(), cashier.getEmpName(), cashier.getEmpPassword(),
                    cashier.getEmpNic(), cashier.getEmpContact(), cashier.getEmpDescription(), cashier.getEmpAddress(), cashier.getEmpSalary()
            )) {
                Notification.notification("Saved..!", "You have Successfully Saved this Cashier.");
                loadAllEmployees();
                clearAllTexts();
                btnDelete.setDisable(true);
                btnUpdate.setDisable(true);
            }
        } catch (ClassNotFoundException | SQLException | NullPointerException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    //Clear Cashier Details
    public void clearAllTexts() {
        txtEmpID.clear();
        txtEmpName.clear();
        txtEmpPassword.clear();
        txtEmpNic.clear();
        txtEmpContact.clear();
        txtEmpDescription.clear();
        txtEmpAddress.clear();
        txtEmpSalary.clear();
        txtEmpID.requestFocus();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(true);
    }

    //Update Cashier Details
    public void updateCashierBtnOnAction(ActionEvent actionEvent) {
        Cashier cashier = new Cashier(
                txtEmpID.getText(),
                txtEmpName.getText(),
                txtEmpPassword.getText(),
                txtEmpNic.getText(),
                txtEmpContact.getText(),
                txtEmpDescription.getText(),
                txtEmpAddress.getText(),
                Double.parseDouble(txtEmpSalary.getText())
        );
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are You Sure ?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();
            /**
             *Show()// just view the alert but won't wait for the confirmation (JVM --> continue)
             * ShowAndWait(); //view and wait until confirmation
             **/

            if (buttonType.get().equals(ButtonType.YES)) {
                //Update
                boolean isUpdated = CrudUtil.execute(
                        "UPDATE Cashier SET CashierName=? ," +
                                " CashierPsw=? ," +
                                " CashierNIC=? ," +
                                " CashierCNumber=? , " +
                                " CashierDescription=? ," +
                                " CashierAddress=?, " +
                                " CashierSalary=? WHERE CashierID=?", cashier.getEmpName(), cashier.getEmpPassword(),
                        cashier.getEmpNic(), cashier.getEmpContact(), cashier.getEmpDescription(), cashier.getEmpAddress(), cashier.getEmpSalary(), cashier.getEmpId()
                );
                if (isUpdated) {
                    Notification.notification("Update Confirmed..!", "You have Successfully Updated this Cashier.");
                    loadAllEmployees();
                    btnDelete.setDisable(true);
                    btnUpdate.setDisable(true);
                    btnSave.setDisable(true);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {

        }
    }

    //Delete Cashier Details
    public void deleteBtnOnAction(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are You Sure ?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();
            /**
             *Show()// just view the alert but won't wait for the confirmation (JVM --> continue)
             * ShowAndWait(); //view and wait until confirmation
             **/

            if (buttonType.get().equals(ButtonType.YES)) {
                //Delete
                if (CrudUtil.execute("DELETE FROM Cashier WHERE CashierID=?", txtEmpID.getText())) {
                    Notification.notification("Deleted..!", "You have Successfully deleted this Cashier.");
                    clearAllTexts();
                    loadAllEmployees();
                    btnDelete.setDisable(true);
                    btnUpdate.setDisable(true);
                    btnSave.setDisable(true);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {

        }
    }

    //Cashier ID Search
    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String cashierId = txtSearch.getText();

        Cashier cashier = new CashierCrudController().getCashier(cashierId);
        if (cashier == null) {
            new Alert(Alert.AlertType.WARNING, "No Cashier Related To This CashierId Id").show();
        } else {
            setData(cashier);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnSave.setDisable(true);
        }
    }

    //Set All Cashiers
    private void setData(Cashier cashier) {
        try {
            txtEmpID.setText(cashier.getEmpId());
            txtEmpName.setText(cashier.getEmpName());
            txtEmpPassword.setText(cashier.getEmpPassword());
            txtEmpNic.setText(cashier.getEmpNic());
            txtEmpContact.setText(cashier.getEmpContact());
            txtEmpDescription.setText(cashier.getEmpDescription());
            txtEmpAddress.setText(cashier.getEmpAddress());
            txtEmpSalary.setText(String.valueOf(cashier.getEmpSalary()));
        }catch (NullPointerException ignore){

        }
    }

    //Check items validations
    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(itemMap, btnSave);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(itemMap, btnSave);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
                System.out.println("Work");
            } else if (response instanceof Boolean) {
                saveBtnOnAction();
            }
        }
    }
}
