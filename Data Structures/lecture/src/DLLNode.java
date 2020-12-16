
public class DLLNode <T> {

	T data;
	DLLNode<T> next; // points to the next node in the LL
	DLLNode<T> prev; // points to the previous node in the LL
	
	DLLNode (T data, DLLNode<T> next, DLLNode<T> prev) {
		this.data = data;
		this.next = next;
		this.prev = prev;
	}
}
