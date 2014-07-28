package lexicalanalyzer;

import java.util.*;

/**
 *
 * @author GHPaetzold
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){

        args = new String[3];
        args[0] = "codigo.txt";
        args[1] = "automato.txt";
        args[2] = "reservados.txt";
        
        //Receive and store the parameters.
        String code = args[0];
        String automata = args[1];
        String reserved = args[2];
        
        //Conduct lexical analysis on code file and store the time taken.
        long time = System.currentTimeMillis();
        LexicalAnalyzer al = new LexicalAnalyzer(code, automata, reserved);
        time = System.currentTimeMillis() - time;
        
        //Print all errors found.
        ArrayList<String> erros = al.getErrors();
        if(erros.size()>0){
            System.out.println("Errors found:");
            System.out.println("");
            for(String erro: erros){
                System.out.println(erro);
            }
            System.out.println("");
        }
        
        //Print the tokenized code and the time taken to produce it.
        System.out.println("Tokenized code after " + time + " miliseconds of total lexical analysis:");
        int lastLine = -1;
        for(Token item: al.getTokenList()){  
            if(lastLine!=item.getLine()){
                System.out.println("");
                System.out.print("Line " + item.getLine() + ": ");
            }
            System.out.print(item.getToken() + " ");
            lastLine = item.getLine();
        }
        System.out.println("");
        System.out.println("");
        
        HashMap<String, Symbol> symbolTable = al.getSymbolTable();
        Set<String> keys = symbolTable.keySet();
        System.out.println("Resulting symbol table:");
        System.out.println("");
        for(String key: keys){
            Symbol s = symbolTable.get(key);
            System.out.println("ID: " + s.getId());
        }
    }
}
