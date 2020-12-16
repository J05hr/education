
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
	public static boolean addAfter (IntNode front, int target, int data) {
		for (IntNode ptr = front; ptr != null; ptr = ptr.next) {
			if (ptr.data == target) {
				// found target, insert new node after it
				IntNode node = new IntNode (data, ptr.next);
				ptr.next = node;
				return true;
			}
		}
		return false; // target was not found
	}
	public static boolean addAfterLastOccurrence(IntNode front, int target, int data) {
		
		IntNode last = null; // reference to the last occurrence of target	
		for (IntNode ptr = front; ptr != null; ptr = ptr.next) {
			if (ptr.data == target) {
				last = ptr;
			}
		}	
		if (last == null) {
			// target not found
			return false;
		} else {
			IntNode node = new IntNode (data, last.next);
			last.next = node;
			return true;
		}
	}
	public static IntNode delete (IntNode front, int target) {
		/* 1. Traverse the LL until target is found: prev is one node behind ptr */
		IntNode ptr = front;
		IntNode prev = null;
		while (ptr != null && ptr.data != target) {
			prev = ptr;
			ptr = ptr.next;
		}
		/* 2. Delete */
		if (ptr == null) {
			// list is empty OR target does not exist
			return front; // front is null
		} else if (ptr == front) {
			// target is the first node of the LL
			return front.next; // deletes the first node
		} else {
			// target is in the middle or the last item
			prev.next = ptr.next;
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
		 addAfter(L, 3, 4);
		 traverse(L);
		 addAfter(L, 5, 5);
		 traverse(L);
		 addAfterLastOccurrence(L, 5, 7);
		 traverse(L);
		 L = delete(L, 3);
		 traverse(L);
	}
}
