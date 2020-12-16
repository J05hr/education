import java.util.NoSuchElementException;

/*
 * Generic Linked List
 */
public class LinkedList <T> {

	Node<T> front; // points to the first node of the LL
	int size;
	
	LinkedList () {
		front = null;
		size = 0;
	}
	public void addToFront (T data) {
		front = new Node<T>(data, front);
		size += 1;
	}
	public void traverse () {
		for (Node<T> ptr = front; ptr != null; ptr = ptr.next) {
			System.out.print(ptr.data + " -> ");
		}
		System.out.println("\\");
	}
	public T removeFront () {
		if (front == null) {
			// list is empty
			throw new NoSuchElementException("List is empty");
		}
		T frontData = front.data; // saves front.data
		front = front.next; // removes the first node
		size -= 1;
		return frontData;
	}
}
