
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Logger {

    FileWriter fr = null;
    BufferedWriter br = null;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime timeStamp;
    boolean print;
    boolean writeToFile;
    File file;

    Logger() {
        this.print = false;
        this.writeToFile = false;
        initialiseFile();
    }

    Logger(boolean print, boolean writeToFile) {
        this.print = print;
        this.writeToFile = writeToFile;
        this.file = new File("lastRun.log");
        initialiseFile();
    }

    Logger(boolean print, boolean writeToFile, String file) {
        this.print = print;
        this.writeToFile = writeToFile;
        this.file = new File(file);
        initialiseFile();
    }

    Logger(boolean print, boolean writeToFile, File file) {
        this.print = print;
        this.writeToFile = writeToFile;
        this.file = file;
        initialiseFile();
    }

    void log(String data) {
        timeStamp = LocalDateTime.now();
        data = dtf.format(timeStamp) + ": " + data + "\n";
        if (print == true) {
            System.out.print(data);
        }
        if (writeToFile == true) {
            writeToFile(data);
        }
    }

    private void initialiseFile() {
        //only clear the file if file logging is enabled
        if (writeToFile == true) {
            try {
                new PrintWriter(file).close();
            } catch (FileNotFoundException ex) {
                //file doesnt exist can safely ignore
            }

            try {
                fr = new FileWriter(file);
                br = new BufferedWriter(fr);
            } catch (IOException ex) {
                System.out.println("INTERNAL ERROR: Cannot crate logger file writer.");
            }

        }
    }

    private void writeToFile(String data) {

        try {

            br.write(data);
            br.flush();
        } catch (IOException ex) {
            System.out.println("INTERNAL ERROR: Cannot write log message to file");
        }

    }

}
