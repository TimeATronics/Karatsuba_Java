package com.aradhya.binproj;

import java.util.ArrayList;

public class binaryMultiplicationNaive extends binaryOperations {
	/*
	 * Task 3:
	 * Naive iterative implementation of binary multiplication (extending the binaryOperations class).
	 */
    public char[] binaryMultiplication(myBinaryNumber x, myBinaryNumber y) throws Exception {
		// Multiplication based on repeated addition, while iterating through all digits of the operands.
        char[] a = x.myBinaryNumStr; char[] b = y.myBinaryNumStr;
		char[][] allOps = new char[b.length][];
		for (int i = b.length - 1; i >= 0; i--) {
			ArrayList<Character> sumOperand = new ArrayList<Character>();
			char currDigit = b[i];
			if (currDigit == '0') continue;
		    else {
				for (int j = (a.length - 1); j >= 0; j--){
					if (a[j] == '1') sumOperand.add(0, '1');
					else sumOperand.add(0, '0');
				}
			}
			int leadingZeros = (b.length - 1) - i;
			for (int j = leadingZeros; j > 0; j--) sumOperand.add('0');
			char[] sumOpArr = new char[sumOperand.size()];
            sumOpArr = myBinaryNumber.charListToCharArr(sumOperand);
			allOps[i] = sumOpArr;
		}
		char[] result = _add(allOps, false);
		return result;
	}
}
