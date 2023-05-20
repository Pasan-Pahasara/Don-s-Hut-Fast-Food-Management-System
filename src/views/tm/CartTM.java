package views.tm;

public class CartTM {
    private String orderId;
    private String code;
    private String itemName;
    private String customerId;
    private int qty;
    private double unitPrize;
    private double discount;
    private double total;

    public CartTM() {
    }

    public CartTM(String orderId, String code, String itemName, String customerId, int qty, double unitPrize, double discount, double total) {
        this.orderId = orderId;
        this.code = code;
        this.itemName = itemName;
        this.customerId = customerId;
        this.qty = qty;
        this.unitPrize = unitPrize;
        this.discount = discount;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrize() {
        return unitPrize;
    }

    public void setUnitPrize(double unitPrize) {
        this.unitPrize = unitPrize;
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
        return "CartTM{" +
                "orderId='" + orderId + '\'' +
                ", code='" + code + '\'' +
                ", itemName='" + itemName + '\'' +
                ", customerId='" + customerId + '\'' +
                ", qty=" + qty +
                ", unitPrize=" + unitPrize +
                ", discount=" + discount +
                ", total=" + total +
                '}';
    }
}
