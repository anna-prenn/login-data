package project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * This class contains the main method.
 * 
 * It takes and reads the input file, then prints the introductory statements.
 * 
 * It then makes an instance of the RecordList class, and fills a RecordList with Record objects.
 * 
 * It then enters interactive mode, and takes user input. It calls functions getFirstSession and getLastSession,
 * 		and makes Session classes which return a String to be printed.
 * 
 * Catches and handles errors.
 * 
 * @author Anna Prenowitz
 */

public class LoginStats {
	
	/**
	 * Takes and reads input file.
	 * 
	 * Prints welcome statements.
	 * 
	 * Creates instance of RecordList and fills it with Record objects.
	 * 
	 * Prompts user input.
	 * 
	 * Calls functions getFirstSession() or getLastSession, according to user input.
	 * 
	 * Catches and handles errors thrown in these functions.
	 * 
	 * @param args commandline argument specifying the name of the input file
	 */
	
	private static Record entry_record;
	
    public static void main(String[] args) {
    	
    	//test if input file was provided
    	if (args.length == 0 ) {
			System.err.println("Usage Error: the program expects file name as an argument.");
			System.exit(1);
		}
    	
    	//make File object
    	File input_obj = new File(args[0]);
    	
    	//check if File object exists
    	if (!input_obj.exists()) {
    		System.err.println(String.format("Error: the file %s does not exist.", input_obj.getAbsolutePath()));
    		System.exit(1);
    	}
    	
    	//check if contents of File is readable
    	if (!input_obj.canRead()){
			System.err.println(String.format("Error: the file %s cannot be opened for reading", input_obj.getAbsolutePath()));
			System.exit(1);
		}
    	
    	//create new Scanner object
    	Scanner input_reader = null;
    	
    	//read file, throw and handle error, ending program if file isn't readable
    	try {
    		input_reader = new Scanner(input_obj);
    	} catch(FileNotFoundException ex) {
    		System.err.println(String.format("Error: the file %s cannot be opened for reading.", input_obj.getAbsolutePath()));
    		System.exit(1);
    	}
    	
    	//create ArrayList object
    	ArrayList<String> arraylist_input = new ArrayList<String>();
    	
    	//fill ArrayList with lines of file, catch error if incorrect line
    	while (input_reader.hasNextLine()) {
    		try {
    			arraylist_input.add(input_reader.nextLine());
    		} catch (NoSuchElementException | IllegalArgumentException ex) {
    			System.err.println("Error: incorrect line");
    			continue;
    		}
    	}
    	
    	//print welcome statements
        System.out.println("Welcome to login stats!");
        System.out.println();
        System.out.println("Available commands:");
        System.out.println("  first USERNAME   - retrieves first login session for the USER");
        System.out.println("  last USERNAME    - retrieves last login session for the USER");
        System.out.println("  all USERNAME     - retrieves all login sessions for the USER");
        System.out.println("  total USERNAME   - retrieves total login time for the USER");
        System.out.println("  quit             - terminates this program");
        System.out.println();
        
        
        //make new RecordList object
        RecordList myList = new RecordList();
        
        //iterate through input
        for (int i=0; i < arraylist_input.size(); i++) {
        	
        	//assigns information from each line of ArrayList to variables
        	boolean login;
            int terminal = Math.abs(Integer.parseInt(arraylist_input.get(i).split(" ")[0]));
            if ((Integer.parseInt(arraylist_input.get(i).split(" ")[0])) > 0) {
                login = true;
            } else {
                login = false;
            }
            String username = arraylist_input.get(i).split(" ")[2];
            Date time = new Date(Long.parseLong(arraylist_input.get(i).split(" ")[1]));
            
            
            //makes Record object with these variables, then adds Record object to RecordList
            entry_record = new Record(terminal, login, username, time);
            
            //add Record objects to RecordList (extends SortedLinkedList)
            myList.add(entry_record);
        }
        
        
        
        
        
        
        
        //instantiate input variables
        String input = "";
        Scanner user_input = new Scanner(System.in);
        
        //while loop runs while user doesn't enter "quit"
        while (("quit".equals((input).trim())) == false) {
        	
        	//take user input
            input = user_input.nextLine();
            
            //run getFirstSession and getLastSession according to user input
        	if ("first".equals(input.split(" ")[0])) {
        		try {
        			System.out.print(myList.getFirstSession(input.split(" ")[1]));
        			System.out.println();
        		} catch(NoSuchElementException | IllegalArgumentException ex) {
        			System.err.println(String.format("No user matching %s found", input.split(" ")[1]));
        		}
        	} else if ("last".equals(input.split(" ")[0])) {
        		try {
        			System.out.print(myList.getLastSession(input.split(" ")[1]));
        			System.out.println();
        		} catch(NoSuchElementException | IllegalArgumentException ex) {
        			System.err.print(String.format("No user matching %s found\n", input.split(" ")[1]));
        		}
        	}
        	
        	//get all Sessions of given user
        	else if ("all".equals(input.split(" ")[0])) {        		
        		try {
        			
        			SortedLinkedList<Session> sll_of_sessions_getall = myList.getAllSessions(input.split(" ")[1]);
        			
        			//print each one separately
        			for (int i=0; i<sll_of_sessions_getall.size(); i++) {
        				System.out.println(sll_of_sessions_getall.get(i));
        				if (i != (sll_of_sessions_getall.size() - 1))
        					System.out.println();
        			}
        		} catch(NoSuchElementException | IllegalArgumentException ex) {
        			//System.err.print(String.format("No user matching %s found\n", input.split(" ")[1]));
        		}
        	}
        	
        	//get total time user has been logged in
        	else if ("total".equals(input.split(" ")[0])) {        		
        		try {
        			//get duration in milliseconds
        			long duration_ms = myList.getTotalTime(input.split(" ")[1]);
        			
        			//calculate duration in terms of days, hours, minutes
        			long days = (duration_ms/(1000*60*60*24));// % 365;
                	long hours = (duration_ms/(1000*60*60)) % 24;
                	long minutes = (duration_ms/(1000*60)) % 60;
                	long seconds = (duration_ms/1000) % 60;
                	
                	//format as string
                	String duration = Long.toString(days) + "d " + Long.toString(hours) 
                		+ "h " + Long.toString(minutes) + "m " + Long.toString(seconds) + "s";
        			
                	//format and print duration
        			System.out.print(input.split(" ")[1]+ ", total duration " + duration);
        			System.out.println();
        			
        			//catch exceptions
        		} catch(NoSuchElementException ex) {
        			System.err.print(String.format("No user matching %s found\n", input.split(" ")[1]));
        		}
        	}
        	
        	System.out.println();
        	
        }
    }
}