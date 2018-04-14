package perceptron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class Main {
	public static void main(String[] args) throws IOException {
		Locale.setDefault(new Locale("en"));

		BufferedReader br = new BufferedReader(new FileReader("iris/train.txt"));
		Set<Iris> trainingSet = new HashSet<Iris>();
		int id = 0; // to identify each Iris
		String curline = "";
		// creating a training set
		while ((curline = br.readLine()) != null) {
			String[] input = curline.split(",");
			Iris iris = new Iris(id, Double.parseDouble(input[0]), Double.parseDouble(input[1]),
					Double.parseDouble(input[2]), Double.parseDouble(input[3]), input[4]);
			trainingSet.add(iris);
			id++;
		}
		br.close();

		br = new BufferedReader(new FileReader("iris/test.txt"));
		curline = "";
		Set<Iris> testSet = new HashSet<Iris>();
		// creating test set
		id = 0;
		while ((curline = br.readLine()) != null) {
			String[] ar = curline.split(",");
			Iris iris = new Iris(id, Double.parseDouble(ar[0]), Double.parseDouble(ar[1]), Double.parseDouble(ar[2]),
					Double.parseDouble(ar[3]), ar[4]); // ar[4], because we want to compare answers, normally we would
														// put ""
			testSet.add(iris);
			id++;
		}
		br.close();
		
		// virginica
		Perceptron perceptronA = new Perceptron(trainingSet.size()) {
			@Override
			public void update_weight(double alpha, Iris x, int length) {
				
				int y = output(x); //our output
				int d; //DESIRED output
				if(x.getclassName().equals("Iris-virginica")) //here the name of a class
					d=1;
				else
					d=0;
				
				//WEIGHTS
				weight[0]+=(d-y)*alpha*x.getA();
				weight[1]+=(d-y)*alpha*x.getB(); 
				weight[2]+=(d-y)*alpha*x.getC(); 
				weight[3]+=(d-y)*alpha*x.getD();

				//THETA
				theta+=(d-y)*alpha*(-1); 
			}
		}; 
		
		// versicolor
		Perceptron perceptronB = new Perceptron(trainingSet.size()) {
			
			@Override
			public void update_weight(double alpha, Iris x, int length) {
				
				int y = output(x); //our output
				int d; //DESIRED output
				if(x.getclassName().equals("Iris-versicolor")) //here the name of a class (the only change)
					d=1;
				else
					d=0;
				
				//WEIGHTS
				weight[0]+=(d-y)*alpha*x.getA();
				weight[1]+=(d-y)*alpha*x.getB(); 
				weight[2]+=(d-y)*alpha*x.getC(); 
				weight[3]+=(d-y)*alpha*x.getD();

				//THETA
				theta+=(d-y)*alpha*(-1); 
			}
		}; 
		
		//setosa
		Perceptron perceptronC = new Perceptron(trainingSet.size()) {
			
			@Override
			public void update_weight(double alpha, Iris x, int length) {
				
				int y = output(x); //our output
				int d; //DESIRED output
				if(x.getclassName().equals("Iris-setosa")) //here the name of a class (the only change)
					d=1;
				else
					d=0;
				
				//WEIGHTS
				weight[0]+=(d-y)*alpha*x.getA();
				weight[1]+=(d-y)*alpha*x.getB(); 
				weight[2]+=(d-y)*alpha*x.getC(); 
				weight[3]+=(d-y)*alpha*x.getD();

				//THETA
				theta+=(d-y)*alpha*(-1); 
			}
		};

		// Training

		Scanner scan = new Scanner(System.in);
		System.out.println("The learning parameter (alpha): ");
		double alpha = scan.nextDouble(); // the learning parameter
		System.out.println("The number of iterations: ");
		int numOfIt = scan.nextInt(); // the number of iterations

		// our test case
		// String test= scan.next();
		// String[] testCase = test.split(",");
		// Iris iris = new Iris(id, Double.parseDouble(testCase[0]),
		// Double.parseDouble(testCase[1]), Double.parseDouble(testCase[2]),
		// Double.parseDouble(testCase[3]), "");
		scan.close();

		// UPDATING WEIGHTS
		for (int i = 0; i < numOfIt; i++) {
			for (Iris iris : trainingSet) {
				perceptronA.update_weight(alpha, iris, trainingSet.size());
				perceptronB.update_weight(alpha, iris, trainingSet.size());
				perceptronC.update_weight(alpha, iris, trainingSet.size());

			}
		}

		// output of our test case

		// System.out.println(Perceptron.outputClass(iris)); // output

		// output from a file
		
		System.out.println("-------------------TEST--------------------");
		int good = 0;
		for (Iris x : testSet) {
			int outA = perceptronA.output(x); // virginica
			int outB = perceptronB.output(x); // versicolor
			int outC = perceptronC.output(x); // setosa
			//int outputC = perceptronA.output(x);
			System.out.println(outA);
			System.out.println(outB);
			String myOutput;
			if(outA == 1)
				myOutput="Iris-virginica";
			else if(outB == 1)
				myOutput="Iris-versicolor";
			else if(outC == 1)
				myOutput="Iris-setosa";
			else
				myOutput="Iris-virginica";
			
			String theirOutput = x.getclassName();
			System.out.println("My output: " + myOutput + " Their output: " + theirOutput);
			if (myOutput.equals(theirOutput))
				good++;
		}

		System.out.println("The percentage of good answers: " + (double) good / testSet.size());

	}
}
