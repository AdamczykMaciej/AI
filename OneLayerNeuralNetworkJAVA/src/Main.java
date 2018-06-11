import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void train(double alpha, double mxError, Perceptron[] perceptron_list,
			List<LanguageSample> trainingList) {
		double error = 0;
		while (true) {
			double d = 0, y = 0;
			error = 0;
			for (LanguageSample ls : trainingList) {
				for (int i = 0; i < perceptron_list.length; i++) {
					perceptron_list[i].update_weight(alpha, ls);
					y = perceptron_list[i].output(ls);
					if (ls.getClassName().equals(perceptron_list[i].classifier))
						d = 1;
					else
						d = 0;
					error += 0.5 * Math.pow(d - y, 2);
				}
			}
			System.out.println("Error: " + error);
			if (error < mxError)
				break;
		}
	}

	public static void main(String[] args) throws IOException {

		// TRAINING
		File dir = new File("training");
		BufferedReader br;
		Scanner scan;

		List<LanguageSample> trainingList = new ArrayList<>();
		for (final File langDir : dir.listFiles()) {
			for (final File f : langDir.listFiles()) {
				br = new BufferedReader(new InputStreamReader(
	                      new FileInputStream(f), "UTF8"));
				String curToken;
				scan = new Scanner(new FileInputStream(f)); // it is important to specify the encoding (by
																		// we should look
																		// at
																		// the encoding of input files
				LanguageSample ls = new LanguageSample(f.getName(), langDir.getName()); // class names "pl", "en", "fr"
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
				scan.close();
			}

		}
		scan = new Scanner(System.in);
		System.out.println("\nThe learning parameter (alpha): ");
		double alpha = scan.nextDouble(); // the learning parameter
		System.out.println("The maximum error: ");
		double mxError = scan.nextDouble(); // the number of iterations

		final int DIM = 26;
		Perceptron perceptronPL = new Perceptron("pl", DIM);
		Perceptron perceptronEN = new Perceptron("en", DIM);
		Perceptron perceptronFR = new Perceptron("fr", DIM);

		Perceptron[] perceptronList = { perceptronPL, perceptronEN, perceptronFR };

		train(alpha, mxError, perceptronList, trainingList);
		// train(alpha, mxError, perceptronEN, trainingList);
		// train(alpha, mxError, perceptronFR, trainingList);

		// TEST
		File testDir = new File("testing1");

		List<LanguageSample> testList = new ArrayList<LanguageSample>();
		// create LanguageSamples

		for (File f : testDir.listFiles()) {

			int count = 0; // the number of letters (only 26 ACII)
			LanguageSample ls = new LanguageSample(f.getName(), "");
			scan = new Scanner(f, "UTF-8");
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
			testList.add(ls);
		}

		scan.close();
		System.out.println("-------------------TEST--------------------");
		// int good = 0;
		for (int i = 0; i < testList.size(); i++) {
			double outPL = perceptronPL.output(testList.get(i)); // pl
			double outEN = perceptronEN.output(testList.get(i)); // en
			double outFR = perceptronFR.output(testList.get(i)); // fr
			System.out.println("pl: " + outPL);
			System.out.println("en: " + outEN);
			System.out.println("fr: " + outFR);

			double mx = outPL;
			String myOutput = "pl";
			if (outEN > mx) {
				mx = outEN;
				myOutput = "en";
			}
			if (outFR > mx) {
				mx = outFR;
				myOutput = "fr";
			}

			System.out.println("File's name: " + testList.get(i).getName() + " My output: " + myOutput
					+ " Their output: " + testList.get(i).getClassName());
		}
	}
}