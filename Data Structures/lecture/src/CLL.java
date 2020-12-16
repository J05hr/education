
public class CLL <T> {

	Node<T> tail;
	int size;
	
	CLL () {
		tail = null;
		size = 0;
	}
	public void addToFront (T data) {
		Node<T> node = new Node(data, null);
		if (tail == null) {
			// empty list
			node.next = node; // make node point to itself (only node in the LL)
			tail = node;
		} else {
			node.next = tail.next; // make node point to the front (tail.next)
			tail.next = node;  // last node must point to the new node (new front)
		}
		size += 1;
	}
	public void traverse () {
		
		if (tail == null) {
			System.out.println("Empty list");
			return;
		}
		Node<T> ptr = tail.next; // points to the front
		do {
			System.out.print(ptr.data + " ");
			ptr = ptr.next;
		} while (ptr != tail.next);
		System.out.println();
	}
	public void removeFront () {
		if (tail == null || tail == tail.next) {
			// empty list OR one node list
			tail = null;
			size = 0;
		} else {
			tail.next = tail.next.next; // makes the last node point to the second node
			size -= 1;
		}
	}
	public static void main(String[] args) {
		CLL<Integer> cll = new CLL<Integer>();
		cll.addToFront(3);
		cll.addToFront(2);
		cll.addToFront(1);
		cll.addToFront(0);
		cll.traverse();
		cll.removeFront();
		cll.traverse();
	}
}
