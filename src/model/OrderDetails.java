package model;

public class OrderDetails {
    private String orderId;
    private String itemCode;
    private String itemName;
    private String cusId;
    private int qty;
    private double discount;
    private double total;

    public OrderDetails() {
    }

    public OrderDetails(String itemCode,String itemName, int qty, double total) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.qty = qty;
        this.total = total;
    }

    public OrderDetails(String orderId, String itemCode, String itemName, String cusId, int qty, double discount, double total) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.cusId = cusId;
        this.qty = qty;
        this.discount = discount;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", cusId='" + cusId + '\'' +
                ", qty=" + qty +
                ", discount=" + discount +
                ", total=" + total +
                '}';
    }
}
