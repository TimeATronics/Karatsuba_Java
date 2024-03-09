/*
 * Author: Aradhya Chakrabarti
 * Roll No. 2205880
 */
package com.aradhya.binproj;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Driver {
	/*
	 * Task 4:
	 * Driver class containing the main function
	 */

	// Helper method to generate a random integer (0 or 1).
	public static int getRand() { return ((1 + (int)(Math.random() * 100)) % 2); }
	public static String genRandBinStr(int n) {
		// Helper method to create a n-bit long pseudo-random binary number represented as a String.
    	String randStr = "";
    	for(int i = 0; i < n; i++) {
        	int x = getRand();
        	randStr = randStr + String.valueOf(x);
    	}
    	return randStr;
	}
    public static void main(String[] args) {
		/*
		 * Main method.
		 */
		try {
			StringBuilder testFile = new StringBuilder();
			// Check if user requested read-from-file operation or profiling operation.
			if (args[0].equals("run")) {
				testFile.setLength(0);
				testFile.append(args[1]);
				// Read operands from specified file
				Scanner sc = new Scanner(new File(args[1]));
				String operand1 = sc.next();
				myBinaryNumber a = new myBinaryNumber(operand1);
				String operand2 = sc.next();
				myBinaryNumber b = new myBinaryNumber(operand2);
				sc.close();
				// Compute the sum and product of the operands.
				binaryMultiplicationNaive operations = new binaryMultiplicationNaive();
				myBinaryNumber sum = new myBinaryNumber(String.valueOf(operations.binaryAddition(a, b)));
				myBinaryNumber product = new myBinaryNumber(String.valueOf(operations.binaryMultiplication(a, b)));
				// Write the sum and product to a new file.
				FileWriter wrObj = new FileWriter("output.txt", false);
				wrObj.write(String.valueOf(sum.myBinaryNumStr));
				wrObj.write("\n");
				wrObj.write(String.valueOf(product.myBinaryNumStr));
				wrObj.close();
			} else if (args[0].equals("test")) {
				/*
				 * Task 5 (Part 1)
				 * Profiling
				 */
				binaryMultiplicationNaive a = new binaryMultiplicationNaive();
				binaryMultiplicationFast b = new binaryMultiplicationFast();

				ArrayList<Float> times1 = new ArrayList<Float>();
				ArrayList<Float> times2 = new ArrayList<Float>();
				
				// Lengths of random binary number operands to be generated
				int[] lengths = {10, 20, 30, 40, 50, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
				
				System.out.println("Start Profiling:");
				
				for (int j : lengths) {
					// Generate random binary numbers.
					String m = genRandBinStr(j);
					String n = genRandBinStr(j);
					myBinaryNumber x = new myBinaryNumber(m);
					myBinaryNumber y = new myBinaryNumber(n);
					
					System.out.println("Bit Size = " + j);
					/*
					 * To check elapsed time, difference in system clock time in nano seconds
					 * is computed while the required method is called.
					 */
					long time1start = System.nanoTime();
					char[] product1 = a.binaryMultiplication(x, y);
					long time1stop = System.nanoTime();
					long time1 = time1stop - time1start;
					float t1 = time1 / 1000000; // nanoseconds to miliseconds
					times1.add(t1);
					long time2start = System.nanoTime();
					char[] product2 = b.binaryMultiplication(x, y);
					long time2stop = System.nanoTime();
					long time2 = time2stop - time2start;
					float t2 = time2 / 1000000;
					times2.add(t2);
				}
				System.out.println("End Profiling:");
				
				// Create 2D Line Plots for the Naive and Fast Multiplication algorithm
				// running times.
				LineChart newLine = new LineChart();
				newLine.createJPEG("naive.jpeg", times1, lengths);
				newLine.createJPEG("fast.jpeg", times2, lengths);
			}
		} catch (Exception e) {
				System.out.println("Exception at:");
				e.printStackTrace();
			}          
	}
}

class LineChart {
	/*
	 * Helper Class to create a 2D Line Plot and render it to a JPEG File.
	 */
	public void createJPEG(String filename, ArrayList<Float> times, int[] lengths) throws IOException {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		int len = times.size();
		// Populate the Data Set for all the time values w.r.t the bit sizes.
		for (int i = 0; i < len; i++) {
			dataSet.addValue((double)times.get(i), "Time", String.valueOf(lengths[i]));	
		}
		// Create Line Chart
		JFreeChart lineChartObj = ChartFactory.createLineChart("Time vs BitSize", "BitSize", "Time(ms)",
        	dataSet, PlotOrientation.VERTICAL, true, true, false);
		int width = 640;
		int height = 480;
		File lineChart = new File(filename);
		// Save Line Chart to file
		ChartUtils.saveChartAsJPEG(lineChart, lineChartObj, width ,height);
		System.out.println("Created Plot: " + filename);
	}
}