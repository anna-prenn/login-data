package project3;

import java.util.*;

/**
 * This class creates a Record object.
 * 
 * It assigns the variables and throws an exception for any invalid terminal numbers.
 * 
 * It then has getters to access the variables in the class.
 * 
 * @author Anna Prenowitz
 */

public class Record implements Comparable<Record> {
	
	//Initialises variables
	private int terminal;
	private boolean login;
	private String username;
	private Date time;
	
	/**
	 * Constructor takes arguments and assigns them to instance of the Record class.
	 * 
	 * Tests that the terminal is a valid value.
	 * 
	 * @param terminal positive integer representing the terminal of the login/logout.
	 * 
	 * @param login is a boolean value, true if it was a login, false if the Record object represents a logout.
	 * 
	 * @param username the username used by the user logging in or out.
	 * 
	 * @param time the time the user logged in.
	 * 
	 * @throws IllegalArgumentException if the terminal number is invalid.
	 */
	public Record(int terminal, boolean login, String username, Date time) throws IllegalArgumentException {
		
		//assigns variables
		this.terminal = terminal;
		this.login = login;
		this.username = username;
		this.time = time;
		
		//tests if terminal argument is valid
		if (terminal < 1) {
			throw new IllegalArgumentException();
		}
    }
	
	
	/**
     * Getter method for terminal.
     * 
     * @return positive terminal number user logged in or out on.
     */
    public int getTerminal() {
        return terminal;
    }
    
    /**
     * Getter method for login.
     * 
     * @return boolean, true if the Record object is a login, false if it's a logout.
     */
    public boolean isLogin() {
        if (login == true) {
        	return true;
        }
        else {
        	return false;
        }
    }
    
    /**
     * Getter method for logout.
     * 
     * @return boolean, true if the Record object is a logout, false if it's a login.
     */
    public boolean isLogout() {
        if (login == false) {
        	return true;
        }
        else {
        	return false;
        }
    }
    
    /**
     * Getter method for username.
     * 
     * @return String username used to log in or out.
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Getter method for time.
     * 
     * @return Date object, time the user logged in or out.
     */
    public Date getTime() {
        return time;
    }


	/**
     * Compare Record objects such that Record A < Record B if A is before.
     * 
     * @param Record object to be compared.
     *  
     * @return int -1 if smaller, 0 if equal, 1 if greater. 
     * 
     */
    @Override
	public int compareTo(Record o) {
		if (o == null) {
			throw new NullPointerException();
		} else if (!(o instanceof Record)) {
			throw new ClassCastException();
		}
		Record record_o = (Record) o;
		if (this.time.getTime() > record_o.time.getTime()) {
			return 1;
		} else if (this.equals(record_o)) {
			return 0;
		}
		else {
			return -1;
		}
	}
    
    
    /**
     * Tests two Record objects for equality.
     * 
     * @param Record object used to compare to this for equality.
     * 
     * @return Boolean true if equal, false if unequal.
     */
	@Override
	public boolean equals(Object obj) {
		
		//test for conditions of direct equality, null, if obj ! object of type Record
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (!(obj instanceof Record)) {
			return false;
		}
		
		//type case obj
		Record record_o = (Record) obj;
		
		//give primitive types wrapper objects, so the equals() parameter can be used
		Long this_time = new Long(this.time.getTime());
		Long o_time = new Long(record_o.time.getTime());
		Integer this_terminal = new Integer(this.terminal);
		Integer o_terminal = new Integer(record_o.terminal);
		Boolean this_login = new Boolean(this.login);
		Boolean o_login = new Boolean(record_o.login);
		
		
		//test and return true if the different parameters equate
		if ((this_time.equals(o_time)) && (this.username.equals(record_o.username)) && (this_terminal.equals(o_terminal)) && (this_login.equals(o_login))) {
			return true;
		}
		return false;
		
	}
    
}