package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Customer;
import util.CrudUtil;
import util.Notification;
import util.ValidationUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CustomerManageFormController implements Initializable {
    public AnchorPane customerFormContext;
    public TableView<Customer> tblCustomer;
    public JFXButton btnSave;
    public TextField txtCusID;
    public TextField txtCusCity;
    public TextField txtCusPostalCode;
    public TextField txtProvince;
    public TextField txtCusAddress;
    public TextField txtCusTitle;
    public TextField txtCusName;
    public Label lblDate;
    public Label lblTime;
    public TableColumn tblCusId;
    public TableColumn tblCusTitle;
    public TableColumn tblCusName;
    public TableColumn tblCusAddress;
    public TableColumn tblCusCity;
    public TableColumn tblCusProvince;
    public TableColumn tblCusCode;
    public TextField txtSearch;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    LinkedHashMap<TextField, Pattern> itemMap = new LinkedHashMap<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(true);

        tblCusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        tblCusTitle.setCellValueFactory(new PropertyValueFactory<>("cusTitle"));
        tblCusName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        tblCusAddress.setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        tblCusCity.setCellValueFactory(new PropertyValueFactory<>("cusCity"));
        tblCusProvince.setCellValueFactory(new PropertyValueFactory<>("cusProvince"));
        tblCusCode.setCellValueFactory(new PropertyValueFactory<>("cusPostalCode"));

        loadDateAndTime();
        try {
            loadAllCustomers();
            tblCustomer.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    setData(newValue);
                btnDelete.setDisable(false);
                btnUpdate.setDisable(false);
                btnSave.setDisable(true);
                }
            }));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        //Add Pattern and Text to the Map
        //Create a Pattern and Compile it to Use
        Pattern idCusPattern = Pattern.compile("^(C00)-[0-9]{2,5}$");//true
        Pattern titleCusPattern = Pattern.compile("^[Mr|Mrs]{2,3}$");//true
        Pattern nameCusPattern = Pattern.compile("^([A-Z a-z]{4,40})$");//true
        Pattern addressCusPattern = Pattern.compile("^[A-z0-9/, ]{5,30}$");//true
        Pattern cityCusPattern = Pattern.compile("^([A-Z  a-z]{3,20})$");//true
        Pattern provinceCusPatter = Pattern.compile("^([A-Z  a-z]{2,20})$");//true
        Pattern postalCodeCusPatter = Pattern.compile("^([0-9]{2,10})$");//true

        itemMap.put(txtCusID,idCusPattern);
        itemMap.put(txtCusTitle,titleCusPattern);
        itemMap.put(txtCusName,nameCusPattern);
        itemMap.put(txtCusAddress,addressCusPattern);
        itemMap.put(txtCusCity,cityCusPattern);
        itemMap.put(txtProvince,provinceCusPatter);
        itemMap.put(txtCusPostalCode,postalCodeCusPatter);

        btnSave.setOnMouseClicked(event -> {
            saveBtnOnAction();
        });
    }

    //Set All Customers
    private void setData(Customer newValue) {
        try {
            txtCusID.setText(newValue.getCusId());
            txtCusTitle.setText(newValue.getCusTitle());
            txtCusName.setText(newValue.getCusName());
            txtCusAddress.setText(newValue.getCusAddress());
            txtCusCity.setText(newValue.getCusCity());
            txtProvince.setText(newValue.getCusProvince());
            txtCusPostalCode.setText(newValue.getCusPostalCode());
            lblDate.setText(newValue.getCusDate());
            lblTime.setText(newValue.getCusTime());

        } catch (NullPointerException ignored) {

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

    //Save Customers Details
    public void saveBtnOnAction() {

        Customer cus = new Customer(
                txtCusID.getText(),
                txtCusTitle.getText(),
                txtCusName.getText(),
                txtCusAddress.getText(),
                txtCusCity.getText(),
                txtProvince.getText(),
                txtCusPostalCode.getText(),
                lblDate.getText(),
                lblTime.getText()
        );

        try {
            if (CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?,?,?,?,?)",
                    cus.getCusId(), cus.getCusTitle(), cus.getCusName(),
                    cus.getCusAddress(), cus.getCusCity(), cus.getCusProvince(), cus.getCusPostalCode(), cus.getCusDate(), cus.getCusTime()
            )) {
                Notification.notification("Saved..!", "You have Successfully Saved this Cashier.");
                loadAllCustomers();
                clearAllTexts();
            }
        }catch (SQLIntegrityConstraintViolationException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        catch (ClassNotFoundException | SQLException | NullPointerException e) {
            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    //Update Customers Details
    public void updateBtnOnAction(ActionEvent actionEvent) {
        Customer c = new Customer(
                txtCusID.getText(),
                txtCusTitle.getText(),
                txtCusName.getText(),
                txtCusAddress.getText(),
                txtCusCity.getText(),
                txtProvince.getText(),
                txtCusPostalCode.getText(),
                lblDate.getText(),
                lblTime.getText()
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
                boolean isUpdated = CrudUtil.execute("UPDATE Customer SET CustomerTitle=? ," + " CustomerName=? ," +
                                " CustomerAddress=? ," +
                                " City=? , " +
                                " Province=? ," +
                                " PostCode=?, " +
                                " Date=? , Time=? WHERE CustomerID=?", c.getCusTitle(), c.getCusName(),
                        c.getCusAddress(), c.getCusCity(), c.getCusProvince(), c.getCusPostalCode(), c.getCusDate(), c.getCusTime(), c.getCusId()
                );
                if (isUpdated) {
                    Notification.notification("Update Confirmed..!", "You have Successfully Updated this Customer.");
                    loadAllCustomers();
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

    //Delete Customers Details
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
                if (CrudUtil.execute("DELETE FROM Customer WHERE CustomerID=?", txtCusID.getText())) {
                    Notification.notification("Deleted..!", "You have Successfully deleted this Customer.");
                    clearAllTexts();
                    loadAllCustomers();
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

    //Clear Customers Details
    public void clearAllTexts() {
        txtCusID.clear();
        txtCusTitle.clear();
        txtCusName.clear();
        txtCusAddress.clear();
        txtCusCity.clear();
        txtProvince.clear();
        txtCusPostalCode.clear();
        txtCusID.requestFocus();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(true);
    }

    //Customer ID Search
    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = txtSearch.getText();

        Customer customer = new CustomerCrudController().getCustomer(customerId);
        if (customer == null) {
            new Alert(Alert.AlertType.WARNING, "No Customer Related To This CustomerId Id").show();
        } else {
            setData(customer);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnSave.setDisable(true);
        }

    }

    //Load Date and Time
    private void loadDateAndTime() {
        //Set Date
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
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
