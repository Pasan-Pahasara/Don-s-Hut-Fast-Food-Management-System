package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
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
import javafx.util.Duration;
import model.Customer;
import model.Item;
import model.Order;
import model.OrderDetails;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.Notification;
import util.ValidationUtil;
import views.tm.CartTM;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;


public class PlaceOrderFormController implements Initializable {

    public TableView<CartTM> tblOrder;
    public TableColumn colOrderId;
    public TableColumn colItemCode;
    public TableColumn colQTY;
    public TableColumn colPrize;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public JFXComboBox<String> cmbCustomerId;
    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtName;
    public JFXTextField txtCity;
    public JFXTextField txtAddress;
    public JFXTextField txtUnitPrize;
    public JFXTextField txtQTYOnHand;
    public JFXTextField txtDescription;
    public TextField txtQTY;
    public Label lblId;
    public Label lblDate;
    public Label lblTime;
    public TextField txtDiscount;
    public Label lblTotal;
    public JFXButton btnAddToCart;
    public JFXButton btnRemove;
    public JFXButton btnPrintBill;
    public JFXButton btnClearAll;
    public JFXButton btnPlaceOrder;
    LinkedHashMap<TextField, Pattern> Map = new LinkedHashMap<>();
    ObservableList<CartTM> tmList = FXCollections.observableArrayList();

    int cartSelectedRowForRemove = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrize.setCellValueFactory(new PropertyValueFactory<>("unitPrize"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


        loadDateAndTime();
        setCustomerIds();
        setItemCodes();
        setOrderId();
        btnRemove.setDisable(true);
        btnClearAll.setDisable(true);
        btnPrintBill.setDisable(true);
        btnPlaceOrder.setDisable(true);
        btnAddToCart.setDisable(true);

        //--------------------
        cmbCustomerId.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setCustomerDetails(newValue);
                });

