package model;

public class Cashier {
    private String empId;
    private String empName;
    private String empPassword;
    private String empNic;
    private String empContact;
    private String empDescription;
    private String empAddress;
    private Double empSalary;

    public Cashier() {
    }

    public Cashier(String empId, String empName, String empPassword, String empNic, String empContact, String empDescription, String empAddress, Double empSalary) {
        this.empId = empId;
        this.empName = empName;
        this.empPassword = empPassword;
        this.empNic = empNic;
        this.empContact = empContact;
        this.empDescription = empDescription;
        this.empAddress = empAddress;
        this.empSalary = empSalary;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    public String getEmpNic() {
        return empNic;
    }

    public void setEmpNic(String empNic) {
        this.empNic = empNic;
    }

    public String getEmpContact() {
        return empContact;
    }

    public void setEmpContact(String empContact) {
        this.empContact = empContact;
    }

    public String getEmpDescription() {
        return empDescription;
    }

    public void setEmpDescription(String empDescription) {
        this.empDescription = empDescription;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public Double getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(Double empSalary) {
        this.empSalary = empSalary;
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empPassword='" + empPassword + '\'' +
                ", empNic='" + empNic + '\'' +
                ", empContact='" + empContact + '\'' +
                ", empDescription='" + empDescription + '\'' +
                ", empAddress='" + empAddress + '\'' +
                ", empSalary=" + empSalary +
                '}';
    }
}
