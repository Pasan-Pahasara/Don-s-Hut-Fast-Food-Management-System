package controller;

import db.DBConnection;
import model.Cashier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CashierCrudController {
    public Cashier getCashier(String cashierId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Cashier WHERE CashierID=?");

        stm.setObject(1, cashierId);
        ResultSet rst = stm.executeQuery();

        if (rst.next()) {
            Cashier cashier = new Cashier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getDouble(8));

            return cashier;
        }
        return null;
    }
}
