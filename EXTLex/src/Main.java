
import java.util.*;

/**
 * @author GHPaetzold
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        args = new String[3];
        args[0] = "automata.txt";
        args[1] = "reserved.txt";
        args[2] = "code.txt";

        //Check to see if the parameters are all present:
        if (args.length < 3) {
            printHelpMessage();
        } else {
            //Receive and store the parameters.
            String automata = args[0];
            String reserved = args[1];
            String code = args[2];

            //Conduct lexical analysis on code file and store the time taken.
            LexicalAnalyzer al = null;
            al = new LexicalAnalyzer(code, automata, reserved);

            //Print the results:
            printOutput(al);

        }

    }

    private static void printHelpMessage() {
        System.out.println("");
        System.out.println("Usage:");
        System.out.println("\tjava -jar EXTLex.jar <automata> <reserved_words> <code>");
        System.out.println("");
    }

    private static void printOutput(LexicalAnalyzer al) {
        //Print the tokenized code and the time taken to produce it.
        System.out.println("Tokens and Lexemes:");
        for (Token item : al.getTokenList()) {
            System.out.println(item.getToken() + "\t" + item.getValue());
        }
        System.out.println("");

        //Print all errors found.
        System.out.println("Errors:");
        ArrayList<String> errors = al.getErrors();
        if (errors.size() == 0) {
            System.out.println("No lexical errors identified!");
        } else {
            for (String error : errors) {
                System.out.println(error);
            }
        }
    }
}
