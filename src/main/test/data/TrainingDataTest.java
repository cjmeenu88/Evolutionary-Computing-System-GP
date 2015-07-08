package data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class TrainingDataTest {

	@Test
	public void testLoadTrainingData() throws Exception {
		System.out.println("***testTrainingData***");
		ArrayList<TrainingData> trainingData = TrainingData.getTrainingData();
		assertEquals(21, trainingData.size());
	}
}
