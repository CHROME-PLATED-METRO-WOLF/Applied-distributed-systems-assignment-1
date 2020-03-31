
public class LogEntry {
String studentNumber;
int pinCode;
String dateTime;


LogEntry()
{
    
}

LogEntry(String studentNumber, String dateTime)
{
    this.studentNumber = studentNumber;
    this.dateTime = dateTime;
}

LogEntry(String studentNumber, String dateTime, int pinCode)
{
    this.studentNumber = studentNumber;
    this.dateTime = dateTime;
    this.pinCode = pinCode;
}

void setStudentNumber(String studentNumber)
{
    this.studentNumber = studentNumber;
}

String getStudentNumber()
{
    return this.studentNumber;
}

void setPinCode(int pinCode)
{
    this.pinCode = pinCode;
}

int getPinCode()
{
    return this.pinCode;
}

void setDateTime(String dateTime)
{
    this.dateTime = dateTime;
}

String getDatetime()
{
    return this.dateTime;
}

}
