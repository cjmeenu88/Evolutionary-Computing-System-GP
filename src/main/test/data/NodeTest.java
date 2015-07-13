package data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NodeTest {

	@Test
	public void countTreeNodes() throws Exception {

		OperandNode left = new OperandNode(String.valueOf(1));
		OperandNode right = new OperandNode(String.valueOf(-3));
		OperatorNode first1 = new OperatorNode("+");

		first1.setLeftChild(left);
		first1.setRightChild(right);
		Tree tree = new Tree(first1);

		// assert number of nodes
		int nodeCount = 3;
		assertEquals(nodeCount, tree.getAllNodes().size());
		// assert number of operand nodes
		int opdCount = 2;
		assertEquals(opdCount, tree.getOperandNodes().size());
		// assert number of operator nodes
		int opCount = 1;
		assertEquals(opCount, tree.getOperatorNodes().size());

	}
}
