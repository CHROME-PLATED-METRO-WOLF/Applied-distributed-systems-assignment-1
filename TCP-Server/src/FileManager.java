
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileManager extends Thread {

    private List<Student> studentList = Collections.synchronizedList(new ArrayList());
    ;
    private List<LogEntry> logList = Collections.synchronizedList(new ArrayList());
    ;
    private int msInterval;
    private boolean exit = false;
    final private String separator = " ";

    FileManager(List<Student> studentList, List<LogEntry> logList) {
        this.studentList = studentList;
        this.logList = logList;
    }

    FileManager(List<Student> studentList, List<LogEntry> logList, int msInterval) {
        this.studentList = studentList;
        this.logList = logList;
        this.msInterval = msInterval;
    }

    public void run() {

    }

    private void saveStudents() {
        try {
            File studentFile = new File("studententry.txt");
            FileWriter studentWriter = null;
            BufferedWriter studentBufferedWriter = null;

            studentWriter = new FileWriter(studentFile);
            studentBufferedWriter = new BufferedWriter(studentWriter);

            while (exit = false) {
                try {
                    Thread.sleep(this.msInterval);
                    int iterator = 0;
                    Student currentStudent;
                    while (iterator < studentList.size()) {
                        currentStudent = this.studentList.get(iterator);
                        studentBufferedWriter.write(currentStudent.getStudentNumber() + separator + currentStudent.getPinCode());
                    }

                } catch (InterruptedException ex) {
                    TCPServer.logger.log("CRITICAL ERROR: cant sleep");
                }
            }
        } catch (IOException ex) {
            TCPServer.logger.log("Error cannot write to file");
        }
    }

    private void saveLog() {

        try {
            //fix this later
            File studentFile = new File("logentry.txt");
            FileWriter studentWriter = null;
            BufferedWriter studentBufferedWriter = null;

            studentWriter = new FileWriter(studentFile);
            studentBufferedWriter = new BufferedWriter(studentWriter);

            while (exit = false) {
                try {
                    Thread.sleep(this.msInterval);
                    int iterator = 0;
                    Student currentStudent;
                    while (iterator < studentList.size()) {
                        currentStudent = this.studentList.get(iterator);
                        studentBufferedWriter.write(currentStudent.getStudentNumber() + separator + currentStudent.getPinCode());
                    }

                } catch (InterruptedException ex) {
                    TCPServer.logger.log("CRITICAL ERROR: cant sleep");
                } catch (IOException ex) {
                    TCPServer.logger.log("Error cannot write to file");
                }
            }
        } catch (IOException ex) {
            TCPServer.logger.log("Error cannot write to file");
        }
    }

}
