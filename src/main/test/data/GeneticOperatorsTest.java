package data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import org.junit.Test;

import utilities.GeneticOperators;
import utilities.Settings;
import utilities.Utilities;

public class GeneticOperatorsTest {

	@Test(expected = Exception.class)
	public void testCrossover_throwsException_nullArguments() throws Exception {
		GeneticOperators.crossover(null, null);

	}

	@Test
	public void test_Crossover_withdepth2() throws Exception {

		double value1 = 0;
		double value2 = 0;

		System.out.println("***testCrossover***");

		OperandNode left1 = new OperandNode(String.valueOf(3));
		OperandNode right1 = new OperandNode(String.valueOf(5));

		OperatorNode plusOperator = new OperatorNode("+");
		plusOperator.setLeftChild(left1);
		plusOperator.setRightChild(right1);
		Tree tree1 = new Tree(plusOperator);
		value1 = tree1.evaluate(0);

		OperandNode left2 = new OperandNode(String.valueOf(6));
		OperandNode right2 = new OperandNode(String.valueOf(4));

		OperatorNode DivOperator = new OperatorNode("/");
		DivOperator.setLeftChild(left2);
		DivOperator.setRightChild(right2);
		Tree tree2 = new Tree(DivOperator);
		value2 = tree2.evaluate(0);

		GeneticOperators.crossover(tree1, tree2);

		assertNotSame(tree1.evaluate(0), value1);
		assertNotSame(tree2.evaluate(0), value2);
	}

	@Test
	public void test_Crossover_withdepth1() throws Exception {

		double value1 = 0;
		double value2 = 0;

		System.out.println("***testCrossover***");

		OperandNode terminal = new OperandNode("x");
		Tree tree1 = new Tree(terminal);
		value1 = tree1.evaluate(0);

		OperandNode left2 = new OperandNode(String.valueOf(6));
		OperandNode right2 = new OperandNode(String.valueOf(4));

		OperatorNode DivOperator = new OperatorNode("/");
		DivOperator.setLeftChild(left2);
		DivOperator.setRightChild(right2);
		Tree tree2 = new Tree(DivOperator);
		value2 = tree2.evaluate(0);

		GeneticOperators.crossover(tree2, tree1);

		assertNotSame(tree1.evaluate(0), value1);
		assertNotSame(tree2.evaluate(0), value2);

		GeneticOperators.crossover(tree1, tree2);

		assertNotSame(tree1.evaluate(0), value1);
		assertNotSame(tree2.evaluate(0), value2);
	}

	@Test
	public void testSelection() throws Exception {
		System.out.println("***testSelection***");

		Properties settings = Settings.getSettings();
		String prop = settings.getProperty(Settings.PROP_POPULATION_SIZE);
		int size = Integer.parseInt(prop);

		ArrayList<GeneticProgrammingTree> population = GeneticProgrammingTree
				.getGeneticTreePopulation(size);
		ArrayList<GeneticProgrammingTree> newPopulation = GeneticOperators
				.selection(population);
		Collections.sort(population);

		// make sure sizes of original population and after selection are not
		// the same
		double selectionProbability = Settings.getSurvivalProbability();
		int customPopSize = 340;

		int numberOfSurvivors = (int) Math.ceil(selectionProbability
				* customPopSize);

		assertEquals(34, numberOfSurvivors); // test to make sure probability
												// and number of trees moving
												// forward
		assertFalse(population.size() == newPopulation.size());

	}

	@Test
	public void test_mutate_depth2() throws Exception {

		OperandNode left1 = new OperandNode(String.valueOf(3));
		OperandNode right1 = new OperandNode(String.valueOf(5));

		OperatorNode plusOperator = new OperatorNode("+");
		plusOperator.setLeftChild(left1);
		plusOperator.setRightChild(right1);
		Tree singleTree = new Tree(plusOperator);

		ArrayList<String> beforeMutationLst = Node.postOrderItems(singleTree
				.getRoot());
		String postOrderStr = Utilities
				.convertArrayListToString(beforeMutationLst);
		int originalDepth = singleTree.depth();

		GeneticOperators.mutate(singleTree);

		ArrayList<String> afterMutationLst = Node.postOrderItems(singleTree
				.getRoot());
		String postOrderStrAfter = Utilities
				.convertArrayListToString(afterMutationLst);

		assertEquals(originalDepth, singleTree.depth());
		assertNotSame(postOrderStrAfter, postOrderStr);
	}

	@Test
	public void test_mutate_depth1() throws Exception {

		OperandNode terminal = new OperandNode("x");
		Tree singleTree = new Tree(terminal);

		ArrayList<String> beforeMutationLst = Node.postOrderItems(singleTree
				.getRoot());
		String postOrderStr = Utilities
				.convertArrayListToString(beforeMutationLst);
		int originalDepth = singleTree.depth();

		GeneticOperators.mutate(singleTree);

		ArrayList<String> afterMutationLst = Node.postOrderItems(singleTree
				.getRoot());
		String postOrderStrAfter = Utilities
				.convertArrayListToString(afterMutationLst);

		assertEquals(originalDepth, singleTree.depth());
		assertNotSame(postOrderStrAfter, postOrderStr);
	}

	@Test
	public void test_probabilities() throws Exception {
		double pop = 0;

		int treesize = 0;

		double crossoverProbablity = Settings.getCrossoverProbability();

		int numberOfPairsForCrossover = (int) Math
				.floor((crossoverProbablity * pop) / 2);

		assertEquals(numberOfPairsForCrossover, 0);

		double numofTreesForMutation = Math.ceil(Settings
				.getMutationProbability() * treesize);

		assertEquals(numofTreesForMutation, 0, 0.001);

	}

}
