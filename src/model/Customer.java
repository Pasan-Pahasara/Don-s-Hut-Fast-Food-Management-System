package model;

public class Customer {
    private String cusId;
    private String cusTitle;
    private String cusName;
    private String cusAddress;
    private String cusCity;
    private String cusProvince;
    private String cusPostalCode;
    private String cusDate;
    private String cusTime;

    public Customer() {
    }

    public Customer(String cusId) {
        this.cusId = cusId;
    }

    public Customer(String cusId, String cusTitle, String cusName, String cusAddress, String cusCity, String cusProvince, String cusPostalCode) {
        this.cusId = cusId;
        this.cusTitle = cusTitle;
        this.cusName = cusName;
        this.cusAddress = cusAddress;
        this.cusCity = cusCity;
        this.cusProvince = cusProvince;
        this.cusPostalCode = cusPostalCode;
    }

    public Customer(String cusId, String cusTitle, String cusName, String cusAddress, String cusCity, String cusProvince, String cusPostalCode, String cusDate, String cusTime) {
        this.cusId = cusId;
        this.cusTitle = cusTitle;
        this.cusName = cusName;
        this.cusAddress = cusAddress;
        this.cusCity = cusCity;
        this.cusProvince = cusProvince;
        this.cusPostalCode = cusPostalCode;
        this.cusDate = cusDate;
        this.cusTime = cusTime;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getCusTitle() {
        return cusTitle;
    }

    public void setCusTitle(String cusTitle) {
        this.cusTitle = cusTitle;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getCusCity() {
        return cusCity;
    }

    public void setCusCity(String cusCity) {
        this.cusCity = cusCity;
    }

    public String getCusProvince() {
        return cusProvince;
    }

    public void setCusProvince(String cusProvince) {
        this.cusProvince = cusProvince;
    }

    public String getCusPostalCode() {
        return cusPostalCode;
    }

    public void setCusPostalCode(String cusPostalCode) {
        this.cusPostalCode = cusPostalCode;
    }

    public String getCusDate() {
        return cusDate;
    }

    public void setCusDate(String cusDate) {
        this.cusDate = cusDate;
    }

    public String getCusTime() {
        return cusTime;
    }

    public void setCusTime(String cusTime) {
        this.cusTime = cusTime;
    }
}
