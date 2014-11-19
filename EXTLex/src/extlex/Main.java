package extlex;

import java.util.*;

/**
 * @author Gustavo Henrique Paetzold
 */
public class Main {
    
    public static void main(String[] args) {

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
        if (errors.isEmpty()) {
            System.out.println("No lexical errors identified!");
        } else {
            for (String error : errors) {
                System.out.println(error);
            }
        }
    }
}
