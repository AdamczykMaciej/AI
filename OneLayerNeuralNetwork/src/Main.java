import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {

		// TRAINING
		File dir = new File("training");
		System.out.println("Name" + dir.getName());
		BufferedReader br;
		Scanner scan;

		final int DIM = 26;
		Perceptron perceptronPL = new Perceptron(DIM);
		Perceptron perceptronEN = new Perceptron(DIM) {
			@Override
			public void update_weight(double alpha, LanguageSample ls) {
				double y = output(ls); // our output
				int d; // DESIRED output
				if (ls.getClassName().equalsIgnoreCase("en")) // here the name of a class
					d = 1;
				else
					d = 0;

				// WEIGHTS

				for (int i = 0; i < weight.length; i++) {
					weight[i] += (d - y) * alpha * ls.parameters[i] * y * (1 - y);
				}
				// normalization
//				double length = 0;
//
//				for (int i = 0; i < ls.parameters.length; i++) {
//					length += Math.pow(ls.parameters[i], 2);
//				}
//
//				length = Math.sqrt(length);
//				if (Double.isNaN(length))
//					System.out.println("ERRRRRRORRRR");
//				for (int i = 0; i < weight.length; i++) {
//					weight[i] /= BigDecimal.valueOf(weight[i]).round(new MathContext(20, RoundingMode.HALF_UP))
//							.doubleValue();
//					// System.out.println(weight[i]+" "+length);
//				}

				theta += (d - y) * alpha * (-1) * y * (1 - y);

			}
		};
		Perceptron perceptronFR = new Perceptron(DIM) {
			@Override
			public void update_weight(double alpha, LanguageSample ls) {
				double y = output(ls); // our output
				int d; // DESIRED output
				if (ls.getClassName().equalsIgnoreCase("fr")) // here the name of a class
					d = 1;
				else
					d = 0;

				// WEIGHTS

				for (int i = 0; i < weight.length; i++) {
					weight[i] += (d - y) * alpha * ls.parameters[i] * y * (1 - y);
				}
				// normalization
//				double length = 0;
//
//				for (int i = 0; i < ls.parameters.length; i++) {
//					length += Math.pow(ls.parameters[i], 2);
//				}
//
//				length = Math.sqrt(length);
//				if (Double.isNaN(length))
//					System.out.println("ERRRRRRORRRR");
//				for (int i = 0; i < weight.length; i++) {
//					weight[i] /= BigDecimal.valueOf(weight[i]).round(new MathContext(20, RoundingMode.HALF_UP))
//							.doubleValue();
//					// System.out.println(weight[i]+" "+length);
//				}

				theta += (d - y) * alpha * (-1) * y * (1 - y);

			}
		};

		// TRAINING
		List<LanguageSample> trainingList = new ArrayList<>();
		for (final File langDir : dir.listFiles()) {
			for (final File f : langDir.listFiles()) {
				br = new BufferedReader(new FileReader(f));
				String curToken;
				scan = new Scanner(new FileInputStream(f), "UTF-8"); // it is important to specify the encoding (by
																		// default it is not UTF-8), we should look at
																		// the encoding of input files

				LanguageSample ls = new LanguageSample(langDir.getName());
				ls.setClassName(langDir.getName()); // class names "pl", "en", "fr"
				int count = 0; // the number of letters (only 26 ACII)
				while (scan.hasNext()) {

					String[] token = scan.next().split("");
					for (int i = 0; i < token.length; i++) {
						char c = token[i].charAt(0);
						// System.out.println(c);
						if ((c >= 65 && c <= 90)) { // ASCII, CHECKING TOKENS, uppercase, lowercase letters
							count++;
							ls.parameters[(int) c - 'A']++;

						} else if (c >= 97 && c <= 122) {
							count++;
							ls.parameters[(int) c - 'a']++;
						}
					}
				}

				for (int i = 0; i < ls.parameters.length; i++) {
					ls.parameters[i] /= (double) count;
				}

				trainingList.add(ls);
			}
		}
		scan = new Scanner(System.in);
		System.out.println("\nThe learning parameter (alpha): ");
		double alpha = scan.nextDouble(); // the learning parameter
		System.out.println("The maximum error: ");
		double mxError = scan.nextDouble(); // the number of iterations

		double errorPL = 0, errorEN = 0, errorFR = 0; // to be able to calculate the error, look further
		do { // PL, calculating the error and comparing with mx
			errorPL = 0;
			double dPL; // desired output, for calculating the Error
			double yPL; // output
			for (int j = 0; j < trainingList.size(); j++) {

				if (trainingList.get(j).getClassName().equalsIgnoreCase("pl")) // here the name of a class
					dPL = 1;
				else
					dPL = 0;

				perceptronPL.update_weight(alpha, trainingList.get(j));
				yPL = perceptronPL.output(trainingList.get(j));
				errorPL += (0.5 * (Math.pow((dPL - yPL), 2)));
			}
			System.out.println("PL " + errorPL);
		} while (errorPL > mxError);
		do { // EN
			errorEN = 0;
			for (int j = 0; j < trainingList.size(); j++) {
				double dEN; // desired output, for calculating the Error
				double yEN; // output
				if (trainingList.get(j).getClassName().equalsIgnoreCase("en")) // here the name of a class
					dEN = 1;
				else
					dEN = 0;

				perceptronEN.update_weight(alpha, trainingList.get(j));
				yEN = perceptronEN.output(trainingList.get(j));
				errorEN += 0.5 * (Math.pow((dEN - yEN), 2));

			}
			System.out.println("EN " + errorEN);
		} while (errorEN > mxError);
		do { // FR
			errorFR = 0;
			for (int j = 0; j < trainingList.size(); j++) {
				double dFR; // desired output, for calculating the Error
				double yFR; // output
				if (trainingList.get(j).getClassName().equalsIgnoreCase("fr")) // here the name of a class
					dFR = 1;
				else
					dFR = 0;

				perceptronFR.update_weight(alpha, trainingList.get(j));
				yFR = perceptronFR.output(trainingList.get(j));
				errorFR += 0.5 * (Math.pow((dFR - yFR), 2));

			}
			System.out.println("FR error: " + errorFR);
		} while (errorFR > mxError);

		// TEST
		File testDir = new File("test");

		List<LanguageSample> testList = new ArrayList<LanguageSample>();
		// create LanguageSamples
		for (final File f : testDir.listFiles()) {

			int count = 0; // the number of letters (only 26 ACII)
			LanguageSample ls = new LanguageSample(testDir.getName());
			ls.setClassName(f.getName());
			scan = new Scanner(f);
			while (scan.hasNext()) {

				String[] token = scan.next().split("");
				for (int i = 0; i < token.length; i++) {
					char c = token[i].charAt(0);
					// System.out.println(c);
					if ((c >= 65 && c <= 90)) { // ASCII, CHECKING TOKENS, uppercase, lowercase letters
						count++;
						// String letter = c + "";

						for (int k = 0; k < ls.parameters.length; k++) {
							ls.parameters[(int) c - 'A']++;
						}
					} else if (c >= 97 && c <= 122) {
						count++;
						ls.parameters[(int) c - 'a']++;

					}
				}
			}

			for (int i = 0; i < ls.parameters.length; i++) {
				ls.parameters[i] /= (double) count;
			}

			testList.add(ls);
		}
		System.out.println("-------------------TEST--------------------");
		// int good = 0;
		for (int i = 0; i < testList.size(); i++) {
			double outA = perceptronPL.output(testList.get(i)); // pl
			double outB = perceptronEN.output(testList.get(i)); // en
			double outC = perceptronFR.output(testList.get(i)); // fr
			// int outputC = perceptronA.output(x);
			System.out.println("1: " + outA);
			System.out.println("2: " + outB);
			System.out.println("3: " + outC);

			double mx = outA;
			String myOutput = "PL";
			if (outB > mx) {
				mx = outB;
				myOutput = "EN";
			} else if (outC > mx) {
				mx = outC;
				myOutput = "FR";
			}

			String theirOutput = testList.get(i).getClassName();
			System.out.println("My output: " + myOutput + " Their output: " + theirOutput);
		}
	}
}