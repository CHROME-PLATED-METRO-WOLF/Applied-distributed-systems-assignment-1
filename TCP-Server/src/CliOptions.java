
import java.lang.management.ManagementFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

//pure data structure for command line options
public class CliOptions {

    private final Options options;
    private final CommandLineParser parser = new DefaultParser();
    private final HelpFormatter formatter = new HelpFormatter();
    private boolean setExit = true;
    private CommandLine cmd;
    private final String args[];
    private String programName = "program";
    private String helpOptionTxt;
    private int msInterval = 300000;
    private int msDelay = 100;

    private int serverPort = 8888;
    private int maxConnections = 100;
    private int fileWriteDelay;

    private int loggingLevel;
    private String logFile;

    CliOptions(String args[]) {
        this.options = new Options();
        this.args = args;

    }

    void setPredefined() {
        //list of options specific for this program
        //allthough this class can be used in other projects normally using the addOptions and removeOptions method
        //this method is for quickly adding options
        setHelpOption("h", "help", "display all options");
        addOption("p", "port", true, "custom port number", false);
        addOption("m", "max-connections", true, "custom port number", false);
        addOption("cci", "connection-check-interval", true, "MS Time between connection checks", false);
        addOption("ccd", "connection-check-delay", true, "MS Delay between checking each connection", false);
        addOption("fwd", "file-write-delay", true, "MS Delay writing data to files", false);
        addOption("l", "log-level", true, "Logging level 0 = none (default) 1 = app output 2 = file logging 3 = file and app logging", false);
        addOption("lf", "log-file", true, "Log file name default is lastRun.log NOTE: log-level must be set to 2 or 3 to log to file", false);
        addOption("v", "version", false, "Displays version informtion", false);

    }

    void addOption(String shortText, String longText, boolean argRequired, String description, boolean requiredOption) {
        Option newOption = new Option(shortText, longText, argRequired, description);
        newOption.setRequired(requiredOption);
        options.addOption(newOption);
    }

    void setHelpOption(String shortText, String longText, String description) {
        Option helpOption = new Option(shortText, longText, false, description);
        helpOption.setRequired(false);
        options.addOption(helpOption);
        helpOptionTxt = shortText;
    }

    void parseOptions() {

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption(helpOptionTxt)) {
                printHelp();
                exit();
            }

            if (cmd.hasOption("p")) {

                if (isPortValid(cmd.getOptionValue("p"))) {
                    if (Integer.parseInt(cmd.getOptionValue("p")) < 1024) {
                        System.out.println("port is less than 1024 root may be required to run on that port");
                    }
                    this.serverPort = Integer.parseInt(cmd.getOptionValue("p"));
                    System.out.println(serverPort);

                } else {
                    System.out.println("Please enter a port number between 1 and 65535");
                    exit();
                }

            } else {
                this.serverPort = 8888;
            }

            if (cmd.hasOption("m")) {

                try {
                    this.maxConnections = Integer.parseInt(cmd.getOptionValue("m"));
                    if (this.maxConnections < 0) {
                        System.out.println("Connections must be above 0");
                        exit();
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number for the max connections");
                    exit();
                }
            }

            if (cmd.hasOption("cci")) {
                try {
                    this.msInterval = Integer.parseInt(cmd.getOptionValue("cci"));
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number for connection check interval");
                    exit();
                }
            }
            if (cmd.hasOption("ccd")) {
                try {
                    this.msDelay = Integer.parseInt(cmd.getOptionValue("cci"));
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number for connection check interval");
                    exit();
                }
            }

            if (cmd.hasOption("fwd")) {
                try {
                    this.fileWriteDelay = Integer.parseInt(cmd.getOptionValue("fwd"));
                    if (this.fileWriteDelay < 30000) {
                        System.out.println("WARNING: file write delay is less than 30 seconds.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number the write delay");
                    exit();
                }

            }

            if (cmd.hasOption("l")) {
                try {
                    this.loggingLevel = Integer.parseInt(cmd.getOptionValue("l"));
                    if (this.loggingLevel < 0 || this.loggingLevel > 3) {
                        System.out.println("Error please enter a number between 0 and 3");
                        exit();
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Error please enter a number between 0 and 3");
                    exit();
                }
            }

            if (cmd.hasOption("lf")) {

                this.logFile = cmd.getOptionValue("lf");

            }

            if (cmd.hasOption("v")) {

                System.out.println("Version Alpha 0.1 \n test version \n Built 10/04/2020 8:21:16");
                System.out.println("JVM running in: " + System.getProperty("sun.arch.data.model") + " bit mode");
                System.out.println("OS Arch: " + System.getProperty("os.arch"));
                System.out.println("VM Version: " + ManagementFactory.getRuntimeMXBean().getSpecVersion());
                exit();

            }

        } catch (ParseException e) {
            printHelp();

            exit();
        }

    }

    static boolean isPortValid(String port) {
        try {
            int portNum;
            portNum = Integer.parseInt(port);
            if (portNum > 65535 || portNum < 1) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error please enter a valid number for the port");
            return false;
        }
    }

    void setSysExit(boolean exit) {
        this.setExit = exit;
    }

    private void exit() {
        if (this.setExit == true) {
            System.exit(1);
        }
    }

    void printHelp() {
        formatter.printHelp(programName, options);
        exit();
    }

    void setProgramName(String helpName) {
        this.programName = helpName;
    }

    int getServerPort() {
        return this.serverPort;
    }

    int getMaxConnections() {
        return this.maxConnections;
    }

    int getMsInterval() {
        return this.msInterval;
    }

    int getMsDelay() {
        return this.msDelay;
    }

    int getFileWriteDelay() {
        return this.fileWriteDelay;
    }
    
    int getLoggingLevel()
    {
        return this.loggingLevel;
    }
    
    String getLogFile()
    {
        return this.logFile;
    }

}
