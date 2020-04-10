
import java.io.File;


public class logger {
    boolean print;
    boolean writeToFile;
    File file;
    
    logger()
    {
       this.print = false;
       this.writeToFile = false;
    }
    
    logger(boolean print, boolean writeToFile)
    {
       this.print = print;
       this.writeToFile = writeToFile;
       this.file = new File("lastRun.log");
    }
    
    logger(boolean print, boolean writeToFile, String file)
    {
       this.print = print;
       this.writeToFile = writeToFile;
       this.file = new File(file);
    }
    
    logger(boolean print, boolean writeToFile, File file)
    {
       this.print = print;
       this.writeToFile = writeToFile;
       this.file = file;
    }
    
    void log()
    {
        
    }
    
    private void writeToFile()
    {
        
    }
    
    
}
