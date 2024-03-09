/*
 * Author: Aradhya Chakrabarti
 * Roll No. 2205880
 */
package com.aradhya.binproj;

import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigInteger;

public class myBinaryNumber {
    /*
     * Task 1:
     * This class represents arbitrary-length binary numbers using character arrays.
     */
    public char[] myBinaryNumStr;
    private int myBinaryNumLen;

    myBinaryNumber(int n) {
        /*
         * Constructor to initialize an n-bit binary number with all bits set to 0.
         */
        this.myBinaryNumLen = n;
        char[] stringArr = new char[myBinaryNumLen];
        Arrays.fill(stringArr, '0');
        this.myBinaryNumStr = stringArr;
    }

    myBinaryNumber(String s) throws Exception {
        /*
         * Constructor to initialize a binary number represented by String s.
         */
        if (s.length() == 0 || s.equals("0")) {
            char[] zero = {'0'};
            this.myBinaryNumStr = zero;
        } else {
            if (!s.matches("[01]+")) { // Regular Expression
                throw new Exception(s + " contains invalid characters.");
            }
            /*
             * Remove leading zeros, if any in input String.
             */
            int i = 0;
            while (i < s.length() && s.charAt(i) == '0') i++;
            StringBuffer removedZeros = new StringBuffer(s);
            removedZeros.replace(0, i, "");
            this.myBinaryNumStr = removedZeros.toString().toCharArray();
            this.myBinaryNumLen = removedZeros.toString().toCharArray().length;
        }
    }

    public int getSize() {
        /*
         * Returns the size of the binary number.
         */
        return this.myBinaryNumStr.length;
    }
    
    public int getBit(int p) throws Exception {
        /*
         * Returns the bit at position 'p' from the LSB side.
         */
    	if (p > (this.myBinaryNumStr.length - 1)) {
    		throw new Exception(p + " is out of bounds.");
    	}
        return Integer.parseInt(String.valueOf(this.myBinaryNumStr[this.myBinaryNumStr.length - p - 1]));
    }

    public void setBit(int p, int b) throws Exception {
        /*
         * Sets the bit at position 'p' from LSB.
         */
    	if (b != 0 && b != 1) {
    		throw new Exception(b + " is not 0 or 1.");
    	}
    	if (p > (myBinaryNumLen - 1)) { 
    		throw new Exception(p + " is out of bounds.");
        }
        /*
         * Remove leading zeros, if any, then set bit
         */
        int i = 0;
        while (i < this.myBinaryNumStr.length && this.myBinaryNumStr[i] == '0') i++;
        if (i != 0) {
            StringBuffer removedZeros = new StringBuffer(this.myBinaryNumStr.toString());
            removedZeros.replace(0, i, "");
            this.myBinaryNumStr = removedZeros.toString().toCharArray();
            this.myBinaryNumLen = removedZeros.toString().toCharArray().length;
        }
        this.myBinaryNumStr[this.myBinaryNumStr.length - p - 1] = Character.forDigit(b, 10);
    }

    public void printNumber() {
        /*
         * Prints the binary number.
         */
		char[] num = remLeadingZeros(this.myBinaryNumStr);
		for (int i = 0; i < num.length; i++) System.out.printf("%c", num[i]);
		System.out.println("");
	}

    public void printDecimalNumber() throws Exception {
        /*
         * Prints the decimal equivalent of the binary number.
         * (Uses the BigInteger class to represent decimal values of arbitrary length)
         */
        BigInteger result = new BigInteger(new String(this.myBinaryNumStr), 2);
		System.out.println(result);
    }

    // Helper methods:
    public void updateValue(myBinaryNumber s) {
        // Updates value of the binary number.
        this.myBinaryNumStr = s.myBinaryNumStr;
        this.myBinaryNumLen = s.myBinaryNumStr.length;
    }

    public static char[][] arrOfArrs(char[] a, char[] b) {
        // Creates a 2D array / collection of character arrays, i.e two binary numbers
		char[][] result = {a, b};
		return result;
	}

    public static char[][] arrOfArrs(char[] a, char[] b, char[] c) {
        // Creates a 2D array / collection of character arrays, i.e three binary numbers
        char[][] result = {a, b, c};
		return result;
	}

    public static char[] charListToCharArr(ArrayList<Character> listArr){
        // Convert ArrayList<Character> to char[]
		int len = listArr.size();
        char[] arr = new char[len];
		for(int i = 0; i < len; i++) arr[i] = listArr.get(i);
		return arr;
	}

	public static ArrayList<Character> charArrToCharList(char[] arr) {
        // Convert char[] to ArrayList<Character>
        int len = arr.length;
		ArrayList<Character> listArr = new ArrayList<Character>();
		for (int i = 0; i < len; i++) listArr.add(arr[i]);
		return listArr;
	}

    public static char[] remLeadingZeros(char[] num){
        // Remove leading zeros in given binary number
		ArrayList<Character> list = charArrToCharList(num);
		while (list.get(0) == '0') list.remove(0);
		char[] charArr = charListToCharArr(list);
        return charArr;
	}
}
