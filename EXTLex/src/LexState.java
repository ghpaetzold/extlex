/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.HashMap;

/**
 *
 * @author GHPaetzold
 */
public class LexState {
    
    private String id;
    private String token;
    private String value;
    private HashMap<Character, String> map;

    public LexState(String id, String token, String value, HashMap<Character, String> map) {
        this.id = id;
        this.token = token;
        this.value = value;
        this.map = map;
    }

    public LexState() {
        this.map = new HashMap<>();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the map
     */
    public HashMap<Character, String> getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(HashMap<Character, String> map) {
        this.map = map;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    
    
}
