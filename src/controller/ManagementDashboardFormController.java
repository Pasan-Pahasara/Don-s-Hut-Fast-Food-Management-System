package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.UILoader;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class ManagementDashboardFormController implements Initializable {
    public AnchorPane mainPane;
    public Text txtDate;
    public Text txtTime;
    public AnchorPane managementDashboardFormContext;
    public JFXButton btnOrderView;
    public ImageView imageLoad;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageLoad.setVisible(false);
        try {
            UILoader.SetUi("MainForm", mainPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //-------------------------------------------RealTimeClock----------------------------------------------------------

        Thread clock = new Thread() {
            public void run() {
                for (; ; ) {
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm ");
                    Calendar cal = Calendar.getInstance();

                    int minute = cal.get(Calendar.MINUTE);
                    int hour = cal.get(Calendar.HOUR_OF_DAY);

                    try {
                        String state = null;
                        if (hour >= 12) {
                            state = "PM";
                        } else {
                            state = "AM";
                        }
                        txtTime.setText("" + String.format("%02d", hour) + ":" + String.format("%02d", minute) + " " + state);
                        try {
                            sleep(1000);
                        } catch (InterruptedException ex) {
                            System.out.println(ex);
                        }
                    } catch (NullPointerException e) {
                        System.out.println(e);
                    }
                }
            }
        };
        clock.start();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        txtDate.setText(formatter.format(date));
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

    public void dashboardOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        UILoader.SetUi("MainForm", mainPane);
        animationCogWheel();
    }

    public void employeeManagementOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("CashierManageForm", mainPane);
        animationCogWheel();
    }

    public void manageItemsOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("ItemManageForm", mainPane);
        animationCogWheel();
    }

    public void orderViewOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("OrderViewForm",mainPane);
        animationCogWheel();
    }

    public void viewDetailsOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("ManagementViewDetails", mainPane);
        animationCogWheel();
    }

    public void systemReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("SystemReportForm", mainPane);
        animationCogWheel();
    }

    public void infoOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        UILoader.InfoUI("AboutUsForm");

    }

    public void logOutOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("/views/EnterForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) managementDashboardFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Button) {
            Button button = (Button) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), button);
            scaleT.setToX(1.1);
            scaleT.setToY(1.1);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            button.setEffect(glow);
        }else if (mouseEvent.getSource() instanceof ImageView){
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.1);
            scaleT.setToY(1.1);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    public void playMouseExitedAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Button) {
            Button button1 = (Button) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), button1);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            button1.setEffect(null);

        } else if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
    }

    //Animation Cog Wheel
    public void animationCogWheel() {
        imageLoad.setVisible(true);
        Timeline rotate = new Timeline();
        Timeline rotate1 = new Timeline();
        DoubleProperty r = imageLoad.rotateProperty();
        rotate1.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(r, 0)),
                new KeyFrame(new Duration(1000), new KeyValue(r, 360))
        );
        rotate1.play();
        rotate.play();
    }
}
