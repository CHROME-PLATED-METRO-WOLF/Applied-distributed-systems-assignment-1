
public class LogEntry {

    private String studentNumber;
    private int pinCode;
    private String dateTime;

    //default constructor which does nothing :)
    LogEntry() {

    }

    //constructor that sets student number and date time
    LogEntry(String studentNumber, String dateTime) {
        this.studentNumber = studentNumber;
        this.dateTime = dateTime;
    }

    //constructor that sets all variables
    LogEntry(String studentNumber, String dateTime, int pinCode) {
        this.studentNumber = studentNumber;
        this.dateTime = dateTime;
        this.pinCode = pinCode;
    }

    //setter for student number
    void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    //getter for student number
    String getStudentNumber() {
        return this.studentNumber;
    }

    //setter for pincode
    void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

//getter for pincode
    int getPinCode() {
        return this.pinCode;
    }

    //setter for date and time
    void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    //getter for date and time
    String getDatetime() {
        return this.dateTime;
    }

    
    @Override
    public String toString()
    {
        return "Student: " + studentNumber + " Pin Code: " + pinCode + " Date Time: " + dateTime;
        
    }
}
