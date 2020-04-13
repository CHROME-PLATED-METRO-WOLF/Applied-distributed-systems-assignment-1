
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//each time the list is saved save a hash of both lists and only if the lists have changed should they be rewritten to the file
//you could read the 2 files in parell on other threads
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
        if (list.size() == 0) {
            TCPServer.logger.log("List is empty not saving");
        } else {
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
                }

                iterator = 0;

                if (list.get(0) instanceof LogEntry) {
                    LogEntry currentLog;
                    synchronized (logList) {
                        while (iterator < list.size()) {
                            currentLog = (LogEntry) list.get(iterator);
                            bufferedWriter.write(currentLog.getStudentNumber() + separator + currentLog.getDatetime() + currentLog.getPinCode() + "\n");
                            iterator++;
                        }
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

    }

    private void saveFile(List<Object> list, File File, boolean append) {
        if (list.size() == 0) {
            TCPServer.logger.log("List is empty not saving");
        } else {
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
                }

                iterator = 0;

                if (list.get(0) instanceof LogEntry) {
                    LogEntry currentLog;
                    synchronized (logList) {
                        while (iterator < list.size()) {
                            currentLog = (LogEntry) list.get(iterator);
                            bufferedWriter.write(currentLog.getStudentNumber() + separator + currentLog.getDatetime() + currentLog.getPinCode() + "\n");
                            iterator++;
                        }
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

    }

    void loadFiles() {
        TCPServer.logger.log("Loading Lists");
        loadFile(studentList, logList, "studententry.txt", "logentry.txt");
        TCPServer.logger.log("Lists Loaded");
    }

    //java does not allow you to check if a generic list is a certain type at runtime which is SO ANOYING
    //That means i cannot turn the load files into a generic method like the save file methods
    //there is a work around where you create a new blank list of student and logentry and then
    //use the instanceof to compare the lists but i refuse to add that extra inefficeny to the program
    //Actually that wont work, you could try casting the generic list to the blank list and if it throws an
    //exception try the other type list but once again that adds more cpu cycles to the program
    //so i decided to just not use generics and do the plain old single method for both lists
    //it achieves the same thing but with a bit more code :(
    private void loadFile(List<Student> StudentList, List<LogEntry> LogEntryList, String StudentFile, String LogEntryFile) {
        File studentFile = new File(StudentFile);
        File logEntryFile = new File(LogEntryFile);

        FileReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            //fix this later

            reader = new FileReader(studentFile);
            bufferedReader = new BufferedReader(reader);

            String[] data;
            synchronized (StudentList) {
                for (String line; (line = bufferedReader.readLine()) != null;) {
                    data = line.split(separator);
                    StudentList.add(new Student(data[0], Integer.parseInt(data[1])));
                }
            }
            reader = new FileReader(logEntryFile);
            synchronized (LogEntryList) {
                for (String line; (line = bufferedReader.readLine()) != null;) {
                    data = line.split(separator);
                    LogEntryList.add(new LogEntry(data[0], data[1], Integer.parseInt(data[2])));
                }
            }
        } catch (FileNotFoundException ex) {
            TCPServer.logger.log("ERROR: Loading files, files not found");

        } catch (IOException ex) {
            TCPServer.logger.log("ERROR: IO exception when reading lists from file");
        } finally {
            try {
                bufferedReader.close();
                reader.close();

            } catch (IOException ex) {

                TCPServer.logger.log("ERROR: Log lists: cannot close streams");
            } catch (java.lang.NullPointerException e) {
                TCPServer.logger.log("Error streams dont exist!");
            }
        }
    }

    void exit() {
        exit = true;
    }

}
