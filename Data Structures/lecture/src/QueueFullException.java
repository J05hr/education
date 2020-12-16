
public class QueueFullException extends Exception {

	public QueueFullException() {
		super(); // invoking Exception's class no-arg constructor
	}
	public QueueFullException(String msg) {
		super(msg); // invoking Exception's class one arg constructor
	}
}