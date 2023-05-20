package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Item;
import util.CrudUtil;
import util.Notification;
import util.ValidationUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ItemManageFormController implements Initializable {
    public AnchorPane itemFormContext;
    public TableView<Item> tblItem;
    public TextField txtItemCode;
    public TextField txtItemName;
    public TextField txtItemPackSize;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TableColumn colCode;
    public TableColumn colName;
    public TableColumn colQty;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public JFXButton btnDelete;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public TextField txtSearch;
    LinkedHashMap<TextField, Pattern> itemMap = new LinkedHashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);
            btnSave.setDisable(true);
        }catch (NullPointerException ignored){

        }
        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("itemQtyOnHand"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("itemPackSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("itemUnitPrice"));

        try {
            loadAllItems();
            tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    setData(newValue);
                    try {
                        btnDelete.setDisable(false);
                        btnUpdate.setDisable(false);
                        btnSave.setDisable(true);
                    }catch (NullPointerException ignored){

                    }
                }
            });
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        //Add Pattern and Text to the Map
        //Create a Pattern and Compile it to Use
        Pattern idItmPattern = Pattern.compile("^(P00)-[0-9]{2,5}$");//true
        Pattern DescriptionItmPattern = Pattern.compile("^[A-z]{2,10}$");//true
        Pattern PackItmPattern = Pattern.compile("^(Small|Medium|Large|small|medium|large)$");//true
        Pattern UnitPriceItmPattern = Pattern.compile("^([0-9]{2,6}.[0-9]{1,2})$");//true
        Pattern QtyItmPattern = Pattern.compile("^[0-9]{1,5}$");//true

        itemMap.put(txtItemCode, idItmPattern);
        itemMap.put(txtItemName, DescriptionItmPattern);
        itemMap.put(txtItemPackSize, PackItmPattern);
        itemMap.put(txtUnitPrice, UnitPriceItmPattern);
        itemMap.put(txtQtyOnHand, QtyItmPattern);
        try {
            btnSave.setOnMouseClicked(event -> {
                saveBtnOnAction();
            });
        } catch (NullPointerException e) {

        }
    }

    //Set all items
    private void setData(Item newValue) {
        try {
            txtItemCode.setText(newValue.getItemCode());
            txtItemName.setText(newValue.getItemName());
            txtQtyOnHand.setText(String.valueOf(newValue.getItemQtyOnHand()));
            txtItemPackSize.setText(newValue.getItemPackSize());
            txtUnitPrice.setText(String.valueOf(newValue.getItemUnitPrice()));
        } catch (NullPointerException ignore) {

        }
    }

    //Load all items
    private void loadAllItems() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item");
        ObservableList<Item> observableList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            observableList.add(
                    new Item(
                            resultSet.getString("ItemCode"),
                            resultSet.getString("Description"),
                            resultSet.getInt("QtyOnHand"),
                            resultSet.getString("PackSize"),
                            resultSet.getDouble("UnitPrice")
                    )
            );
        }
        tblItem.setItems(observableList);
    }

    //Save items details
    public void saveBtnOnAction() {
        try {
            Item item = new Item(
                    txtItemCode.getText(),
                    txtItemName.getText(),
                    Integer.parseInt(txtQtyOnHand.getText()),
                    txtItemPackSize.getText(),
                    Double.parseDouble(txtUnitPrice.getText())
            );

            if (CrudUtil.execute("INSERT INTO Item VALUES (?,?,?,?,?)",
                    item.getItemCode(), item.getItemName(), item.getItemQtyOnHand(), item.getItemPackSize(), item.getItemUnitPrice()
            )) {
                Notification.notification("Saved..!", "You have Successfully Saved this Item.");
                loadAllItems();
                clearAllTexts();
                tblItem.refresh();
                btnDelete.setDisable(true);
                btnUpdate.setDisable(true);
            }
        } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    //Clear details items
    private void clearAllTexts() {
        txtItemCode.clear();
        txtItemName.clear();
        Integer.parseInt(txtQtyOnHand.getText());
        txtItemPackSize.clear();
        Double.parseDouble(txtUnitPrice.getText());
        txtItemCode.requestFocus();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(true);
    }

    //Update items details
    public void updateBtnOnAction(ActionEvent actionEvent) {
        Item t = new Item(
                txtItemCode.getText(),
                txtItemName.getText(),
                Integer.parseInt(txtQtyOnHand.getText()),
                txtItemPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText())
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
                        "UPDATE Item SET Description=? ," +
                                "QtyOnHand=? ," +
                                "PackSize=? ," +
                                "UnitPrice=? WHERE ItemCode=?", t.getItemName(),
                        t.getItemQtyOnHand(), t.getItemPackSize(), t.getItemUnitPrice(), t.getItemCode()
                );
                if (isUpdated) {
                    Notification.notification("Update Confirmed..!", "You have Successfully Updated this Item.");
                    loadAllItems();
                    btnDelete.setDisable(true);
                    btnUpdate.setDisable(true);
                    btnSave.setDisable(true);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Delete items details
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
                if (CrudUtil.execute("DELETE FROM Item WHERE ItemCode=?", txtItemCode.getText())) {
                    Notification.notification("Deleted..!", "You have Successfully deleted this Item.");
                    clearAllTexts();
                    loadAllItems();
                    btnDelete.setDisable(true);
                    btnUpdate.setDisable(true);
                    btnSave.setDisable(true);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Item Code Search btn
    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        itemCodeSearch();
        String itemId = txtSearch.getText();

        Item item = new ItemCrudController().getItem(itemId);
        if (item == null) {
//            new Alert(Alert.AlertType.WARNING, "No Customer Related To This CustomerId Id").show();
        } else {
            setData(item);
            try {
                btnDelete.setDisable(false);
                btnUpdate.setDisable(false);
                btnSave.setDisable(true);
            }catch (NullPointerException ignored){

            }
        }
    }

    //Item Code Search method
    public void itemCodeSearch() throws SQLException, ClassNotFoundException {
        if (txtSearch.getText().trim().isEmpty()) {
            loadAllItems();

        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM Item WHERE ItemCode=?", txtSearch.getText());
            ObservableList<Item> itemObservableList = FXCollections.observableArrayList();

            while (result.next()) {
                itemObservableList.add(
                        new Item(
                                result.getString("ItemCode"),
                                result.getString("Description"),
                                result.getInt("QtyOnHand"),
                                result.getString("PackSize"),
                                result.getDouble("UnitPrice")
                        )
                );
            }
            tblItem.setItems(itemObservableList);
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
