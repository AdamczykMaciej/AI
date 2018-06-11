
//import org.apache.commons.codec.language.bm.Lang;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class Perceptron {
	//public double theta = 0;
	public Double[] weight;
	public String classifier;
	static int a = 10;
	int x = a;

	public Perceptron(String classifier, int weightDim) {

		this.classifier = classifier;
		weight = new Double[weightDim];
		// we can change weights and experiment
		System.out.println("Weights:");
		for (int i = 0; i < weightDim; i++) {
			weight[i] = ThreadLocalRandom.current().nextDouble(-0.1, 0.1);
			// weight[i]=ThreadLocalRandom.current().nextDouble(-2, 2);
			System.out.print(weight[i] + ", ");
		}

	}

	public void update_weight(double alpha, LanguageSample ls) {

		double y = output(ls); // our output
		int d = 0; // DESIRED output
		if (ls.getClassName().equalsIgnoreCase(classifier)) // here the name of a class
			d = 1;

		for (int i = 0; i < weight.length; i++) {
			weight[i] += alpha * (d - y) * y * (1 - y) * ls.parameters[i];
		}
//		// normalization
//		double length = 0;
//
//		for (int i = 0; i < weight.length; i++) {
//			length += Math.pow(weight[i], 2);
//		}
//
//		length = Math.sqrt(length);
//		// if (Double.isNaN(length))
//		// System.out.println("ERRRRRRORRRR");
//		for (int i = 0; i < weight.length; i++) {
//			weight[i] /= length;
//			 //System.out.println(weight[i]+" "+length);
//		}

		//theta += -alpha * (d - y) * y * (1 - y);

	}

	public double output(LanguageSample ls) {
		double net = 0;
		for (int i = 0; i < weight.length; i++) {
			net += ls.parameters[i] * weight[i];
		}
		//net -= theta;

		// if(Double.isNaN(net)) {
		// System.out.println("ERROR");
		// }
		// sigmoid
		double output = 1 / (1 + Math.exp(-net));
		return output;

		// if(dot_product>=theta) {
		// return 1;
		// }
		// return 0;
	}
}
