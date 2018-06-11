import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
	private static final String decisionAttribute = "classOfCar"; // we have to tell the Decision Attribute's name to
																	// identify
	private static final int NUMBER_OF_ATTRIBUTES = 6; // don't take into account the decision attribute

	public static ArrayList<Car> createListOfObservations(File file) {
		ArrayList<Car> cars = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String curLine;
			while ((curLine = br.readLine()) != null) {
				String[] attributes = curLine.split(",");
				// System.out.println(attributes[6]);
				Car car = new Car(attributes[0], attributes[1], attributes[2], attributes[3], attributes[4],
						attributes[5], attributes[6]);
				cars.add(car);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cars;
	}

	public static LinkedHashMap<String, Integer> getDecisionClasses(ArrayList<Car> trainingData,
			LinkedHashMap<String, Integer> occurencesOfdecisionClasses) {
		for (int i = 0; i < trainingData.size(); i++) { // updating a LinkedHashMap (to preserve ordering) (no
														// duplicates)
			// to know all Decision Classes and number of them
			String decisionClass = trainingData.get(i).getAttributes().get(decisionAttribute);
			if (!occurencesOfdecisionClasses.containsKey(decisionClass))
				occurencesOfdecisionClasses.put(decisionClass, 1);
			else {
				int upCounter = occurencesOfdecisionClasses.get(decisionClass) + 1;
				occurencesOfdecisionClasses.put(decisionClass, upCounter);
			}
		}
		return occurencesOfdecisionClasses;
	}

	public static LinkedHashMap<String, Integer> getAttributeClasses(ArrayList<Car> trainingData,
			LinkedHashMap<String, Integer> attributeClasses) {
		ArrayList<Set<String>> listAtt = new ArrayList<>();

		for (int i = 0; i < trainingData.get(0).getAttributes().size(); i++) {
			listAtt.add(new HashSet<String>());
		}
		for (int i = 0; i < trainingData.size(); i++) {
			listAtt.get(0).add(trainingData.get(i).getAttributes().get("buyingPrice"));
			listAtt.get(1).add(trainingData.get(i).getAttributes().get("priceOfMaintenance"));
			listAtt.get(2).add(trainingData.get(i).getAttributes().get("numberOfDoors"));
			listAtt.get(3).add(trainingData.get(i).getAttributes().get("capacity"));
			listAtt.get(4).add(trainingData.get(i).getAttributes().get("sizeOfLuggageBoot"));
			listAtt.get(5).add(trainingData.get(i).getAttributes().get("carSafety"));
		}
		attributeClasses.put("buyingPrice", listAtt.get(0).size());
		attributeClasses.put("priceOfMaintenance", listAtt.get(1).size());
		attributeClasses.put("numberOfDoors", listAtt.get(2).size());
		attributeClasses.put("capacity", listAtt.get(3).size());
		attributeClasses.put("sizeOfLuggageBoot", listAtt.get(4).size());
		attributeClasses.put("carSafety", listAtt.get(5).size());

		return attributeClasses;
	}

	public static void checkDecisions(ArrayList<Car> testCars, ArrayList<Car> testCars2) {
		double purity = 0;
		for (int i = 0; i < testCars.size(); i++) {
			System.out.println("buying price: " + testCars.get(i).getAttributes().get("buyingPrice") + "\n"
					+ "price of maintenance: " + testCars.get(i).getAttributes().get("priceOfMaintenance") + "\n"
					+ "number of doors: " + testCars.get(i).getAttributes().get("numberOfDoors") + "\n" + "capacity: "
					+ testCars.get(i).getAttributes().get("capacity") + "\n" + "size of luggage boot: "
					+ testCars.get(i).getAttributes().get("sizeOfLuggageBoot") + "\n" + "car safety: "
					+ testCars.get(i).getAttributes().get("carSafety"));
			System.out.println("DECISION: "+testCars.get(i).getAttributes().get(decisionAttribute) + "\n"
					+ "------------------------------------------------------");

			if (testCars.get(i).getAttributes().get(decisionAttribute)
					.equals(testCars2.get(i).getAttributes().get(decisionAttribute)))
				purity++;
		}
		System.out.println("Accuracy: " + purity / (double) testCars.size());
	}

	public static void findProbabilities(ArrayList<Car> trainingCars, ArrayList<Car> testCars,
			LinkedHashMap<String, Integer> occurencesOfDecisionClasses,
			LinkedHashMap<String, Integer> attributeClasses) {
		
		int[] found = new int[NUMBER_OF_ATTRIBUTES];
		LinkedHashMap<String, Double> probabilities = new LinkedHashMap<>();
		for (int i = 0; i < testCars.size(); i++) {
			System.out.println(testCars.get(i).getAttributes().values().toString());
			probabilities = new LinkedHashMap<>();
			for (Map.Entry<String, Integer> entry : occurencesOfDecisionClasses.entrySet()) {
				double probability = 1;
				found = new int[NUMBER_OF_ATTRIBUTES];
				for (int j = 0; j < trainingCars.size(); j++) {
					String decisionClass = trainingCars.get(j).getAttributes().get(decisionAttribute);
					if (decisionClass.equals(entry.getKey())) { // looking for trainingCars with the same decisionClass
						if (trainingCars.get(j).getAttributes().get("buyingPrice")
								.equals(testCars.get(i).getAttributes().get("buyingPrice")))
							found[0]++;
						if (trainingCars.get(j).getAttributes().get("priceOfMaintenance")
								.equals(testCars.get(i).getAttributes().get("priceOfMaintenance")))
							found[1]++;
						if (trainingCars.get(j).getAttributes().get("numberOfDoors")
								.equals(testCars.get(i).getAttributes().get("numberOfDoors")))
							found[2]++;
						if (trainingCars.get(j).getAttributes().get("capacity")
								.equals(testCars.get(i).getAttributes().get("capacity")))
							found[3]++;
						if (trainingCars.get(j).getAttributes().get("sizeOfLuggageBoot")
								.equals(testCars.get(i).getAttributes().get("sizeOfLuggageBoot")))
							found[4]++;
						if (trainingCars.get(j).getAttributes().get("carSafety")
								.equals(testCars.get(i).getAttributes().get("carSafety")))
							found[5]++;
					}
				}
				System.out.println("\n****************\n");
				/*
				 * LIKELIHOOD
				 */
				probability *= (double) (found[0] + 1)
						/ (double) (entry.getValue() + attributeClasses.get("buyingPrice") + 1);
				 System.out.println("found " + found[0] + " number of a decisionClass - " +
				 entry.getKey() + " : "
				 + entry.getValue());
				System.out.println(probability);
				probability *= (double) (found[1] + 1)
						/ (double) (entry.getValue() + attributeClasses.get("priceOfMaintenance") + 1);
				 System.out.println("found " + found[1] + " number of a decisionClass - " +
						 entry.getKey() + " : "
						 + entry.getValue());
				System.out.println(probability); 
				probability *= (double) (found[2] + 1)
						/ (double) (entry.getValue() + attributeClasses.get("numberOfDoors") + 1);
				 System.out.println("found " + found[2] + " number of a decisionClass - " +
						 entry.getKey() + " : "
						 + entry.getValue());
				System.out.println(probability);
				probability *= (double) (found[3] + 1)
						/ (double) (entry.getValue() + attributeClasses.get("capacity") + 1);
				 System.out.println("found " + found[3] + " number of a decisionClass - " +
						 entry.getKey() + " : "
						 + entry.getValue());
				System.out.println(probability);
				probability *= (double) (found[4] + 1)
						/ (double) (entry.getValue() + attributeClasses.get("sizeOfLuggageBoot") + 1);
				System.out.println("found " + found[4] + " number of a decisionClass - " +
						 entry.getKey() + " : "
						 + entry.getValue());
				System.out.println(probability);
				probability *= (double) (found[5] + 1)
						/ (double) (entry.getValue() + attributeClasses.get("carSafety") + 1);
				System.out.println("found " + found[5] + " number of a decisionClass - " +
						 entry.getKey() + " : "
						 + entry.getValue());
				System.out.println(probability);
				/*
				 * A PRIORI
				 */
				probability *= (double) (entry.getValue() + 1)
						/ (double) (trainingCars.size() + occurencesOfDecisionClasses.size() + 1);
				System.out.println("A Priori: "+(double) (entry.getValue() + 1)
						/ (double) (trainingCars.size() + occurencesOfDecisionClasses.size() + 1));

				probabilities.put(entry.getKey(), probability);
			}
			Map<String, Double> sorted = probabilities.entrySet().stream() // Java8 approach, we sort the map values
					.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
							LinkedHashMap::new)); // LinkedHashMap to preserve the order
			for (Map.Entry<String, Double> entry : sorted.entrySet()) {
				System.out.println(entry.getKey());
				System.out.println(entry.getValue());
			}
			int k = 0;
			for (Map.Entry<String, Double> ent : sorted.entrySet()) {
				if (k == 0) {
					testCars.get(i).setClassOfCar(ent.getKey());
					System.out.println(ent.getKey());
					System.out.println("-------------------------------");
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		File file = new File("car_bayes/training");
		ArrayList<Car> trainingCars = createListOfObservations(file);

		file = new File("car_bayes/test");
		ArrayList<Car> testCars = createListOfObservations(file);

		LinkedHashMap<String, Integer> occurencesOfDecisionClasses = new LinkedHashMap<>();
		occurencesOfDecisionClasses = getDecisionClasses(trainingCars, occurencesOfDecisionClasses);
		LinkedHashMap<String, Integer> attributeClasses = new LinkedHashMap<>();
		attributeClasses = getAttributeClasses(trainingCars, attributeClasses);

		findProbabilities(trainingCars, testCars, occurencesOfDecisionClasses, attributeClasses);

		file = new File("car_bayes/test");
		ArrayList<Car> testCars2 = createListOfObservations(file); // for Comparison only

		checkDecisions(testCars, testCars2); // the order of inserted arguments matters here
		
		//test case
		System.out.println("Enter Your test case: ");
		Scanner scan = new Scanner(System.in);
		String[] ar = scan.nextLine().split(",");
		Car car = new Car(ar[0], ar[1], ar[2], ar[3], ar[4], ar[5], "");
		ArrayList<Car> test = new ArrayList<>();
		test.add(car);
		occurencesOfDecisionClasses = getDecisionClasses(trainingCars, occurencesOfDecisionClasses);
		attributeClasses = getAttributeClasses(trainingCars, attributeClasses);
		findProbabilities(trainingCars, test, occurencesOfDecisionClasses, attributeClasses);
	}
}
