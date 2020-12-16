
public class TestLinkedList {

	public static void main(String[] args) {
		
		LinkedList<String> states = new LinkedList<String>();
		states.addToFront("NJ");
		states.addToFront("NY");
		states.addToFront("TN");
		states.traverse();
		
		LinkedList<Integer> numbers = new LinkedList<Integer>();
		numbers.addToFront(9);
		numbers.addToFront(5);
		numbers.addToFront(4);
		numbers.addToFront(2);
		numbers.traverse();
		
		LinkedList<Float> fNumbers = new LinkedList<Float>();
		fNumbers.addToFront(0.7f);
		Float value = fNumbers.removeFront();
		System.out.println("value = " + value);
		fNumbers.removeFront();
	}
}