        cmbItemCode.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setItemDetails(newValue);
                });
        tblOrder.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
        //--------------------

        //Add Pattern and Text to the Map
        //Create a Pattern and Compile it to Use
        Pattern qtyPattern = Pattern.compile("^[0-9]{1,5}$");//true
        Pattern discountPattern = Pattern.compile("^[0-9]{1,3}$");//true

        Map.put(txtQTY, qtyPattern);
        Map.put(txtDiscount, discountPattern);

            btnAddToCart.setOnMouseClicked(event -> {
                addToCartOnAction();
            });
    }

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

    private void setOrderId() {
        try {
            lblId.setText(new OrderCrudController().getOrderId());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerDetails(String selectedCustomerId) {
        try {
            Customer c = CustomerCrudController.getCustomer(selectedCustomerId);
            if (c != null) {
                txtName.setText(c.getCusName());
                txtAddress.setText(c.getCusAddress());
                txtCity.setText(c.getCusCity());
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void setItemDetails(String selectedItemCode) {
        try {

            Item i = ItemCrudController.getItem(selectedItemCode);
            if (i != null) {
                txtDescription.setText(i.getItemName());
                txtUnitPrize.setText(String.valueOf(i.getItemUnitPrice()));
                txtQTYOnHand.setText(String.valueOf(i.getItemQtyOnHand()));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //
    private void setCustomerIds() {
        try {
            ObservableList<String> cIdObList = FXCollections.observableArrayList(
                    CustomerCrudController.getCustomerIds()
            );
            cmbCustomerId.setItems(cIdObList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Set Item Code
    private void setItemCodes() {
        try {

            cmbItemCode.setItems(FXCollections.observableArrayList(ItemCrudController.getItemCodes()));

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Remove Button On Action
    public void removeBtnOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            tmList.remove(cartSelectedRowForRemove);
            if (tmList.isEmpty()) {

            }
            calculateTotal();
            tblOrder.refresh();
            btnRemove.setDisable(true);
            btnClearAll.setDisable(true);
            btnPrintBill.setDisable(true);
            btnPlaceOrder.setDisable(true);
        }
    }

    //Add Cart
    public void addToCartOnAction() {
        double unitPrice = Double.parseDouble(txtUnitPrize.getText());
        double discount = Double.parseDouble(txtDiscount.getText());
        int qty = Integer.parseInt(txtQTY.getText());
        double Cost = ((unitPrice * qty) * discount / 100);
        double totalCost = (unitPrice * qty) - Cost;

        CartTM isExists = isExists(cmbItemCode.getValue());

        if (isExists != null) {
            for (CartTM temp : tmList
            ) {
                if (temp.equals(isExists)) {
                    temp.setQty((temp.getQty() + qty));
                    temp.setTotal(temp.getTotal() + totalCost);
                }
            }
        } else {

            CartTM tm = new CartTM(
                    lblId.getText(),
                    cmbItemCode.getValue(),
                    txtDescription.getText(),
                    cmbCustomerId.getValue(),
                    qty,
                    unitPrice,
                    discount,
                    totalCost
            );

            tmList.add(tm);
            tblOrder.setItems(tmList);
        }
        tblOrder.refresh();
        calculateTotal();
        clearAllTextsAddInCart();
        btnRemove.setDisable(false);
        btnClearAll.setDisable(false);
        btnPrintBill.setDisable(false);
        btnAddToCart.setDisable(true);
        btnPlaceOrder.setDisable(false);
    }

    //Calculate Total
    private void calculateTotal() {
        double total = 0;
        for (CartTM tm : tmList
        ) {
            total += tm.getTotal();
        }
        lblTotal.setText(total + "/=");
    }

    //Cart is Exists
    private CartTM isExists(String itemCode) {
        for (CartTM tm : tmList
        ) {
            if (tm.getCode().equals(itemCode)) {
                return tm;
            }
        }
        return null;
    }

    //Place Order
    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException {
        ArrayList<OrderDetails> details = new ArrayList<>();
        for (CartTM tm : tmList
        ) {
            details.add(
                    new OrderDetails(
                            tm.getOrderId(),
                            tm.getCode(),
                            tm.getItemName(),
                            tm.getCustomerId(),
                            tm.getQty(),
                            tm.getDiscount(),
                            tm.getTotal()

                    )
            );
            tm.getCode();

        }
        Order order = new Order(
                lblId.getText(),
                cmbCustomerId.getValue(),
                lblDate.getText(),
                lblTime.getText()
        );

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved = new OrderCrudController().saveOrder(order);
            if (isOrderSaved) {
                boolean isDetailsSaved = new OrderCrudController().saveOrderDetails(details);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are You Sure ?", ButtonType.YES, ButtonType.NO);

                Optional<ButtonType> buttonType = alert.showAndWait();
                /**
                 *Show()// just view the alert but won't wait for the confirmation (JVM --> continue)
                 * ShowAndWait(); //view and wait until confirmation
                 **/

                if (buttonType.get().equals(ButtonType.YES)) {
                    //Place Order
                if (isDetailsSaved) {
                    connection.commit();
                    Notification.notification("Place this Order Now..!", "You have Successfully Place this Order Now.");

                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Error!").show();
                }
            } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Error!").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        } finally {
            connection.setAutoCommit(true);
        }
        setOrderId();
        printBill();
        ClearOnAction();
    }

    //Clear Button On Action
    public void clearOnAction(ActionEvent actionEvent) {
        ClearOnAction();
    }

    //Clear All Texts Add In Cart
    public void clearAllTextsAddInCart() {
        cmbItemCode.getSelectionModel().clearSelection();
        txtDescription.clear();
        txtQTYOnHand.clear();
        txtUnitPrize.clear();
        txtQTY.clear();
        txtDiscount.clear();
        cmbItemCode.requestFocus();
    }

    //Clear All Texts
    public void clearAllTexts() {
        cmbCustomerId.getSelectionModel().clearSelection();
        txtName.clear();
        txtAddress.clear();
        txtCity.clear();
        cmbItemCode.getSelectionModel().clearSelection();
        txtDescription.clear();
        txtQTYOnHand.clear();
        txtUnitPrize.clear();
        txtQTY.clear();
        txtDiscount.clear();
        cmbCustomerId.requestFocus();
        btnRemove.setDisable(true);
        btnClearAll.setDisable(true);
        btnPrintBill.setDisable(true);
        btnAddToCart.setDisable(true);
        btnPlaceOrder.setVisible(true);
    }

    //Clear Button
    public void ClearOnAction() {
        clearAllTexts();
        tmList.clear();
        lblTotal.setText("0.00/=");
        tblOrder.refresh();
        btnPlaceOrder.setDisable(true);
    }

    //Print On Action
    public void printBillOnAction(ActionEvent event){
        printBill();
    }
    //Print Bill
    public void printBill(){
        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/views/reports/Dons.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, new JRBeanCollectionDataSource(tmList));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validatePlaceOrder(Map, btnAddToCart);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validatePlaceOrder(Map, btnAddToCart);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
                System.out.println("Work");
            } else if (response instanceof Boolean) {
                addToCartOnAction();
            }
        }
    }
}
