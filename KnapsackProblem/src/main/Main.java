package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static int[] parseToBinary(int number, int size) {
		int[] arr = new int[size];
		int i = size - 1;
		while (number > 0) {
			arr[i] = number % 2;
			number /= 2;
			i--;
		}
		return arr;
	}

	public static int isSet(int num, int at) {
		return (num >> at) & 1; 
	}

	public static void main(String[] args) throws FileNotFoundException {

		int capacity = 0;
		int totalValue = 0;
		int totalCapacity = 0;
		int mxValue = -1;
		int mxWeight = 0;
		int bestVector =0;
		int[][] elems = new int[30][2];
		final int fileNum = 1;
		File file = new File("./knapsack_data/"+fileNum);
		Scanner scan = new Scanner(file);
		capacity = scan.nextInt();
		scan.nextLine();
		int k = 0;
		while (scan.hasNextLine()) {

			String[] element = scan.nextLine().split(" ");
			elems[k][0] = Integer.parseInt(element[0]);
			elems[k][1] = Integer.parseInt(element[1]);
			k++;
		}

		int n = 30; // the number of elements
		
		for (int i = 0; i < (1 << n); i++) {
			totalValue = 0;
			totalCapacity = 0;
			
			
			for (int j = 0; j < n; j++) {
				totalValue += elems[j][0] * isSet(i, j);
				totalCapacity += elems[j][1] * isSet(i, j);

			}
			if (totalCapacity <= capacity) {
				if (mxValue < totalValue) {
					mxValue = totalValue;
					mxWeight = totalCapacity;
					bestVector = i;
				}
			}

		}
		
//		for(int i=0; i< (1 << n); i++) {
//			totalValue = 0;
//			totalCapacity = 0;
//			//System.out.print("{");
////			String vec = "";
//			for(int j=0; j < n; j++) {
//				
//				if((i & (1 << j)) > 0) {
//					totalValue+=elems[j][0];
//					totalCapacity+=elems[j][1];
////					vec+=1;
//					//System.out.print(j+" ");
//				}
//			}
//			if (totalCapacity <= capacity) {
//				if (mxValue < totalValue) {
//					mxValue = totalValue;
//					bestVector = i;
//				}
//			}
			//System.out.println("}");
//		}
	
		System.out.println("Best vector: " + new StringBuilder(Integer.toBinaryString(bestVector)).reverse());
		System.out.println("Total value: " + mxValue);
		System.out.println("Total capacity/weight: " + mxWeight);
	}

}
