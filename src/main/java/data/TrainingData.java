package data;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TrainingData {

	public final static String TRAINING_FILE_NAME = "trainingdata.txt";
	private static ArrayList<TrainingData> trainingDataList = null;

	double inputData;
	double outputData;

	public TrainingData(double inputData, double outputData) {
		this.inputData = inputData;
		this.outputData = outputData;
	}

	public double inputData() {
		return inputData;
	}

	public double outputData() {
		return outputData;
	}

	// public static void generateInitialTrainingDataOriginal(double minValue,
	// int dataSetSize) throws Exception {
	//
	// FileOutputStream outputStream = new
	// FileOutputStream("trainingdata_orig.txt");
	//
	// DataOutputStream out = new DataOutputStream(outputStream);
	//
	// BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
	//
	// for (int i = 0; i < dataSetSize; i++) {
	// double x = minValue;
	// double y = ((x * x) - 1)/2;
	//
	// bw.write("" + x + "," + y + "\n");
	//
	// minValue++;
	// }
	//
	// bw.flush();
	// bw.close();
	// }

	// public static void generateInitialTrainingDataOptional(double minValue,
	// int dataSetSize) throws Exception {
	//
	// FileOutputStream outputStream = new
	// FileOutputStream("trainingdata_opt.txt");
	//
	// DataOutputStream out = new DataOutputStream(outputStream);
	//
	// BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
	//
	// for (int i = 0; i < dataSetSize; i++) {
	// double x = minValue;
	// double y = ((-3 * (x * x * x)) + 7)/2;
	//
	// bw.write("" + x + "," + y + "\n");
	//
	// minValue++;
	// }
	//
	// bw.flush();
	// bw.close();
	// }

	public static ArrayList<TrainingData> getTrainingData() throws Exception {
		if (trainingDataList == null) {
			trainingDataList = readTrainingData(TRAINING_FILE_NAME);
		}

		return trainingDataList;
	}

	private static ArrayList<TrainingData> readTrainingData(String fileName)
			throws Exception {
		ArrayList<TrainingData> trainingDataList = new ArrayList<TrainingData>();

		InputStream inputStream = TrainingData.class.getClassLoader()
				.getResourceAsStream(fileName);

		DataInputStream in = new DataInputStream(inputStream);

		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String strLine;

		while ((strLine = br.readLine()) != null) {
			String[] strArr = strLine.split(",");

			double inputData = Double.parseDouble(strArr[0]);

			double outputData = Double.parseDouble(strArr[1]);
			System.out.print("input is,  " + inputData);
			System.out.println("output is " + outputData);
			TrainingData trainingData = new TrainingData(inputData, outputData);

			trainingDataList.add(trainingData);
		}

		in.close();

		return trainingDataList;
	}
}
