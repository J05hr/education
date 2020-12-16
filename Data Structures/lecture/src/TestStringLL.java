
public class TestStringLL {

	public static void main(String[] args) {
		
		StringLL memes = new StringLL();
		memes.addToFront("pepe the frog");
		memes.addToFront("tide pods");
		memes.traverse();
		
		StringLL names = new StringLL();
		names.addToFront("Kevin");
		names.addToFront("Ana");
		names.addToFront("John");
		names.traverse();
	}
}
