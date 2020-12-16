import java.util.NoSuchElementException;

public class parenMatch {

	/*
	 * Returns true if expression has matching parenthesis, false otherwise
	 */
	public static boolean parenMatch (String expression) {
		
		Stack<Character> stack = new Stack<Character>();
		
		for ( int i = 0; i < expression.length(); i++ ) {
			char chExpr = expression.charAt(i);
			
			if (chExpr == '(' || chExpr == '[') {
				stack.push(chExpr);
			
			} else if (chExpr == ')' || chExpr == ']') {
				
				char chStack = ' ';
				try {
					chStack = stack.pop();
				} catch (NoSuchElementException e) {
					System.out.println("Caught the exception");
					e.printStackTrace();
					return false;
				}
				
				if (chExpr == ')' && chStack == '[') {
					return false;
				} else if (chExpr == ']' && chStack == '(') {
					return false;
				}
			}
		}
		return stack.isEmpty();
	}
	
	public static void main(String[] args) {
	
		String expr1 = "[(A + B) * C] - B";
		String expr2 = "[(A + B) * C - B";
		String expr = "(A + B) * C) - B";
		
		if (parenMatch(expr)) {
			System.out.println(expr + ": is matched");
		} else {
			System.out.println(expr + ": not matched");
		}
	}
}
