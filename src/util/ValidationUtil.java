package util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
// Validation Util Class for all validation, you can change the logic as you want
public class ValidationUtil {
    public static Object validate(LinkedHashMap<TextField, Pattern> map, JFXButton btn) {
        for (TextField key : map.keySet()) {
            Pattern pattern = map.get(key);
            if (!pattern.matcher(key.getText()).matches()) {
                addError(key, btn);
                return key;
            }
            removeError(key, btn);
        }
        return true;
    }

    private static void removeError(TextField txtField, JFXButton btn) {
        txtField.getParent().setStyle("-fx-border-color: green");
        ((AnchorPane) txtField.getParent()).getChildren().get(1).setStyle("-fx-text-fill: green");
        btn.setDisable(false);
    }

    private static void addError(TextField txtField, JFXButton btn) {
        if (txtField.getText().length() > 0) {
            txtField.getParent().setStyle("-fx-border-color: red");
              ((AnchorPane) txtField.getParent()).getChildren().get(1).setStyle("-fx-text-fill: red");

        }
        btn.setDisable(true);
    }
    public static Object validatePlaceOrder(LinkedHashMap<TextField, Pattern> map, JFXButton btn) {
        for (TextField key : map.keySet()) {
            Pattern pattern = map.get(key);
            if (!pattern.matcher(key.getText()).matches()) {
                add(key, btn);
                return key;
            }
            remove(key, btn);
        }
        return true;
    }

    private static void remove(TextField txtField, JFXButton btn) {
        txtField.getParent().setStyle("-fx-border-color: green");
        btn.setDisable(false);
    }

    private static void add(TextField txtField, JFXButton btn) {
        if (txtField.getText().length() > 0) {
            txtField.getParent().setStyle("-fx-border-color: red");
        }
        btn.setDisable(true);
    }
}