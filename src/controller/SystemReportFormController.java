package controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import util.UILoader;

import java.io.IOException;

public class SystemReportFormController {

    public AnchorPane mainPane2;

    public void dailyReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("DailyIncomeForm",mainPane2);
    }

    public void monthlyReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("MonthlyIncomeForm",mainPane2);
    }

    public void annuallyReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.SetUi("YearlyIncome",mainPane2);
    }

}
