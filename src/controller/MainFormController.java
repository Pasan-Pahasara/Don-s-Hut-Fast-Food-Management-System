package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Cashier;
import model.IncomeReport;
import model.OrderDetails;
import util.CrudUtil;
import views.tm.IncomeReportTM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainFormController {
    public Label lblTotal;
    public Label lblOrders;
    public Label lblSoldItems;
    public TableView<OrderDetails> tblMostMovableReport;
    public TableColumn colMCode;
    public TableColumn colMItemName;
    public TableColumn colMQty;
    public TableColumn colMCost;
    public TableView<OrderDetails> tblLeastMovableReport;
    public TableColumn colLCode;
    public TableColumn colLItemName;
    public TableColumn colLQty;
    public TableColumn colLCost;
    public LineChart lineChart;

    public void initialize() throws SQLException, ClassNotFoundException {
        colMCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colMItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colMQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colMCost.setCellValueFactory(new PropertyValueFactory<>("total"));
        colLCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colLItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colLQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colLCost.setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            lblTotal.setText(String.valueOf(new OrderCrudController().getSumTotal()));
            lblOrders.setText(String.valueOf(new OrderCrudController().getSumSales()));
            lblSoldItems.setText(String.valueOf(new OrderCrudController().getSumSoldItems()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setData();
        setData2();

        XYChart.Series series = new XYChart.Series();
        series.setName("Sales");

        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT (MONTHNAME(OrderDate)),sum(`Order Details`.OrderQty) FROM `Order` INNER JOIN `Order Details` ON `Order`.OrderID = `Order Details`.OrderID GROUP BY OrderDate");
        ResultSet rst = stm.executeQuery();
        String prevMonth = "";

        while (rst.next()) {
            String month = rst.getString(1);

            int count = rst.getInt(2);
            series.getData().add(new XYChart.Data<>(month, count));
            prevMonth = month;
        }
        lineChart.getData().addAll(series);
    }

    private void setData() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT ItemCode ,Description, sum(OrderQty),sum(Price) FROM `Order Details` GROUP BY ItemCode ORDER BY sum(OrderQty) DESC limit 1");
        ObservableList<OrderDetails> orderDetailsObservableList = FXCollections.observableArrayList();


        while (rst.next()) {
            orderDetailsObservableList.add(new OrderDetails(
                            rst.getString("ItemCode"),
                            rst.getString("Description"),
                            rst.getInt("sum(OrderQty)"),
                            rst.getDouble("sum(Price)")
                    )
            );
        }
        tblMostMovableReport.setItems(orderDetailsObservableList);
    }

    private void setData2() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT ItemCode ,Description, sum(OrderQty),sum(Price) FROM `Order Details` GROUP BY ItemCode ORDER BY sum(OrderQty) ASC limit 1");
        ObservableList<OrderDetails> detailsObservableList = FXCollections.observableArrayList();


        while (resultSet.next()) {
            detailsObservableList.add(new OrderDetails(
                            resultSet.getString("ItemCode"),
                            resultSet.getString("Description"),
                            resultSet.getInt("sum(OrderQty)"),
                            resultSet.getDouble("sum(Price)")
                    )
            );
        }
        tblLeastMovableReport.setItems(detailsObservableList);
    }
}