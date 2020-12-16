
/*
 * Generic Node
 */
public class Node <T> {

	// instance variables
	T data;       // generic data
	Node<T> next; // link to the next node
	
	Node (T data, Node<T> next) {
		this.data = data;
		this.next = next;
	}
}
