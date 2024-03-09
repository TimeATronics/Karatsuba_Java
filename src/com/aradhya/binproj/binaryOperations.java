/*
 * Author: Aradhya Chakrabarti
 * Roll No. 2205880
 */
package com.aradhya.binproj;

import java.util.ArrayList;

public abstract class binaryOperations {
	/*
	 * Task 2:
	 * Abstract Class to define operations on binary numbers (myBinaryNumber).
	 */
    public abstract char[] binaryMultiplication(myBinaryNumber x, myBinaryNumber y) throws Exception;

	public char[] binaryAddition(myBinaryNumber a, myBinaryNumber b) throws Exception {
		/*
		 * Wraps _add(char[][], boolean) to take two binary numbers as input.
		 */
		char[] x = a.myBinaryNumStr;
		char[] y = b.myBinaryNumStr;
		char[][] operands = myBinaryNumber.arrOfArrs(x, y);
		char[] result = _add(operands, false);
		return result;
	}

	// Helper methods:
    public static char[] _add(char[][] operands, boolean twosComp) {
		// Add 2/3 binary numbers provided as a collection in a 2D array.
		// Provision for keeping overflow bit if two's complement format specified
		ArrayList<Character> sum = new ArrayList<Character>();
		int i = 0;
		if (operands[i] == null) while (operands[i] == null) i++;
		for (int p = 0; p < operands[i].length; p++) sum.add('0');
		for (i = 0; i < operands.length; i++) {
			char[] currOpArr = operands[i];
			if (currOpArr == null) continue;
			// Get operands from char[][]
			ArrayList<Character> currOp = new ArrayList<Character>();
			for (int j = 0; j < currOpArr.length; j++) currOp.add(currOpArr[j]);
			// Add leading zeros to either operand to make them of the same size.
			if (currOp.size() < sum.size()) {
				int leadingZeros = sum.size() - currOp.size();
				for (int k = leadingZeros; k > 0; k--) currOp.add(0, '0');
			} else if (currOp.size() > sum.size()) {
				int leadingZeros = currOp.size() - sum.size();
				for (int k = leadingZeros; k > 0; k--) sum.add(0, '0');
			}
			char overflow = '0';
			// Binary Addition:
			/*
			 * 0 + 0 = 0 (Carry = 0)
			 * 0 + 1 = 1 (Carry = 0)
			 * 1 + 0 = 1 (Carry = 0)
			 * 1 + 1 = 0 (Carry = 1)
			 */
			for (int h = currOp.size() - 1; h >= 0; h--) {
				if (currOp.get(h) == '1' && sum.get(h) == '1') {
					if (overflow == '1') sum.set(h, '1');
                    else {
						sum.set(h, '0');
						overflow = '1';
					}
				} else if (currOp.get(h) == '0' && sum.get(h) == '0') {
					if(overflow == '1') {
						sum.set(h, '1');
						overflow = '0';
					} else sum.set(h, '0');
				} else {
					if (overflow == '1') {
						sum.set(h,'0');
						overflow = '1';
					} else sum.set(h,'1');
				}
			}
			// Keep or drop overflow bit according to value of flag
			if (overflow == '1' && !twosComp) {
				sum.add(0, '1');
			}
        }
		char[] sumArr = new char[sum.size()];
		sumArr = myBinaryNumber.charListToCharArr(sum);
		return sumArr;
    }

    public static char[] _subtract(char[] op1, char[] op2) {
		/*
		 * Add 2's complement of second operand with the first operand.
		 * This returns the difference between two binary numbers.
		 */
		ArrayList<Character> a = myBinaryNumber.charArrToCharList(op1);
		ArrayList<Character> b = myBinaryNumber.charArrToCharList(op2);
		// Add leading zeros to either operand to make both of the same size
		if (op1.length > op2.length) {
			int leadingZeros = op1.length - op2.length;
			for (int i = leadingZeros; i > 0; i--) b.add(0, '0');
		} else if (op1.length < op2.length) {
			int leadingZeros = op2.length - op1.length;
			for (int i = leadingZeros; i > 0; i--) a.add(0, '0');
		}
		// Convert second operand to its 2's complement format, i.e 1's complement + 1
		op1 = myBinaryNumber.charListToCharArr(a); op2 = myBinaryNumber.charListToCharArr(b);
		for (int j = 0; j < op2.length; j++) {
			if (op2[j] == '0') op2[j] = '1';
            else op2[j] = '0';
		}
		char[][] allOps = new char[2][];
		allOps[0] = op2;
		char[] oneNegSign = {'1'};
		allOps[1] = oneNegSign;
		op2 = _add(allOps, true);
		char[][] opArr = {op1, op2};
		return _add(opArr, true);
	}
}
