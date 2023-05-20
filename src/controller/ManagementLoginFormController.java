package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.event.ActionEvent;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagementLoginFormController {

    public static AnchorPane enterFormContext;
    public AnchorPane managementLogContext;
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Label lblHide;
    public JFXButton btnAdminLogin;

    int attempts = 0;

    //Admin logIn Button
    public void adminLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        adminLogin();
    }

    //Admin logIn Method
    public void adminLogin() throws IOException, SQLException {

        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String query = "SELECT * FROM Login";
        PreparedStatement stm = con.prepareStatement(query);

        String UserName = txtUserName.getText();
        String Password = txtPassword.getText();

        ResultSet rst = stm.executeQuery(query);
        if (rst.next()) {
            if (attempts++ < 5) {
                if (UserName.equals(rst.getString(1)) && Password.equals(rst.getString(2))) {
                    UILoader.CashierUI("ManagementDashboardForm", enterFormContext);
                    Notification.notification("Login Management Successfully.!", "You have Successfully login to the Management.");
                    Stage stage1 = (Stage) managementLogContext.getScene().getWindow();
                    stage1.close();
                } else {
                    //try again
                    Notification.notificationAttempts("Admin", attempts);
                }
            } else {
                txtUserName.setEditable(false);
                txtPassword.setEditable(false);
                Notification.EmptyDataNotification("Account is Temporarily Disabled or You Did not Sign in Correctly");
                Notification.notificationError("Login Unsuccessfully.!", "Please Enter Correct Password.");
            }
        }
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
        if (keyEvent.getCode().equals(KeyCode.ENTER))adminLogin();
    }
}
