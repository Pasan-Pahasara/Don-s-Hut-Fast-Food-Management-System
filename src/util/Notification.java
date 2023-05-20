package util;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Notification {
    public static void notification(String title, String text) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(new ImageView(new Image("/assets/check-mark.png")))
                .hideAfter(Duration.seconds(4))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }
    public static void notificationError(String title, String text) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(new ImageView(new Image("/assets/warning.png")))
                .hideAfter(Duration.seconds(4))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }
    public static void notificationAttempts(String option,int attempts) {
        Notifications notificationBuilder = Notifications.create()
                .title( option+" Login UnSuccessful.!")
                .text( option+" Not Login, You can use  Five  trys.\n This is "+attempts+". Try Again.!")
                .graphic(new ImageView(new Image("/assets/warning.png")))
                .hideAfter(Duration.seconds(4))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }
    public static void EmptyDataNotification(String option) {
        Notifications notificationBuilder = Notifications.create()
                .title( "Empty DataSet !")
                .text("Your Select "+option+" ID are Empty DataSet .\n Try to another ID.")
                .graphic(new ImageView(new Image("/assets/warning.png")))
                .hideAfter(Duration.seconds(4))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }
}
