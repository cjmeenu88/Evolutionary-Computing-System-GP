package data;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import utilities.Settings;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Settings.class)
public class TreeTest {

	@Test
	public void test_Tree_Depth() throws Exception {

		OperandNode left = new OperandNode(String.valueOf(3));
		OperandNode right = new OperandNode(String.valueOf(5));

		OperatorNode plusOperator = new OperatorNode("+");
		plusOperator.setLeftChild(left);
		plusOperator.setRightChild(right);
		Tree tree = new Tree(plusOperator);

		assertEquals("Test tree depth of 2.", 2, tree.depth());
	}

	@Test
	public void test_no_childNodes_Withdepth1() throws Exception {
		// give
		Node expectedNode = new OperandNode(OperandNode.OPERAND_X);

		// when
		Tree testTree1 = GeneticProgrammingTree.generateInitialTree(1);
		// expected
		Assert.assertEquals(expectedNode.numChildNodes(), testTree1.getRoot()
				.numChildNodes());
	}

	@Test
	public void test_Tree_withDepth0() throws Exception {

		try {

			// when
			Tree testTree1 = GeneticProgrammingTree.generateInitialTree(0);
			// expected
			assertNotNull(testTree1.evaluate(0));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Could not generate GeneticProgramming tree");
		}
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void test_Tree_Evaluate() throws Exception {
		OperandNode left = new OperandNode(String.valueOf(2));
		OperandNode right = new OperandNode(String.valueOf(2));

		OperatorNode plusOperator = new OperatorNode("+");
		plusOperator.setLeftChild(left);
		plusOperator.setRightChild(right);
		Tree tree = new Tree(plusOperator);

		double result = tree.evaluate(5);
		assertEquals("Evaluate (2 + 2) tree on x = 5", 4, result, 0.000);
	}

	/**
	 * Test the tree generation to make sure root is always an operator
	 *
	 * @throws Exception
	 */
	@Test
	public void test_root_isoperator() throws Exception {

		for (int i = 0; i < 100; i++) {
			Tree gpTree = Tree.generateTree(4);

			if (gpTree.getRoot().depth() > 1)
				assertThat(gpTree.getRoot(), instanceOf(OperatorNode.class));
			else
				assertThat(gpTree.getRoot(), instanceOf(OperandNode.class));
		}

	}

	@Test
	public void testCreateGeneticProgammingTree() {
		System.out.println("***testCreateGeneticProgammingTree***");

		try {
			GeneticProgrammingTree gpTree = GeneticProgrammingTree
					.createGeneticProgrammingTree(TrainingData
							.getTrainingData());

			System.out.println("Tree depth: " + gpTree.depth());

			gpTree.postOrderPrint();
			System.out.println("");

			double xval = 1;
			System.out.println("Evaluate: " + gpTree.evaluate(xval));

			System.out.println("Fitness: " + gpTree.getFitness());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Could not generate GeneticProgramming tree");
		}
	}

	@Test
	public void testSortGeneticProgammingPopulation() {
		System.out.println("***testSortGeneticProgammingPopulation***");

		int size = 0;

		ArrayList<GeneticProgrammingTree> population = new ArrayList<GeneticProgrammingTree>(
				size);
		try {
			for (int i = 0; i < 10; i++) {
				GeneticProgrammingTree gpTree = GeneticProgrammingTree
						.createGeneticProgrammingTree(TrainingData
								.getTrainingData());

				gpTree.inOrderPrint();
				population.add(gpTree);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Could not generate GeneticProgramming tree");
		}

		System.out.println("Unsorted population");
		for (GeneticProgrammingTree gpTree : population) {
			System.out.println("GP Tree Fitness: " + gpTree.getFitness());
		}

		Collections.sort(population);

		System.out.println("\nSorted population, in descending order");
		for (GeneticProgrammingTree gpTree : population) {
			System.out.println("GP Tree Fitness: " + gpTree.getFitness());
		}
	}

	@Test
	public void test_tree_with_dividebyzero() {
		double value = 0;
		double expected = 1;
		try {
			OperandNode left = new OperandNode(String.valueOf(2));
			OperandNode right = new OperandNode(OperandNode.OPERAND_X);

			OperatorNode Operator = new OperatorNode("/");
			Operator.setLeftChild(left);
			Operator.setRightChild(right);
			Tree tree = new Tree(Operator);

			value = tree.evaluate(0);

			assertEquals(expected, value, 0.001);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_tree_with_zerobyzero() {

		double value = 0;
		double expected = 1;
		try {
			OperandNode left = new OperandNode(String.valueOf(0));
			OperandNode right = new OperandNode(OperandNode.OPERAND_X);

			OperatorNode Operator = new OperatorNode("/");
			Operator.setLeftChild(left);
			Operator.setRightChild(right);
			Tree tree = new Tree(Operator);

			value = tree.evaluate(0);

			assertEquals(expected, value, 0.001);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testTreeCopy() throws Exception {
		System.out.println("***testTreeCopy***");

		Tree copyTree = null;

		OperandNode left = new OperandNode(String.valueOf(3));
		OperandNode right = new OperandNode(String.valueOf(5));

		OperatorNode plusOperator = new OperatorNode("+");
		plusOperator.setLeftChild(left);
		plusOperator.setRightChild(right);
		Tree tree = new Tree(plusOperator);

		try {
			copyTree = Tree.copy(tree);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Could not copy tree");
		}

		assertEquals(tree.evaluate(0), copyTree.evaluate(0), 0.001);
	}
}
