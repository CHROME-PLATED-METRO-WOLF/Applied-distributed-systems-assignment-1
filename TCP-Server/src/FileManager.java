
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager extends Thread {

    private ArrayList<Student> studentList;
    private ArrayList<LogEntry> logList;
    private int msInterval;
    private boolean exit = false;
    final private String separator = " ";

    FileManager(ArrayList<Student> studentList, ArrayList<LogEntry> logList) {
        this.studentList = studentList;
        this.logList = logList;
    }

    FileManager(ArrayList<Student> studentList, ArrayList<LogEntry> logList, int msInterval) {
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
                    System.out.println("CRITICAL ERROR: cant sleep");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
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
                    System.out.println("CRITICAL ERROR: cant sleep");
                } catch (IOException ex) {
                    Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}

