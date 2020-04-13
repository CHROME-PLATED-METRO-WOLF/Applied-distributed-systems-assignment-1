
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileManager extends Thread {

    private List<Student> studentList = Collections.synchronizedList(new ArrayList());

    private List<LogEntry> logList = Collections.synchronizedList(new ArrayList());

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

    private void saveFile(List<Object> list, String File, boolean append) {
        File file = new File(File);
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            //fix this later

            writer = new FileWriter(file);
            bufferedWriter = new BufferedWriter(writer);

            while (exit = false) {

                Thread.sleep(msInterval);
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
            }
        } catch (IOException ex) {
            TCPServer.logger.log("ERROR: IO exception when writing lists to file");
        } catch (InterruptedException ex) {
            TCPServer.logger.log("ERROR cannot sleep file manager thread");
        } finally {
            try {
                bufferedWriter.close();
                writer.close();
            } catch (IOException ex) {

                TCPServer.logger.log("ERROR: Log lists: cannot close streams");
            }
        }
    }

    private void saveFile(List<Object> list, File file, boolean append) {

    }

    void exit() {
        exit = true;
    }

}
