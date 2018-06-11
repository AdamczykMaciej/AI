import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class KMeans {
	public static void main(String[] args) throws FileNotFoundException {

		/*
		 * Reading from the console
		 */
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a number of clusters (k):");
		int k = scan.nextInt();
		scan.close();

		/*
		 * Creating a list of observations
		 */
		File file = new File("iris/test.txt");
		BufferedReader br = null;
		ArrayList<Iris> observations = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader(file));
			String curline;
			while ((curline = br.readLine()) != null) {
				// curline.replaceAll("\t|( )"," ");
				String[] ar = curline.split(",");
				int clusterId = ThreadLocalRandom.current().nextInt(0, k);
				observations.add(new Iris(Double.parseDouble(ar[0]), Double.parseDouble(ar[1]),
						Double.parseDouble(ar[2]), Double.parseDouble(ar[3]), ar[4], clusterId));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * Initialization of clusters
		 */
		final int irisSize = 4; // because Iris has 4 parameters
		double[][] clusters = new double[k][irisSize];

		int[] countObservations = new int[k];

		for (int i = 0; i < clusters.length; i++) {
			int observationId = ThreadLocalRandom.current().nextInt(0, observations.size());
			// randomly assign existing points to clusters ( we don't have to do that
			// randomly)
			int clusterId = observations.get(i).getClusterId();
			double parA = observations.get(i).getA();
			double parB = observations.get(i).getB();
			double parC = observations.get(i).getC();
			double parD = observations.get(i).getD();

			clusters[i][0] = parA;
			clusters[i][1] = parB;
			clusters[i][2] = parC;
			clusters[i][3] = parD;
		}

		/*
		 * THE WHOLE PROCESS of K-MEANS
		 */
		countObservations = new int[k]; // counts observations for every cluster
		double distances[] = new double[k]; // for counting distances between observations and clusters
		double mnDistance = 0; // for finding the smallest Distance
		int mnIndex = 0;
		int it = -1;
		while (true) {
			it++;

			/*
			 * Some printing
			 */
			System.out.println("Iteration: " + it);
			// distances, changing assignments
			System.out.println("-----------IRIS observations and their clusters: -----------");

			for (int i = 0; i < observations.size(); i++) {
				int clusterId = observations.get(i).getClusterId();
				System.out.println("IRIS: " + i + " CLUSTER: " + clusterId);
			}

			double sumOfDistances = 0;
			double sumOfSums = 0;
			for (int j = 0; j < clusters.length; j++) {
				sumOfDistances = 0;
				for (int i = 0; i < observations.size(); i++) {

					if (observations.get(i).getClusterId() == j) {
						sumOfDistances += Math.pow(observations.get(i).getA() - clusters[j][0], 2);
						sumOfDistances += Math.pow(observations.get(i).getA() - clusters[j][1], 2);
						sumOfDistances += Math.pow(observations.get(i).getA() - clusters[j][2], 2);
						sumOfDistances += Math.pow(observations.get(i).getA() - clusters[j][3], 2);
					}

				}
				System.out.println("Centroid " + j + " distance: " + sumOfDistances);
				sumOfSums+=sumOfDistances;
			}
			System.out.println(sumOfSums);

			/*
			 * CHECKING DISTANCES BETWEEN OBSERVATIONS AND CLUSTERS AND REASSIGNING CLUSTERS
			 * TO OBSERVATIONS
			 */
			for (int i = 0; i < observations.size(); i++) { // iterating through all observations
				distances = new double[k];
				for (int j = 0; j < clusters.length; j++) { // iterating through all clusters for every observation
					distances[j] += Math.pow(observations.get(i).getA() - clusters[j][0], 2);
					distances[j] += Math.pow(observations.get(i).getB() - clusters[j][1], 2);
					distances[j] += Math.pow(observations.get(i).getC() - clusters[j][2], 2);
					distances[j] += Math.pow(observations.get(i).getD() - clusters[j][3], 2);
					if (j == 0) { // initial
						mnDistance = distances[j];
						mnIndex = j;
					} else { // finding the smallest Distance
						if (distances[j] < mnDistance) {
							mnDistance = distances[j];
							mnIndex = j;
						}
					}
				}
				// change of the assigned cluster
				observations.get(i).setClusterId(mnIndex);
			}

			/*
			 * Creation of OldClusters for the comparison if anything changed in clusters,
			 * if not - break
			 */
			double[][] oldClusters = new double[k][irisSize]; // for a comparison
			for (int i = 0; i < clusters.length; i++) {
				for (int j = 0; j < clusters[0].length; j++) {
					oldClusters[i][j] = clusters[i][j];
				}
			}

			/*
			 * Recalculating the clusters
			 * 
			 * Counting observations for every cluster
			 */
			countObservations = new int[k];
			for (int i = 0; i < observations.size(); i++) {
				int clusterId = observations.get(i).getClusterId();
				double parA = observations.get(i).getA();
				double parB = observations.get(i).getB();
				double parC = observations.get(i).getC();
				double parD = observations.get(i).getD();

				clusters[clusterId][0] += parA;
				clusters[clusterId][1] += parB;
				clusters[clusterId][2] += parC;
				clusters[clusterId][3] += parD;

				countObservations[clusterId]++; // we will divide by that later (to get correct centroids, means of
												// points)
			}

			/*
			 * Checking if any EMPTY cluster
			 */
//			 for (int i = 0; i < countObservations.length; i++) {
//			 if (countObservations[i] == 0) {
//			 int elementId = ThreadLocalRandom.current().nextInt(0,observations.size());
//			// System.out.println("new assignment to an empty cluster: " + elementId);
////			 observations.get(elementId).setClusterId(i);
////			 clusters[i][0] = observations.get(elementId).getA();
////			 clusters[i][1] = observations.get(elementId).getB();
////			 clusters[i][2] = observations.get(elementId).getC();
////			 clusters[i][3] = observations.get(elementId).getD();
////			 countObservations[i] = 1;
//			
//			 // we should decrease countObservations, but it would result in many
//			 // complications (a vicious circle)
//			 // the difference of length "1" is such a small difference that we can omit
//			 // countObservations[clusterId]--;
//			
//			 // IF WE DO NOT WANT TO ASSIGN EXISTING POINTS TO EMPTY CLUSTERS(CENTROIDS),
//			 // LEAVE THE ASSIGNED VALUES
//			 clusters[i][0] += 0;
//			 clusters[i][1] += 0;
//			 clusters[i][2] += 0;
//			 clusters[i][3] += 0;
//			 countObservations[i] = 1;
//			 }
//			 }

			/*
			 * MEAN
			 */
			for (int i = 0; i < clusters.length; i++) {
				for (int j = 0; j < clusters[0].length; j++) {
					clusters[i][j] /= countObservations[i];
				}
			}
			
			for (int i = 0; i < clusters.length; i++) {
				System.out.println("CLUSTER " + i);
				for (int j = 0; j < clusters[0].length; j++) {
					System.out.print(clusters[i][j] + " ");
				}
				System.out.println();
			}

			/*
			 * CHECK IF CENTROIDS CHANGE
			 */
			boolean areTheSame = true;
			for (int i = 0; i < clusters.length; i++) {
				areTheSame = true;
				for (int j = 0; j < clusters[0].length; j++) {
					if (clusters[i][j] != oldClusters[i][j])
						areTheSame = false;
				}
				if (areTheSame == false)
					break;
			}
			if (areTheSame == false)
				continue;

			break;

		}

		/*
		 * PRINTING CLUSTERS' (CENTROIDS) RESULTS
		 */
		System.out.println("\n---------------RESULTS---------------\n");
		for (int i = 0; i < clusters.length; i++) {
			System.out.println("CLUSTER " + i);
			for (int j = 0; j < clusters[0].length; j++) {
				System.out.print(clusters[i][j] + " ");
			}
			System.out.println();
		}
	}
}
