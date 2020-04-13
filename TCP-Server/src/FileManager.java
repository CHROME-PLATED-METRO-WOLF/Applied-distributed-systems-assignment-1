
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//each time the list is saved save a hash of both lists and only if the lists have changed should they be rewritten to the file
public class FileManager extends Thread {

    private List<Student> studentList = Collections.synchronizedList(new ArrayList());

    private List<LogEntry> logList = Collections.synchronizedList(new ArrayList());

    private int msInterval;
    private boolean exit = false;
    final private String separator = " ";

    FileManager(List<Student> studentList, List<LogEntry> logList, int msInterval) {
        this.studentList = studentList;
        this.logList = logList;
        this.msInterval = msInterval;
    }

    public void run() {

        while (exit == false) {

            try {
                Thread.sleep(msInterval);
                TCPServer.logger.log("Saving lists");
                saveFile(studentList, "studententry.txt", true);
                saveFile(logList, "logentry.txt", true);
                TCPServer.logger.log("Lists saved");
            } catch (InterruptedException ex) {
                TCPServer.logger.log("ERROR: file manager cannot sleep thread");
            }
        }

    }

    private void saveFile(List list, String File, boolean append) {
        File file = new File(File);
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            //fix this later

            writer = new FileWriter(file, append);
            bufferedWriter = new BufferedWriter(writer);

            int iterator = 0;

            if (list instanceof Student) {
                Student currentStudent;
                while (iterator < list.size()) {
                    currentStudent = (Student) list.get(iterator);
                    bufferedWriter.write(currentStudent.getStudentNumber() + separator + currentStudent.getPinCode());
                }
            } else if (list instanceof LogEntry) {
                LogEntry currentLog;
                while (iterator < list.size()) {
                    currentLog = (LogEntry) list.get(iterator);
                    bufferedWriter.write(currentLog.getStudentNumber() + separator + currentLog.getPinCode());

                }

            }

        } catch (IOException ex) {
            TCPServer.logger.log("ERROR: IO exception when writing lists to file");
        } finally {
            try {
                bufferedWriter.close();
                writer.close();
            } catch (IOException ex) {

                TCPServer.logger.log("ERROR: Log lists: cannot close streams");
            }
        }
    }

    private void saveFile(List<Object> list, File File, boolean append) {
        File file = File;
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            //fix this later

            writer = new FileWriter(file, append);
            bufferedWriter = new BufferedWriter(writer);

            int iterator = 0;

            if (list instanceof Student) {
                Student currentStudent;
                while (iterator < list.size()) {
                    currentStudent = (Student) list.get(iterator);
                    bufferedWriter.write(currentStudent.getStudentNumber() + separator + currentStudent.getPinCode());
                }
            } else if (list instanceof LogEntry) {
                LogEntry currentLog;
                while (iterator < list.size()) {
                    currentLog = (LogEntry) list.get(iterator);
                    bufferedWriter.write(currentLog.getStudentNumber() + separator + currentLog.getPinCode());

                }

            }

        } catch (IOException ex) {
            TCPServer.logger.log("ERROR: IO exception when writing lists to file");
        } finally {
            try {
                bufferedWriter.close();
                writer.close();
            } catch (IOException ex) {

                TCPServer.logger.log("ERROR: Log lists: cannot close streams");
            }
        }
    }

    void exit() {
        exit = true;
    }

}
