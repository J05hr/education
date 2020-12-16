
public class DLL <T> {

	DLLNode<T> front;
	int size;
	
	DLL () {
		front = null;
		size = 0;
	}
	public void addToFront (T data) {
		DLLNode<T> node = new DLLNode<T>(data, front, null);
		if (front != null) {
			front.prev = node; // make current front node point back to node
		}
		front = node; // node is the new front
		size += 1;
	}
	public void removeFront () {
		if (front != null) {
			front = front.next;
			front.prev = null;
			size -= 1;
		}
	}
	
	// delete (target), addAfter(target, data)
	
	public void traverse () {
		for (DLLNode<T> ptr = front; ptr != null; ptr = ptr.next) {
			System.out.print(ptr.data + " ");
		}
		System.out.println();
	}
	public static void main(String[] args) {
		DLL<String> pies = new DLL<String>();
		pies.addToFront("Blueberry");
		pies.addToFront("Pecan");
		pies.addToFront("Apple");
		pies.addToFront("Pumpkin");
		pies.traverse();
		pies.removeFront();
		pies.traverse();
	}

}
