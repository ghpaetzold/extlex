/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author GHPaetzold
 */
public class LexicalAnalyzer {

    //Paths of reference files.
    private String pathCode;
    private String pathStates;
    private String pathReserved;
    //Token automata.
    private Automata automata;
    //Code.
    private String linearCode;
    //Error list.
    private ArrayList<String> errors;
    //Symbol table.
    private HashMap<String, Symbol> symbolTable;
    //Token list.
    private ArrayList<Token> tokenList;
    //Line controller.
    private Integer currLine;
    //Function map.
    private HashMap<String, Method> functionMap;

    public LexicalAnalyzer() {
        this.pathCode = this.pathStates = this.pathReserved = "";
        this.errors = null;
        this.symbolTable = null;

        this.functionMap = new HashMap<>();
        try {
            this.functionMap.put("LEXEME", this.getClass().getMethod("LEXEME", String.class, Automata.class, ArrayList.class, String.class, Integer.class));
            this.functionMap.put("NEWLINE", this.getClass().getMethod("NEWLINE", String.class, Automata.class, ArrayList.class, String.class, Integer.class));
            this.functionMap.put("NULL", this.getClass().getMethod("NULL", String.class, Automata.class, ArrayList.class, String.class, Integer.class));
        } catch (NoSuchMethodException | SecurityException ex) {
        }
    }

    public LexicalAnalyzer(String pathCode, String pathStates, String pathReserved) {
        this.pathCode = pathCode;
        this.pathStates = pathStates;
        this.pathReserved = pathReserved;
        this.errors = null;
        this.symbolTable = null;

        this.functionMap = new HashMap<>();
        try {
            this.functionMap.put("LEXEME", this.getClass().getMethod("LEXEME", String.class, Automata.class, ArrayList.class, String.class, Integer.class));
            this.functionMap.put("NEWLINE", this.getClass().getMethod("NEWLINE", String.class, Automata.class, ArrayList.class, String.class, Integer.class));
            this.functionMap.put("NULL", this.getClass().getMethod("NULL", String.class, Automata.class, ArrayList.class, String.class, Integer.class));
        } catch (NoSuchMethodException | SecurityException ex) {
        }

        this.initializeAnalyzer();
    }

    private void initializeAnalyzer() {

        //Check for missing reference files.
        if (getPathCode().isEmpty() || getPathStates().isEmpty() || getPathReserved().isEmpty()) {
            System.out.println("Reference files missing, analyzer could not perform initialization.");
            return;
        }

        //Read code from file.
        BufferedReader br;
        linearCode = "";
        try {
            br = new BufferedReader(new FileReader(new File(getPathCode())));
            while (br.ready()) {
                linearCode += (char) br.read();
            }
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Code file not found, analyzer could not initialize.");
        } catch (IOException ex) {
            System.out.println("Failure in reading automata, analyzer could not initialize.");
        }

        //Normalize the code to avoid errors.
        linearCode = linearCode + " ";

        //Produce automata from file.
        automata = readStates(getPathStates());
        automata.setReserved(readReserved(getPathReserved()));
        tokenList = processCode(linearCode, automata);

    }

