package project3;

import java.util.*;

/**
 * This class creates a Session object.
 * 
 * It verifies the validity of the Record objects passed to constructor.
 * 
 * It calculates the duration of a Session, checking for active sessions.
 * 
 * It contains getters to access the different variables.
 * 
 * It overrides the toString() function, formatting the String output which will be printed
 * 		when a Session object is made in the RecordList class.
 * 
 * @author Anna Prenowitz
 */

public class Session implements Comparable<Session> {
	
	//declare variables
	private int terminal;
	private Date loginTime;
	private Date logoutTime;
	private String username;
	private String duration;
	private long duration_ms;
	
	/**
	 * Constructor for Session class.
	 * 
	 * Tests for valid parameters, calculates duration if not an active session.
	 * 
	 * Formats duration as a String, if active session or not.
	 * 
	 * @param login Record object containing data of one user's login.
	 * 
	 * @param logout Record object containing data of one user's logout, corresponding to their login.
	 */
    public Session (Record login, Record logout) {
    	
    	if (login == null) {
    		throw new IllegalArgumentException();
    	}
    	
    	//assigns variables to values from Record objects passed to constructor
    	terminal = login.getTerminal();
    	loginTime = login.getTime();
    	
    	
    	
    	if (logout == null) {
    		logoutTime = null;
    	} else {
    		logoutTime = logout.getTime();
    	}
    	username = login.getUsername();
    		
    	//converts duration from Date time to milliseconds, then calculates time between login and logout
    	if (logoutTime != null) {
    		if (logoutTime.before(loginTime)) {
        		throw new IllegalArgumentException("Logout time is before login time.");
        	}
    		duration_ms = logoutTime.getTime() - loginTime.getTime();
        	if (duration_ms < 0) {
        		duration_ms *= -1;
        	}
    	}
    	
    	//assigns String to duration if active session, calculates and formats duration if not
    	if (logout == null) {
    		duration = "active session";
    	} else {
    		long days = (duration_ms/(1000*60*60*24));
        	long hours = (duration_ms/(1000*60*60)) % 24;
        	long minutes = (duration_ms/(1000*60)) % 60;
        	long seconds = (duration_ms/1000) % 60;
        	duration = Long.toString(days) + " days, " + Long.toString(hours) 
        		+ " hours, " + Long.toString(minutes) + " minutes, " + Long.toString(seconds) + " seconds";
    	}
    	
    	
    }
    
    
    
    
    /**
     * Getter method for terminal.
     * 
     * @return positive terminal number.
     */
    public int getTerminal() {
        return terminal;
    }
    
    /**
     * Getter method for login time.
     * 
     * @return time user logged in for this Session.
     */
    public Date getLoginTime() {
        return loginTime;
    }
    
    /**
     * Getter method for logout time.
     * 
     * @return time user logged out for this Session.
     */
    public Date getLogoutTime() {
        return logoutTime;
    }
    
    /**
     * Getter method for username.
     * 
     * @return username of user for this Session.
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Getter method for duration of Session.
     * 
     * @return the time the user was logged in in milliseconds, or return -1 if it's still an active session.
     */
    public long getDuration() {
    	if (logoutTime != null) {
    		return duration_ms;
    	} else {
    		return -1;
    	}
    }
    
    /**
     * Overrides toString() method, formats the output String to be printed.
     * 
     * Tests for if the Session is active or not.
     * 
     * @return String output.
     */
    public String toString() {
    	String output_string;
    	
    	//tests for if active session or not
    	if (getLogoutTime() == null) {
    		output_string = String.format("%s, terminal %d, duration %s%n  logged in: %s%n  "
    				+ "logged out: still logged in", username, terminal, duration, loginTime.toString());
    	} else {
    		output_string = String.format("%s, terminal %d, duration %s%n  "
    				+ "logged in: %s%n  logged out: %s", username, terminal, duration, loginTime.toString(), logoutTime.toString());
    	}
    	
    	//returns formatted String
    	return output_string;
    }

	@Override
	/**
     * Compares Session objects by size.
     * 
     * If first Session is smaller returns -1, if first Session is larger returns 1,
     * if Sessions are equal returns 0
     * 
     * @param Session object used for comparison
     * 
     * @return int positive, 0, or negative to convey the comparison.
     */
	public int compareTo(Session o) {
		if (o == null) {
			throw new NullPointerException();
		} else if (!(o instanceof Session)) {
			throw new ClassCastException();
		}
		Session session_o = (Session) o;
		//compare Session objects by login time
		if (this.loginTime.getTime()>session_o.loginTime.getTime()) {
			return 1;
		}//call equals method to test for equality
		else if (this.equals(session_o)) {
			return 0;
		}
		else {
			return -1;
		}
		
		
	}
	
	/**
     * Tests two Session objects for equality.
     * 
     * @param Session object used to compare to this for equality.
     * 
     * @return Boolean true if equal, false if unequal.
     */
	@Override
	public boolean equals(Object obj) {
		//do initial checks for equality, null and instanceof Session
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Session)) {
			return false;
		}
		
		//type cast Session
		Session session_o = (Session) obj;
		
		//convert to objects, so the equals() parameter can be used
		Integer this_terminal = new Integer(this.terminal);
		Integer o_terminal = new Integer(session_o.terminal);
		
		//make sure logoutTime isn't null, then check all elements are correct
		if ((this.logoutTime == null) || (session_o.logoutTime == null)) {
			if ((this.loginTime.equals(session_o.loginTime)) && (this.logoutTime == (session_o.logoutTime)) && (this_terminal.equals(o_terminal)) && (this.username.equals(session_o.username))) {
				return true;
			} else {
				return false;
			}
		} else {
			if ((this.loginTime.equals(session_o.loginTime)) && (this.logoutTime.equals(session_o.logoutTime)) && (this_terminal.equals(o_terminal)) && (this.username.equals(session_o.username))) {
				return true;
			} else {
				return false;
			}
		}
		
	}
}