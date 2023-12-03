package project3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an implementation of a sorted doubly-linked list.
 * All elements in the list are maintained in ascending/increasing order
 * based on the natural order of the elements.
 * This list does not allow <code>null</code> elements.
 *
 * @author Joanna Klukowska
 * @author Anna Prenowitz
 *
 * @param <E> the type of elements held in this list
 */
public class SortedLinkedList<E extends Comparable<E>>
    implements Iterable<E> {

    private Node head;
    private Node tail;
    private int size;

    /**
     * Constructs a new empty sorted linked list.
     */
    public SortedLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adds the specified element to the list in ascending order.
     *
     * @param element the element to add
     * @return <code>true</code> if the element was added successfully,
     * <code>false</code> otherwise (if <code>element==null</code>)
     */
    public boolean add(E element) {
    	
    	if (element==null) {
    		return false;
    	} else {
    		Node new_node = new Node(element);
    		Node current = head;
    		
    		//empty list
    		if (head == null) {
    			head = new_node;
    			tail=new_node;
    			head.prev = null;
    			head.next = null;
    			size += 1;
    			return true;
    		}
    		//one element in list
    		if (head == tail) {
    			
    			//adding the second one if new node is smaller
    			if (current.data.compareTo(new_node.data)>0) {
    				head.prev = new_node;
    				head = new_node;
    				head.prev = null;
    				head.next = tail;
    				size += 1;
    				return true;
    			}
    			//if new element is larger or same size than the 1 element
    			else {
    				head.next = new_node;
        			tail = new_node;
        			tail.prev = head;
        			tail.next = null;
        			size += 1;
        			return true;
    			}
    		}
    			
    		
    		// if new smaller than first, put new at front
    		if (current.data.compareTo(new_node.data)>0) {
    			head.prev = new_node;
    			new_node.next = head;
				head = new_node;
				head.prev = null;
				size += 1;
				return true;
    		}
    		
    		//add if larger than or equal to last element
    		if (tail.data.compareTo(new_node.data)<=0) {
    			tail.next = new_node;
    			new_node.prev = tail;
    			new_node.next = null;
    			tail = new_node;
    			size += 1;
				return true;
    		}
    		
    		
    		//iterate through list
    		while (current != tail) {
    			if (current.next.data.compareTo(new_node.data)<0) {
    				current = current.next;
    			}
    			//add new node if smaller than the next element
    			else {
    				Node tmp = current.next;
    				current.next = new_node;
    				new_node.next = tmp;
    				tmp.prev = new_node;
    				new_node.prev = current;
    				size += 1;
    				return true;
    			}
    		}
    		
    		
    		
    	}
    	
        return false;
    }

    /**
     * Removes all elements from the list.
     */
    public void clear() {
    	//set head and tail to null
    	head = null;
    	tail = null;
    	size = 0;
    }

    /**
     * Returns <code>true</code> if the list contains the specified element,
     * <code>false</code> otherwise.
     *
     * @param o the element to search for
     * @return <code>true</code> if the element is in the list,
     * <code>false</code> otherwise
     */
    public boolean contains(Object o) {
    	//test for null
    	if (o==null) {
    		return false;
    	}
    	else {
    		//iterate through list, return true if o if found
    		Node current = head;
    		while (current != null) {
    			if (current.data == o) {
    				return true;
    			}
    			current = current.next;
    		}
    		return false;
    	}
    }

    /**
     * Returns the element at the specified index in the list.
     *
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throw IndexOutOfBoundsException  if the index is out of
     * range <code>(index < 0 || index >= size())</code>
     */
    public E get(int index) throws IndexOutOfBoundsException {
    	
    	if ((index < 0) || (index >= size)) {
    		throw new IndexOutOfBoundsException();
    	}
    	//iterate through list
    	Node current = head;
    	for (int i = 0; i<index; i++) {
    		current = current.next;
    	}
    	return current.data;
    	
    }

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * or -1 if the element is not in the list.
     *
     * @param o the element to search for
     * @return the index of the first occurrence of the element,
     * or -1 if the element is not in the list
     */
    public int indexOf(Object o) {
    	int counter = 0;
    	Node current = head;
    	while (current != null) {
    		if (current.data == o) {
    			return counter;
    		}
    		counter ++;
    		current = current.next;
    	}
        return -1;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * starting at the specified <code>index</code>, i.e., in the range of indexes
     * <code>index <= i < size()</code>, or -1 if the element is not in the list
     * in the range of indexes <code>index <= i < size()</code>.
     *
     * @param o the element to search for
     * @param index the index to start searching from
     * @return the index of the first occurrence of the element, starting at the specified index,
     * or -1 if the element is not found
     */
    public int nextIndexOf(Object o, int index) {
    	if (index >= size) {
    		return -1;
    	}
    	//iterate through list to move current to index
    	Node current = head;
    	int new_index = index;
    	for (int i = 0; i<index; i++) {
    		current = current.next;
    	}
    	//iterate through the rest of the list to find the next instance of Object o
    	while (current!=null) {
    		if (current.data == o) {
    			return new_index;
    		}
    		current = current.next;
    		new_index += 1;
    	}
    	//if o isn't in the list, return -1
        return -1;
    }

    /**
     * Removes the first occurrence of the specified element from the list.
     *
     * @param o the element to remove
     * @return <code>true</code> if the element was removed successfully,
     * <code>false</code> otherwise
     */
    public boolean remove(Object o) {
    	//condition for empty list
    	if (size == 0) {
    		return false;
    	}
    	
    	//special case if size == 1
    	if (size == 1) {
    		if (o == head.data) {
    			head = null;
    			tail = null;
    			size = 0;
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    	
    	//remove node and return true
    	Node current = head;
    	while (current != null) {
    		if (current.data == o) {
    			Node tmp = current.prev;
    			if (tmp != null) {
    				tmp.next = current.next;
    			} else {
    				head = current.next;
    			}
    			if (current.next == null) {
    				tail = tmp;
    			} else {
    				current.next.prev = tmp;
    			}
    			size -= 1;
    			return true;
    		}
    		current = current.next;
    	}
    	//if failed to remove node, return false
        return false;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns an iterator over the elements in the list.
     *
     * @return an iterator over the elements in the list
     */
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    /**
     * Compares the specified object with this list for equality.
     *
     * @param o the object to compare with.
     * @return <code>true</code> if the specified object is equal to this list,
     * <code>false</code> otherwise
     */
    public boolean equals(Object o) {
    	
        if (o == this)
            return true;
        //check if o is an instance of SortedLinkedList
        if (!(o instanceof SortedLinkedList))
            return false;
        //check if lists are of the same length
        if (this.size() != ((SortedLinkedList<?>) o).size() ) {//returning false
            return false;
        }
        
        //iterate over both lists, comparing corresponding pairs of elements
        for (int i = 0; i < this.size(); i++ ) {
            E o1 = this.get(i);
            Object o2 = ((SortedLinkedList<?>) o).get(i);
            
            if (!(o1==null ? o2==null : o1.equals(o2)))
                return false;
        }
        return true;
    }
    

    /**
     * Returns a string representation of the list.
     *  The string representation consists of a list of the lists's elements in
     *  ascending order, enclosed in square brackets ("[]").
     *  Adjacent elements are separated by the characters ", " (comma and space).
     *
     * @return a string representation of the list
     */
    public String toString() {
    	if (head==null) {
    		return "[]";
    	}
    	
    	//construct String enclosed by square brackets and with commas separating elements
    	String return_string = "";
    	Node current = head;
    	return_string +="[" + current.data;
    	current = current.next;
    	while (current != null) {
    		return_string += ", " + current.data;
    		current = current.next;
    	}
    	return_string +="]";
        return return_string;
    }

    /* Inner class to represent nodes of this list.*/
    private class Node implements Comparable<Node> {
        E data;
        Node next;
        Node prev;
        Node(E data) {
            if (data == null ) throw new NullPointerException ("does not allow null");
            this.data = data;
        }
        Node (E data, Node next, Node prev) {
            this(data);
            this.next = next;
            this.prev = prev;
        }
        public int compareTo( Node n ) {
            return this.data.compareTo(n.data);
        }
    }

    /* A basic forward iterator for this list. */
    private class ListIterator implements Iterator<E> {

        Node nextToReturn = head;
        @Override
        public boolean hasNext() {
            return nextToReturn != null;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (nextToReturn == null )
                throw new NoSuchElementException("the end of the list reached");
            E tmp = nextToReturn.data;
            nextToReturn = nextToReturn.next;
            return tmp;
        }

    }
}