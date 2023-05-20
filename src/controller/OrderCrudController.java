package controller;

import model.Order;
import model.OrderDetails;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {

    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO `Order` VALUES(?,?,?,?)", order.getId(), order.getCustomerId(), order.getDate(), order.getTime());
    }

    public boolean saveOrderDetails(ArrayList<OrderDetails> details) throws SQLException, ClassNotFoundException {
        for (OrderDetails det : details
        ) {
            boolean isDetailsSaved = CrudUtil.execute("INSERT INTO `Order Details` VALUES(?,?,?,?,?,?,?)",
                    det.getOrderId(), det.getItemCode(),det.getItemName() ,det.getCusId(), det.getQty(), det.getDiscount(), det.getTotal());
            if (isDetailsSaved) {
                if (!updateQty(det.getItemCode(), det.getQty())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Item SET QtyOnHand=QtyOnHand-? WHERE ItemCode=?", qty, itemCode);
    }

    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT OrderID FROM `Order` ORDER BY OrderID DESC LIMIT 1");
        if (set.next()) {
            int tempId = Integer.parseInt(set.getString(1).split("-")[1]);
            tempId += 1;
            if (tempId <= 9) {
                return "O-00" + tempId;
            } else if (tempId <= 99) {
                return "O-0" + tempId;
            } else {
                return "O-" + tempId;
            }
        } else {
            return "O-001";
        }
    }

//    public OrderDetails getOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
//        PreparedStatement stm = DBConnection.getInstance().getConnection().
//                prepareStatement("SELECT * FROM `Order Details` WHERE OrderID=?");
//
//        stm.setObject(1, orderId);
//        ResultSet rst = stm.executeQuery();
//
//        if (rst.next()) {
//            OrderDetails orderDetails = new OrderDetails(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getInt(4),
//                    rst.getDouble(5),
//                    rst.getDouble(6));
//
//            return orderDetails;
//        }
//        return null;
//    }

    public int getSumSales() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT count(OrderID) FROM `Order`");
        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }
    public int getSumTotal() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT sum(Price) FROM `Order Details`");
        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }
    public int getSumSoldItems() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT sum(OrderQty) FROM `Order Details`");
        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }
}
