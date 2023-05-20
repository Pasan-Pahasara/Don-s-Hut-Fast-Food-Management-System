package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.Notification;
import util.UILoader;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EnterFormController implements Initializable {

    public TextField txtUserName;
    public PasswordField txtPassword;
    public AnchorPane enterFormContext;
    public Label lblHide;
    public JFXButton btnSignIn;

    int attempts = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ManagementLoginFormController.enterFormContext = enterFormContext;
    }
    public void loginOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        login();
    }
    public void login() throws IOException, SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String query = "SELECT * FROM Cashier";
        PreparedStatement stm = con.prepareStatement(query);

        String CashierName = txtUserName.getText();
        String CashierPsw = txtPassword.getText();

        ResultSet rst = stm.executeQuery(query);
        if (rst.next()) {
            if (attempts++ < 5) {
                if (CashierName.equals(rst.getString(2)) && CashierPsw.equals(rst.getString(3))) {
                    UILoader.CashierUI("CashierDashboardForm", enterFormContext);
                    Notification.notification("Login Cashier Successfully.!", "You have Successfully login to the Cashier.");
                } else {
                    //try again
                    Notification.notificationAttempts("Cashier", attempts);
                }
            } else {
                txtUserName.setEditable(false);
                txtPassword.setEditable(false);
                Notification.EmptyDataNotification("Account is Temporarily Disabled or You Did not Sign in Correctly");
            }
        }
    }

    public void managementLoginOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.ManagementPopUpUI("ManagementLoginForm");
    }

    public void minOnMouseClicked(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void maxOnMouseClicked(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
    }

    public void closeOnMouseClicked(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    //------Show Password-----
    public void showPasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("assets/show.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(24);
        view.setFitWidth(24);
        lblHide.setGraphic(view);

        txtPassword.setPromptText(txtPassword.getText());
        txtPassword.setText("");
        txtPassword.setDisable(true);
        txtPassword.requestFocus();
    }

    //------Hide Password-----
    public void hidePasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("assets/hide.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(24);
        view.setFitWidth(24);
        lblHide.setGraphic(view);

        txtPassword.setText(txtPassword.getPromptText());
        txtPassword.setPromptText("");
        txtPassword.setDisable(false);
    }

    public void userName_Key_Released(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            txtPassword.requestFocus();
        }
    }

    public void password_Key_Released(KeyEvent keyEvent) throws SQLException, IOException {
        if (keyEvent.getCode().equals(KeyCode.ENTER))login();
    }
}
