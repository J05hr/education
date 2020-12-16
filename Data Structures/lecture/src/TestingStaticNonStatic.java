
public class TestingStaticNonStatic {

	public static void main(String[] args) {
		
		A a1 = new A();
		a1.x = 1;
		
		A a2 = new A();
		a2.x = 4;
		
		System.out.println("a1.x = " + a1.x);
		System.out.println("a2.x = " + a2.x);
		
		B b1 = new B();
		b1.x = 1;  // right way to access is B.x
		
		B b2 = new B();
		b2.x = 4;
		
		System.out.println("b1.x = " + b1.x);
		System.out.println("b2.x = " + b2.x);
	}
}
