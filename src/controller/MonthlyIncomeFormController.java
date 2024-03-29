package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import views.tm.IncomeReportTM;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MonthlyIncomeFormController implements Initializable {
    public TableView <IncomeReportTM>tblReport;
    public TableColumn colMonth;
    public TableColumn colOrderCount;
    public TableColumn colItemSoldQty;
    public TableColumn colCost;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMonth.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderCount.setCellValueFactory(new PropertyValueFactory<>("numberOfOrders"));
        colItemSoldQty.setCellValueFactory(new PropertyValueFactory<>("numberOfSoldItem"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("finalCost"));


        try {
            tblReport.setItems(loadMonthlyIncomeReport());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private ObservableList<IncomeReportTM> loadMonthlyIncomeReport() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT (MONTHNAME(OrderDate)) ,sum(`Order Details`.OrderQty),count(`Order`.OrderID),sum(`Order Details`.Price)  FROM `Order` INNER JOIN `Order Details` ON `Order`.OrderID = `Order Details`.OrderID GROUP BY extract(MONTH FROM(OrderDate))");
        ResultSet rst = stm.executeQuery();
        ObservableList<IncomeReportTM> obList = FXCollections.observableArrayList();

        while (rst.next()) {
            String date = rst.getString(1);
            int countOrderId = rst.getInt(3);
            int numberOfSoldItem = rst.getInt(2);
            double sumOfTotal = rst.getDouble(4);

            obList.add(new IncomeReportTM(date, countOrderId,numberOfSoldItem,sumOfTotal));
        }
        return obList;
    }
}
