
public class StringLL {

	/* Instance variables or fields */
	StringNode front;  // reference to the first node of the LL
	int size; // number of items in the LL	
	StringLL () {
		front = null; // LL is empty
		size = 0;
	}	
	public void addToFront (String data) {
		front = new StringNode (data, front);
		size += 1;
	}
	public void traverse () {
		for (StringNode ptr = front; ptr != null; ptr = ptr.next) {
			System.out.print(ptr.data + " -> ");
		}
		System.out.println("\\");
	}
}
