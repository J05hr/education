
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
	// static vs non static methods
}
