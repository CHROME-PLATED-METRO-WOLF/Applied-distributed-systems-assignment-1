
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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

    void forceSave() {
        saveFile(studentList, "studententry.txt", false);
        saveFile(logList, "logentry.txt", false);
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

            if (list.get(0) instanceof Student) {
                Student currentStudent;
                synchronized (studentList) {
                    while (iterator < list.size()) {
                        currentStudent = (Student) list.get(iterator);
                        bufferedWriter.write(currentStudent.getStudentNumber() + separator + currentStudent.getPinCode() + "\n");
                        iterator++;
                    }
                }
            } else if (list.get(0) instanceof LogEntry) {
                LogEntry currentLog;
                synchronized (logList) {
                    while (iterator < list.size()) {
                        currentLog = (LogEntry) list.get(iterator);
                        bufferedWriter.write(currentLog.getStudentNumber() + separator + currentLog.getDatetime() + currentLog.getPinCode() + "\n");
                        iterator++;
                    }
                }

            } else {
                //if this fires than it means a list of NOT Student or log entry was givin
                TCPServer.logger.log("CRITICAL: Error both lists are not instances of");
            }

        } catch (IOException ex) {
            TCPServer.logger.log("ERROR: IO exception when writing lists to file");
        } catch (java.lang.IndexOutOfBoundsException e) {
            TCPServer.logger.log("Lists are empty not saving");
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

            if (list.get(0) instanceof Student) {
                Student currentStudent;
                synchronized (studentList) {
                    while (iterator < list.size()) {
                        currentStudent = (Student) list.get(iterator);
                        bufferedWriter.write(currentStudent.getStudentNumber() + separator + currentStudent.getPinCode() + "\n");
                        iterator++;
                    }
                }
            } else if (list.get(0) instanceof LogEntry) {
                LogEntry currentLog;
                synchronized (logList) {
                    while (iterator < list.size()) {
                        currentLog = (LogEntry) list.get(iterator);
                        bufferedWriter.write(currentLog.getStudentNumber() + separator + currentLog.getDatetime() + currentLog.getPinCode() + "\n");
                        iterator++;
                    }
                }

            } else {
                //if this fires than it means a list of NOT Student or log entry was givin
                TCPServer.logger.log("CRITICAL: Error both lists are not instances of");
            }

        } catch (IOException ex) {
            TCPServer.logger.log("ERROR: IO exception when writing lists to file");
        } catch (java.lang.IndexOutOfBoundsException e) {
            TCPServer.logger.log("Lists are empty not saving");
        } finally {
            try {
                bufferedWriter.close();
                writer.close();
            } catch (IOException ex) {
                
                TCPServer.logger.log("ERROR: Log lists: cannot close streams");
            }
        }
    }
    
    void loadFiles() {
        
    }

    /*
    private void loadFile(List list, String File) {
        File file = new File(File);
        FileReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            //fix this later

            reader = new FileReader(file);
            bufferedReader = new BufferedReader(reader);

            int iterator = 0;

            if (list instanceof Student) {
 
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    for (String line; (line = br.readLine()) != null;) {
                        // process the line.
                        
                    }
                    // line is not visible here.
                }

         
            } else if (list instanceof LogEntry) {
                LogEntry currentLog;
                while (iterator < list.size()) {
                    currentLog = (LogEntry) list.get(iterator);
                    bufferedReader.read(currentLog.getStudentNumber() + separator + currentLog.getPinCode());

                }

            }

        } catch (IOException ex) {
            TCPServer.logger.log("ERROR: IO exception when writing lists to file");
        } finally {
            try {
                bufferedReader.close();
                reader.close();
            } catch (IOException ex) {

                TCPServer.logger.log("ERROR: Log lists: cannot close streams");
            }
        }
    }
     */
    
    void exit() {
        exit = true;
    }
    
}
