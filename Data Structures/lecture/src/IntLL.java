
public class IntLL {

	public static IntNode addToFront (IntNode front, int data) {
		IntNode node = new IntNode(data, front);
		return node;
	}
	public static void traverse (IntNode front) {
		IntNode ptr = front;
		while (ptr != null) {
			System.out.print(ptr.data + " -> ");
			ptr = ptr.next; // ptr moves to the next node in the LL
		}
		System.out.println("\\");
	}
	public static IntNode removeFront (IntNode front) {
		if (front == null) {
			// list is empty
			return null;
		} else {
			return front.next;
		}
	}
	public static boolean search (IntNode front, int target) {
		for (IntNode ptr = front; ptr != null; ptr = ptr.next) {
			if (ptr.data == target) {
				// found it
				return true;
			}
		}
		return false;
	}
	public static IntNode addToBack (IntNode front, int data) {
		if (front == null) {
			// LL is empty
			return addToFront(front, data);
		} else {
			IntNode ptr = front;
			while (ptr.next != null) {
				ptr = ptr.next;
			}
			// ptr points to the last item in the LL
			IntNode node = new IntNode(data, null);
			ptr.next = node;
			return front;
		}
	}
	public static void main(String[] args) {
		 IntNode L = null; // L points to the first node of the LL (right now LL is empty)
		 L = addToFront (L, 5);
		 traverse(L); // prints LL
		 L = addToFront(L, 3);
		 traverse(L);
		 L = addToFront(L, 2);
		 L = addToFront(L, 1);
		 traverse(L);
		 L = removeFront(L);
		 traverse(L);
		 System.out.println(search(L, 5));
		 System.out.println(search(L, 9));
		 L = addToBack(L, 12);
		 traverse(L);
	}
}
