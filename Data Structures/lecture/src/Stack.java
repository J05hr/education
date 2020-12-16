
import java.util.NoSuchElementException;

/*
 * Stack using Linked List implementation
 */
public class Stack<T> {
	
	/* 
	 * front points to the top (front) item
	 */
	private Node<T> front;
	int size;
	
	public Stack () {
		front = null;
		size = 0;
	}
	/*
	 * Add item into the top of the stack (front of the Linked List)
	 * @param item to be pushed into the stack
	 */
	public void push (T item){	
		front = new Node<T> (item, front);
		size += 1;
	}
	
	/*
	 * Removes top item from the stack (front of the Linked List)
	 * @return stack's top item
	 */
	public T pop() {
		
		if (front == null) {
			throw new NoSuchElementException("Stack is empty");
		} else {
			T tmp = front.data; // save the top of the stack
			front = front.next; // remove the top
			size -= 1;
			return tmp;
		}	
	}
	
	/*
	 * Returns the top item from stack
	 * @return stack's top item
	 */
	public T peek() {

		if (front == null) {
			return null;
		} else {
			return front.data;
		}		
	}
	
	/*
	 * Checks if stack is empty
	 * @return true if stack is empty, false otherwise
	 */
	public boolean isEmpty(){
		if (front == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public int size () {
		return size;
	}
	
	public String toString () {
		String string = "Stack: ";
		for (Node<T> ptr = front; ptr != null; ptr = ptr.next) {
			string += ptr.data + ", ";
		}
		return string;
	}
}