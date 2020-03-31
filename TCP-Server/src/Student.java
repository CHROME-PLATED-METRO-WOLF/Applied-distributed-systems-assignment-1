
// class for student information
public class Student {

    //private variables that hold the data for each student
    //may be expanded to hold some auxillary information such as time created, age ect
    private String studentNumber;
    int pinCode;

    //default constructor which does nothing :)
    Student() {

    }
//parameter constructor which builds a student with the correct variables

    Student(String studentNumber, int pinCode) {
        this.studentNumber = studentNumber;
        this.pinCode = pinCode;
    }

    //sets the student number
    void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    //gets the student number
    String getStudentNumber() {
        return studentNumber;
    }

    //sets the pin code
    void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    //gets the pincode
    int getPinCode() {
        return pinCode;
    }

    //an overide function which overides the toString function and returnes a string
    @Override
    public String toString() {
        return " -- Student Details -- " + "\n" + "Student: " + studentNumber + " Pincode: " + pinCode;
    }

    public String oldToString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

}
//end class