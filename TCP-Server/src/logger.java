
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class logger {

    FileWriter fr = null;
    BufferedWriter br = null;

    boolean print;
    boolean writeToFile;
    File file;

    logger() {
        this.print = false;
        this.writeToFile = false;
        initialiseFile();
    }

    logger(boolean print, boolean writeToFile) {
        this.print = print;
        this.writeToFile = writeToFile;
        this.file = new File("lastRun.log");
        initialiseFile();
    }

    logger(boolean print, boolean writeToFile, String file) {
        this.print = print;
        this.writeToFile = writeToFile;
        this.file = new File(file);
        initialiseFile();
    }

    logger(boolean print, boolean writeToFile, File file) {
        this.print = print;
        this.writeToFile = writeToFile;
        this.file = file;
        initialiseFile();
    }

    void log(String data) {
        if (print == true) {
            System.out.println(data);
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
        } catch (IOException ex) {
            System.out.println("INTERNAL ERROR: Cannot write log message to file");
        }

    }

}
