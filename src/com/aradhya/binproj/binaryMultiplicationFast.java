package com.aradhya.binproj;

import java.util.ArrayList;

public class binaryMultiplicationFast extends binaryOperations {
	/*
	 * Task 5 (Part 2):
	 * Binary multiplication using the Karatsuba algorithm.
	 */
	public char[] binaryMultiplication (myBinaryNumber x, myBinaryNumber y) throws Exception {
		/*
		 * Wraps helperKaratsuba(char[], char[]) to take two myBinaryNumber objects as input
		 */
		char[] a = x.myBinaryNumStr; char[] b = y.myBinaryNumStr;
		char[] result = helperKaratsuba(a, b);
		return result;
	}

	// Helper method:
    public static char[] helperKaratsuba(char[] a, char[] b) {
		/*
		 * Recursive implementation of the Karatsuba Algorithm for binary multiplication.
		 */
		ArrayList<Character> op1 = myBinaryNumber.charArrToCharList(a);
        ArrayList<Character> op2 = myBinaryNumber.charArrToCharList(b);
		// Add leading zeros to either operand to make them of the same size.
		if (op1.size() > op2.size()) {
			int leadingZeros = op1.size() - op2.size();
			for (int i = leadingZeros; i > 0; i--) op2.add(0,'0');
		} else if (op1.size() < op2.size()) {
			int leadingZeros = op2.size() - op1.size();
			for (int i = leadingZeros; i > 0; i--) op1.add(0,'0');
		}

		/*
		 * According to the Karatsuba algorithm,
		 * X * Y = (2^n - 2^(n/2))*A*C + 2^(n/2)*(A+B)*(C+D) + (1 - 2^(n/2))*B*D
		 * X = first operand
		 * Y = second operand
		 * A = first half of first operand
		 * B = second half of first operand
		 * C = first half of second operand
		 * D = second half of second operand
		 * Reference: https://www.cs.cmu.edu/~15451-f22/lectures/lec23-strassen.pdf
		 */
		/*
		 * Check the length of both operands and find the value of 'n'
		 */
		int powerOfTwo = 1;
		while(powerOfTwo <= op1.size()){
			if (powerOfTwo == op1.size()) break;
			powerOfTwo = powerOfTwo * 2;
		}
		if (powerOfTwo > op1.size()) {
			powerOfTwo = powerOfTwo - op1.size();
			while(powerOfTwo !=0){
				op1.add(0, '0');
				op2.add(0, '0');
				powerOfTwo--;
			}
		}
		int size = op1.size();
		/*
		 * Base Cases:
		 * If size of operand = 1, return 0 or 1.
		 */
		if (size == 1) {
			if (op1.get(0) == '0' || op2.get(0) == '0') {
				char[] zero = {'0'};
				return zero;
			} else {
				char[] one = {'1'};
				return one;
			}
		}
		// A
		ArrayList<Character> aleft = new ArrayList<Character>(op1.subList(0, size / 2));
		// B
		ArrayList<Character> aright = new ArrayList<Character>(op1.subList(size / 2, size));
		// C
		ArrayList<Character> bleft = new ArrayList<Character>(op2.subList(0, size / 2));
		// D
		ArrayList<Character> bright = new ArrayList<Character>(op2.subList(size / 2, size));
		
		// Recursive Call 1: To compute A*C
		char[] c2 = helperKaratsuba(myBinaryNumber.charListToCharArr(aleft), myBinaryNumber.charListToCharArr(bleft));
		// Recursive Call 2: To compute B*D
		char[] c0 = helperKaratsuba(myBinaryNumber.charListToCharArr(aright), myBinaryNumber.charListToCharArr(bright));
		// Recursive Call 3: To compute (A+B)*(C+D)
		char[] c1 = helperKaratsuba(_add(
            myBinaryNumber.arrOfArrs(myBinaryNumber.charListToCharArr(aleft), myBinaryNumber.charListToCharArr(aright)), false),
            _add(
                myBinaryNumber.arrOfArrs(myBinaryNumber.charListToCharArr(bleft), myBinaryNumber.charListToCharArr(bright)), false
            )
        );
		// Subtract (A*C + B*D) from (A+B)*(C+D)
		c1 = _subtract(c1, _add(myBinaryNumber.arrOfArrs(c2, c0), false));
		// Padding operation for c1.
		int zeros = size / 2;
		ArrayList<Character> c1List = myBinaryNumber.charArrToCharList(c1);
		for (int i = zeros; i > 0; i--) c1List.add('0');
		c1 = myBinaryNumber.charListToCharArr(c1List);
        // Padding operation for c2.
		zeros = size;
		ArrayList<Character> c2List = myBinaryNumber.charArrToCharList(c2);
		for (int i = zeros; i > 0; i--) c2List.add('0');
		c2 = myBinaryNumber.charListToCharArr(c2List);
		// Addition after padding gives the resultant product.
		return _add(myBinaryNumber.arrOfArrs(c2, c1, c0), false);
	}
}