    private Automata readStates(String p) {
        Automata result = new Automata();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(p)));
        } catch (FileNotFoundException ex) {
            System.out.println("Error in readStates allocating reader: " + ex.getLocalizedMessage());
            return null;
        }

        try {

            while (br.ready()) {
                LexState state = new LexState();

                String id = br.readLine().trim();
                String token = br.readLine().trim();
                String value = br.readLine().trim();

                HashMap<Character, String> map = state.getMap();
                String transition = br.readLine().trim();
                while (!transition.isEmpty()) {
                    String[] data = transition.split(" ");
                    if (data[0].contains("~")) {
                        String[] aux = data[0].split("~");
                        int ini = Integer.valueOf(aux[0].trim());
                        int end = Integer.valueOf(aux[1].trim());
                        for (int i = ini; i <= end; i++) {
                            map.put((char) i, data[1].trim());
                        }
                    } else {
                        map.put((char) (int) Integer.valueOf(data[0].trim()), data[1].trim());
                    }
                    transition = br.readLine().trim();
                }

                state.setId(id);
                state.setToken(token);
                state.setMap(map);
                state.setValue(value);
                result.addState(state);
            }
            br.close();

        } catch (IOException ex) {
            System.out.println("Error in readStates reading states file: " + ex.getLocalizedMessage());
            return null;
        }

        result.setFirstState(result.getStates().get(0).getId());

        return result;
    }

    private ArrayList<Token> processCode(String c, Automata a) {

        String initialState = a.getFirstState();
        this.errors = new ArrayList<>();
        this.symbolTable = new HashMap<>();

        ArrayList<Token> result = new ArrayList<>();
        char[] code = c.toCharArray();

        HashMap<String, LexState> idmap = a.getIdmap();

        LexState currState = idmap.get(initialState);

        int n = 0;
        this.currLine = 1;
        String lexema = "";

        while (n < code.length) {

            //Salva estado anterior.
            LexState prev = currState;

            //Calcula estado seguinte.
            currState = a.getNexState(prev, code[n]);

            if (initialState.equals(currState.getId())) {

                String resultToken = prev.getToken();
                String resultValue = prev.getValue();

                //If its an error, add it to the error list.
                if (resultValue.startsWith("ERROR")) {
                    getErrors().add("Line " + this.currLine + ": " + prev.getToken());
                    n++;
                } else {
                    //Get the method with its name corresponding to the value of the previous state.
                    try {
                        Method m = this.functionMap.get(resultValue);
                        m.invoke(this, lexema, a, result, resultToken, this.currLine);
                    } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    }
                }

                //Reset lexema after retrieving new token.
                lexema = "";
            } else {
                lexema += code[n];
                n++;
            }
        }

        return result;
    }

    private HashMap<String, Integer> readReserved(String p) {
        HashMap<String, Integer> result = new HashMap<>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(p)));
        } catch (FileNotFoundException ex) {
            System.out.println("Erro abrindo arquivo de palavras reservadas: " + ex.getLocalizedMessage());
            return null;
        }
        try {
            while (br.ready()) {
                result.put(br.readLine().trim(), 1);
            }
        } catch (IOException ex) {
            System.out.println("Erro na leitura das palavras reservadas: " + ex.getLocalizedMessage());
            return null;
        }

        return result;
    }

    private boolean isReserved(String l, Automata a) {
        return (a.getReserved().get(l) != null);
    }

    /**
     * @return the pathCode
     */
    public String getPathCode() {
        return pathCode;
    }

    /**
     * @param pathCode the pathCode to set
     */
    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    /**
     * @return the pathStates
     */
    public String getPathStates() {
        return pathStates;
    }

    /**
     * @param pathStates the pathStates to set
     */
    public void setPathStates(String pathStates) {
        this.pathStates = pathStates;
    }

    /**
     * @return the pathReserved
     */
    public String getPathReserved() {
        return pathReserved;
    }

    /**
     * @param pathReserved the pathReserved to set
     */
    public void setPathReserved(String pathReserved) {
        this.pathReserved = pathReserved;
    }

    private String addSymbolTableEntry(String value) {
        Symbol s = new Symbol(value);
        this.getSymbolTable().put(value, s);
        return value;
    }

    public void LEXEME(String lexema, Automata a, ArrayList<Token> result, String resultToken, Integer line) {
        if (this.isReserved(lexema, a)) {
            result.add(new Token(lexema, lexema, line));
        } else {
            result.add(new Token(resultToken, lexema, line));
        }
    }

    public void NEWLINE(String lexema, Automata a, ArrayList<Token> result, String resultToken, Integer line) {
        this.currLine++;
    }

    public void NULL(String lexema, Automata a, ArrayList<Token> result, String resultToken, Integer line) {
    }

    /**
     * @return the tokenList
     */
    public ArrayList<Token> getTokenList() {
        return tokenList;
    }

    /**
     * @param tokenList the tokenList to set
     */
    public void setTokenList(ArrayList<Token> tokenList) {
        this.tokenList = tokenList;
    }

    /**
     * @return the errors
     */
    public ArrayList<String> getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    /**
     * @return the symbolTable
     */
    public HashMap<String, Symbol> getSymbolTable() {
        return symbolTable;
    }

    /**
     * @param symbolTable the symbolTable to set
     */
    public void setSymbolTable(HashMap<String, Symbol> symbolTable) {
        this.symbolTable = symbolTable;
    }
}
