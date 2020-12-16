package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
        Node polySum = null;
        Node ptr1 = poly1;
        Node ptr2 = poly2;

        while(ptr1 != null && ptr2 != null) {
            if (ptr1.term.degree == ptr2.term.degree) {
                polySum = new Node((ptr1.term.coeff + ptr2.term.coeff), ptr1.term.degree, polySum);
                ptr1 = ptr1.next;
                ptr2 = ptr2.next;
            } else if (ptr1.term.degree > ptr2.term.degree) {
                polySum = new Node(ptr2.term.coeff, ptr2.term.degree, polySum);
                ptr2 = ptr2.next;
            } else if (ptr1.term.degree < ptr2.term.degree) {
                polySum = new Node(ptr1.term.coeff, ptr1.term.degree, polySum);
                ptr1 = ptr1.next;
            }
        }
        if(ptr1 != null){
            while (ptr1 != null) {
                polySum = new Node(ptr1.term.coeff, ptr1.term.degree, polySum);
                ptr1 = ptr1.next;
            }
        }else if(ptr2 != null){
            while (ptr2 != null) {
                polySum = new Node(ptr2.term.coeff, ptr2.term.degree, polySum);
                ptr2 = ptr2.next;
            }
        }


        //flip the list to the original format and remove zero coefficients;
        Node flipper = polySum;
        polySum = null;
        while(flipper != null){
            if (flipper.term.coeff != 0){
                polySum = new Node(flipper.term.coeff, flipper.term.degree, polySum);
            }
            flipper = flipper.next;
        }

        return polySum;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
        Node polyProd = null;
        Node onePassProducts = null;

        if (poly1 == null || poly2 == null) {
            return null;

        } else {
        	//nested loop to multiply each term in poly1 by each term in poly2
			//each full pass of poly2 for a given poly1 term is appended using add() to avoid duplicate degrees
            for (Node ptr1 = poly1; ptr1 != null; ptr1 = ptr1.next) {
                //reset the pass to null
                onePassProducts = null;

                for (Node ptr2 = poly2; ptr2 != null; ptr2 = ptr2.next) {
                    onePassProducts = new Node((ptr1.term.coeff * ptr2.term.coeff), (ptr1.term.degree + ptr2.term.degree), onePassProducts);
                    //at the end of the pass add to polyProd list
                    if(ptr2.next == null){

                        //flip the onePassProducts list and remove zero coefficients;
                        Node flipper = onePassProducts;
                        onePassProducts = null;
                        while (flipper != null) {
                            if (flipper.term.coeff != 0) {
                                onePassProducts = new Node(flipper.term.coeff, flipper.term.degree, onePassProducts);
                            }
                            flipper = flipper.next;
                        }
                        //add the full pass to the polyProd list
                        polyProd = add(polyProd, onePassProducts);
                    }
                }
            }
        }
        return polyProd;
    }
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		float polyeval = 0;
		for (Node ptr = poly; ptr != null; ptr = ptr.next) {
			polyeval += ((Math.pow(x, ptr.term.degree) * ptr.term.coeff));
		}
		return polyeval;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}