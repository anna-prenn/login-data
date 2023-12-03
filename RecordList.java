package project3;


import java.util.ArrayList;
import java.io.*;
import java.util.*;

/**
 * This class creates a RecordList object.
 * 
 * The RecordList object is an ArrayList of Record objects.
 * 
 * The class also defined functions which select the Record objects that constitute the Session
 * 		which was called, and returns the first or last session of a user.
 * 
 * @author Anna Prenowitz
 */

public class RecordList extends SortedLinkedList<Record> {//extends ArrayList<Record> {
	
	//instantiate variables
	//private RecordList record_list;
	//private Record entry_record;

	//no-arg constructor
    public RecordList(){
    }
    
    
    /**
     * Tests for valid username and RecordList object.
     * 
     * Iterates through RecordList to find first login Record which has matching details to the user input.
     * 
     * Iterates through RecordList to find matching logout if it exists.
     * 
     * Throws exception if the username isn't valid.
     * 
     * Makes a new Session class with Record objects found, and returns Session object.
     * 
     * @param user the username entered by the user.
     *  
     * @return Session object, representing one single session on a terminal, with a login and logout time.
     * 
     * @throws IllegalArgumentException if username entered is empty.
     * 
     * @throws NoSuchElementException if RecordList object is empty or if the username entered does not
     * 		match one on the input file.
     */
    
    //replace everywhere we put record_list with this keyword...
    public Session getFirstSession(String user) throws IllegalArgumentException, NoSuchElementException {
    	
    	//throw exception if username is empty
    	if ((user == null) || (user.equals(""))) {// || (user.isEmpty())) {
			throw new IllegalArgumentException();
		}
    	
    	//make Record objects
        Record log_in = null;
    	Record log_out = null;
    	
    	//throw exception of RecordList object is empty
    	if ((this == null) || (this.size() == 0)) { //|| (this.isEmpty())) {
    		throw new NoSuchElementException();
    	}
    	
    	//iterate through Record objects in RecordList
        for (int i=0; i <this.size(); i++) {
        	
        	//if correct username and is a log in, assign Record object to variable
        	if (((this.get(i).getUsername()).equals(user)) && (this.get(i).isLogin())) {
        		log_in = this.get(i);
        		
        		//iterate through Record objects again to find the matching logout
        		for (int j=0; j < this.size(); j++) {
        			if ((this.get(j).getTerminal() == log_in.getTerminal()) && (this.get(j).getUsername().equals(user)) && (this.get(j).isLogout())) {
        				log_out = this.get(j);
        				break;
        			}
        		}
        		break;
        	}
        }
        //throw exception if username doesn't match one on input file
        if (log_in == null) {
        		throw new NoSuchElementException();
    	}
        
        //make Session object with Record objects found, and return it
        Session first_session = new Session(log_in, log_out);
        return(first_session);
        
    }
    
    
    /**
     * Tests for valid username and RecordList object.
     * 
     * Iterates through RecordList to find last login Record which has matching details to the user input.
     * 
     * Iterates through RecordList to find matching logout if it exists.
     * 
     * Throws exception if the username isn't valid.
     * 
     * Makes a new Session class with Record objects found, and returns Session object.
     * 
     * @param user the username entered by the user.
     *  
     * @return Session object, representing one single session on a terminal, with a login and logout time.
     * 
     * @throws IllegalArgumentException if username entered is empty.
     * 
     * @throws NoSuchElementException if RecordList object is empty or if the username entered does not
     * 		match one on the input file.
     */
    public Session getLastSession(String user) {
    	
    	//throw exception if username is empty
    	if ((user == null) || (user.equals(""))) {// || (user.isEmpty())) {
			throw new IllegalArgumentException();
		}
    	
    	//make Record objects
    	Record log_in = null;
    	Record log_out = null;
    	
    	//throw exception of RecordList object is empty
    	if ((this == null) || (this.size() == 0)) {// || (this.isEmpty())) {
    		throw new NoSuchElementException();
    	}
    	
    	//iterate through Record objects in RecordList
    	for (int i=(this.size()-1); i >= 0; i--) {
    	//for (int i=0; i <this.size(); i++) {
        	
        	//if correct username and is a log in, assign Record object to variable
        	if (((this.get(i).getUsername()).equals(user)) && (this.get(i).isLogin())) {
        		log_in = this.get(i);
        		
        		//iterate through Record objects again to find the matching logout
        		for (int j=0; j < this.size(); j++) {
        			if ((this.get(j).getTerminal() == log_in.getTerminal()) && (this.get(j).getUsername().equals(user)) && (this.get(j).isLogout())) {
        				log_out = this.get(j);
        				break;
        			}
        		}
        		break;
        	}
        }
        
        //throw exception if username doesn't match one on input file
        if (log_in == null) {
    		throw new NoSuchElementException();
        }
        
        
        //make Session object with Record objects found, and return it
        Session last_session = new Session(log_in, log_out);
        return(last_session);
        
    }
    
    
    /**
     * Get SortedLinkedList of all Sessions of the given user.
     * 
     * Adds the duration of the different Sessions (as long as they have ended) to each other.
     * 
     * @param User the username entered by the user.
     *  
     * @return Long the total time the user was online.
     * 
     */
    public long getTotalTime(String user) {
    	//return total time in ms user was logged in
    	long total_time = 0L;
    	long new_duration;
    	SortedLinkedList<Session> all_sessions = getAllSessions(user);
    	
    	for (int i=0; i<all_sessions.size(); i++) {
    		new_duration = all_sessions.get(i).getDuration();
    		if (new_duration != -1) {
    			total_time += new_duration;
    		}
    	}
    	return total_time;
    	
    }
    
    /**
     * Returns SortedLinkedList of all Sessions.
     * 
     * Makes Session objects for all the logins of the given user.
     * 
     * @param User the username entered by the user.
     *  
     * @return SortedLinkedList<Session> list of all Session objects of given user.
     * 
     */
    public SortedLinkedList<Session> getAllSessions(String user) {
    	//make SortedLinkedList<Session> object
    	SortedLinkedList<Session> this_sll = new SortedLinkedList<>();
    	//test for null, empty string
    	if ((user == null) || (user.equals(""))) {
    		throw new IllegalArgumentException("No user matching %s found\\n\", user");
    	}
    	
    	//make Record objects
    	Record log_in = null;
    	Record log_out = null;
    	
    	//throw exception of RecordList object is empty
    	if ((this == null) || (this.size() == 0)) {// || (this.isEmpty())) {
    		throw new NoSuchElementException("Empty list of Records");
    	}
    	
    	//iterate through Record objects in RecordList
    	for (int i=0; i <this.size(); i++) {
        	
    		
        	if (((this.get(i).getUsername()).equals(user)) && (this.get(i).isLogin())) {
        		
        		log_in = this.get(i);
        		
        		//assigning null to log_out for every iteration
        		log_out = null;
        		
        		
        		//iterate through Record objects again to find the matching logout
        		for (int j=i; j < this.size(); j++) {
        			if ((this.get(j).getTerminal() == log_in.getTerminal()) && (this.get(j).getUsername().equals(user)) && (this.get(j).isLogout())) {
        				log_out = this.get(j);
        				break;
        			}
        		}
        		//make new session and add to list every time there's a new login
        		Session new_session = new Session(log_in, log_out);
        		this_sll.add(new_session);
        	}
        }
        
        //throw exception if username doesn't match one on input file
        if (log_in == null) {
    		throw new NoSuchElementException();
        }
        return this_sll;
    	
    }

}