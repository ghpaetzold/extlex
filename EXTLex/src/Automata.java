/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author GHPaetzold
 */
public class Automata {
 
    private ArrayList<LexState> states;
    private HashMap<String, LexState> idmap;
    private HashMap<String, Integer> reserved;
    private String firstState;

    public Automata() {
        states = new ArrayList<>();
        idmap = new HashMap<>();
        reserved = new HashMap<>();
    }

    public Automata(ArrayList<LexState> states, HashMap<String, LexState> idmap, HashMap<String, Integer> reserved) {
        this.states = states;
        this.idmap = idmap;
        this.reserved = reserved;
    }

    /**
     * @return the states
     */
    public ArrayList<LexState> getStates() {
        return states;
    }

    /**
     * @param states the states to set
     */
    public void setStates(ArrayList<LexState> states) {
        this.states = states;
        this.setIdmap(buildIdMap(this.states));
    }

    void addState(LexState state) {
        this.states.add(state);
        this.getIdmap().put(state.getId(), state);
    }

    private HashMap<String, LexState> buildIdMap(ArrayList<LexState> states) {
        HashMap<String, LexState> result = new HashMap<>();
        for(LexState ls: this.states){
            result.put(ls.getId(), ls);
        }
        return result;
    }

    /**
     * @return the idmap
     */
    public HashMap<String, LexState> getIdmap() {
        return idmap;
    }

    /**
     * @param idmap the idmap to set
     */
    public void setIdmap(HashMap<String, LexState> idmap) {
        this.idmap = idmap;
    }

    LexState getNexState(LexState prev, char c) {
        if(prev.getMap()==null){
            return idmap.get(firstState);
        }
        String nextId = prev.getMap().get(c);
        LexState next = idmap.get(nextId);
        if(next==null){
            return idmap.get(firstState);
        }else{
            return next;
        }
    }

    /**
     * @return the reserved
     */
    public HashMap<String, Integer> getReserved() {
        return reserved;
    }

    /**
     * @param reserved the reserved to set
     */
    public void setReserved(HashMap<String, Integer> reserved) {
        this.reserved = reserved;
    }

    /**
     * @param firstState the firstState to set
     */
    public void setFirstState(String firstState) {
        this.firstState = firstState;
    }

    /**
     * @return the firstState
     */
    public String getFirstState() {
        return firstState;
    }
    
}
