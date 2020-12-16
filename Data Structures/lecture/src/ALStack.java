
import java.util.ArrayList;
import java.util.NoSuchElementException;

/*
 * Stack using ArrayList
 */
public class ALStack<T> {

	private ArrayList<T> items;
	
	// creates an array of size 10
	public ALStack () {
		items = new ArrayList<T>();
	}
	
	public ALStack (int initialCapacity) {
		items = new ArrayList<T> (initialCapacity);
	}
	
	public void push (T item) {
		items.add(item);
	}
	
	public T pop () {
		if (items.size() == 0) {
			throw new NoSuchElementException("Stack is empty");
		}
		return items.remove(items.size()-1);
	}
	
	public T peek () {
		if (items.size() == 0) {
			throw new NoSuchElementException("Stack is empty");
		}
		return items.get(items.size()-1);
	}
	
	public boolean isEmpty () {
		return items.isEmpty();
	}
	
	public int size () {
		return items.size();
	}
	
	public void clear() {
		items.clear();
	}
}

