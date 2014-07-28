
import java.util.*;

/**
 * @author GHPaetzold
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        args = new String[4];
        args[0] = "../automata.txt";
        args[1] = "../reserved.txt";
        args[2] = "../code.txt";
        args[3] = "-h";

        //Check to see if the parameters are all present:
        if (args.length < 4) {
            if (args.length == 3) {
                System.out.println("");
                System.out.println("Missing output option parameter!");
                System.out.println("");
                printHelpMessage();
            } else {
                printHelpMessage();
            }
        } else {
            //Receive and store the parameters.
            String automata = args[0];
            String reserved = args[1];
            String code = args[2];
            String output = args[3];

            if (output.toLowerCase().equals("-h") || output.toLowerCase().equals("--help") || output.toLowerCase().equals("-help")) {
                printHelpMessage();
            } else {
                //Conduct lexical analysis on code file and store the time taken.
                LexicalAnalyzer al = null;
                try {
                    al = new LexicalAnalyzer(code, automata, reserved);
                } catch (Exception e) {
                    System.out.println("Failed to conduct lexical analysis, review input files.");
                    return;
                }

                //Print the results:
                switch (output.toLowerCase()) {
                    case "-t":
                        printTokens(al);
                        break;
                    case "-e":
                        printErrorsAndTokens(al);
                        break;
                    default:
                        System.out.println("Output option unknown.");
                        break;
                }
            }
        }

    }

    private static void printHelpMessage() {
        System.out.println("");
        System.out.println("Usage:");
        System.out.println("\tjava -jar EXTLex.jar <automata> <reserved_words> <code> <output_option>");
        System.out.println("");
        System.out.println("Output Options:");
        System.out.println("\t-h\tPrints this message.");
        System.out.println("\t-t\tPrints only the tokens generated.");
        System.out.println("\t-e\tPrints a report of the tokens generated and lexical errors found.");
        System.out.println("");
    }

    private static void printTokens(LexicalAnalyzer al) {
        System.out.println("Tokens:");
        for (Token item : al.getTokenList()) {
            System.out.print(item.getToken() + " ");
        }
    }

    private static void printErrorsAndTokens(LexicalAnalyzer al) {
        //Print the tokenized code and the time taken to produce it.
        System.out.println("Tokens:");
        for (Token item : al.getTokenList()) {
            System.out.print(item.getToken() + " ");
        }
        System.out.println("\n");

        //Print all errors found.
        ArrayList<String> errors = al.getErrors();
        if (errors.size() > 0) {
            System.out.println("Errors:");
            for (String error : errors) {
                System.out.println(error);
            }
            System.out.println("");
        }
    }
}
