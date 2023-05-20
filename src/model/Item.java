package model;

public class Item {
    private String itemCode;
    private String itemName;
    private int itemQtyOnHand;
    private String itemPackSize;
    private double itemUnitPrice;

    public Item() {
    }

    public Item(String itemName, int itemQtyOnHand, double itemUnitPrice) {
        this.itemName = itemName;
        this.itemQtyOnHand = itemQtyOnHand;
        this.itemUnitPrice = itemUnitPrice;
    }

    public Item(String itemCode, String itemName, int itemQtyOnHand, String itemPackSize, double itemUnitPrice) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemQtyOnHand = itemQtyOnHand;
        this.itemPackSize = itemPackSize;
        this.itemUnitPrice = itemUnitPrice;
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

    public int getItemQtyOnHand() {
        return itemQtyOnHand;
    }

    public void setItemQtyOnHand(int itemQtyOnHand) {
        this.itemQtyOnHand = itemQtyOnHand;
    }

    public String getItemPackSize() {
        return itemPackSize;
    }

    public void setItemPackSize(String itemPackSize) {
        this.itemPackSize = itemPackSize;
    }

    public double getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(double itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemQtyOnHand=" + itemQtyOnHand +
                ", itemPackSize='" + itemPackSize + '\'' +
                ", itemUnitPrice=" + itemUnitPrice +
                '}';
    }
}
