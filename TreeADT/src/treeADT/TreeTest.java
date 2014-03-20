package treeADT;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class TreeTest {

	Tree<String> t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;
	ArrayList<Tree<Integer>> integerTrees;

	@Before
	public void setUp() throws Exception {
		t10 = new Tree<String>("ten");
		t9 = new Tree<String>("nine");
		t8 = new Tree<String>("eight");
		t7 = new Tree<String>("seven");
		t6 = new Tree<String>("six");
		t5 = new Tree<String>("five", t7, t8, t9, t10);
		t4 = new Tree<String>("four", t6);
		t3 = new Tree<String>("three");
		t2 = new Tree<String>("two", t4, t5);
		t1 = new Tree<String>("one", t3, t4);
		t0 = new Tree<String>("zero", t1, t2);
		integerTrees = new ArrayList<Tree<Integer>>();
		for (int i = 0; i < 8; i++) {
			integerTrees.add(new Tree<Integer>(i));
		}
	}

	/**
	 * Tests Tree constructor
	 */
	@Test
	public void testTree() {
		assertEquals(t0.getValue(), "zero");
		assertEquals(4, t5.numberOfChildren());
	}

	/**
	 * Tests getValue
	 */
	@Test
	public void testGetValue() {
		for (int i = 0; i < 8; i++) {
			assertEquals(integerTrees.get(i).getValue(), (Integer) i);
		}
		assertEquals(t0.getValue(), "zero");
		assertEquals(t10.getValue(), "ten");
	}

	/**
	 * Tests firstChild
	 */
	@Test
	public void testFirstChild() {
		assertSame(t1, t0.firstChild());
		assertNotSame(t2, t0.firstChild());
		assertSame(t3, t1.firstChild());
		assertNotSame(t4, t1.firstChild());
		assertSame(t4, t2.firstChild());
		assertSame(t7, t5.firstChild());
	}

	/**
	 * Tests lastChild
	 */
	@Test
	public void testLastChild() {
		assertSame(t2, t0.lastChild());
		assertNotSame(t1, t0.lastChild());
		assertNotSame(t7, t5.lastChild());
		assertNotSame(t8, t5.lastChild());
		assertNotSame(t9, t5.lastChild());
		assertSame(t10, t5.lastChild());
	}

	/**
	 * Tests numberOfChildren
	 */
	@Test
	public void testNumberOfChildren() {
		assertEquals(2, t0.numberOfChildren());
		assertEquals(2, t1.numberOfChildren());
		assertEquals(2, t2.numberOfChildren());
		assertEquals(0, t3.numberOfChildren());
		assertEquals(1, t4.numberOfChildren());
		assertEquals(4, t5.numberOfChildren());
		assertEquals(0, t6.numberOfChildren());
	}

	/**
	 * Tests child
	 */
	@Test
	public void testChild() {
		assertSame(t2, t0.child(1));
		assertNotSame(t1, t0.child(1));
		assertSame(t1, t0.child(0));
		assertSame(t7, t5.child(0));
		assertSame(t8, t5.child(1));
		assertSame(t9, t5.child(2));
		assertSame(t10, t5.child(3));
		assertNotSame(t10, t5.child(2));
	}

	/**
	 * Tests child's NoSuchElementException for index less than zero
	 * or greater than or equal to the number of children
	 */
	@Test(expected = NoSuchElementException.class)
	public void testChildException() {
		t0.child(-1);
		t0.child(2);
	}

	/**
	 * Tests children
	 */
	@Test
	public void testChildren() {
		Iterator<Tree<String>> iterator = (Iterator<Tree<String>>) t5
				.children();
		assertSame(t7, iterator.next());
		assertSame(t8, iterator.next());
		assertSame(t9, iterator.next());
		assertSame(t10, iterator.next());
		assertFalse(iterator.hasNext());
	}

	/**
	 * Tests isLeaf
	 */
	@Test
	public void testIsLeaf() {
		assertFalse(t0.isLeaf());
		assertFalse(t2.isLeaf());
		assertFalse(t4.isLeaf());
		assertTrue(t3.isLeaf());
		assertTrue(t6.isLeaf());
		assertTrue(t10.isLeaf());
	}

	/**
	 * Tests equals
	 */
	@Test
	public void testEqualsObject() {
		Tree<String> equals3 = new Tree<String>("three");
		Tree<String> equals2 = new Tree<String>("two", t4, t5);
		Tree<String> equals1 = new Tree<String>("one", t3, t4);
		Tree<String> equals0 = new Tree<String>("zero", t1, t2);
		Tree<String> not0 = new Tree<String>("notzero", t1, t2);
		Tree<String> not1 = new Tree<String>("one", t3);
		Tree<String> not2 = new Tree<String>("two", t6);
		assertTrue(t0.equals(t0));
		assertTrue(equals0.equals(t0));
		assertTrue(equals1.equals(t1));
		assertTrue(equals2.equals(t2));
		assertTrue(equals3.equals(t3));
		assertFalse(not0.equals(t0));
		assertFalse(not1.equals(t2));
		assertFalse(not2.equals(t2));
	}

	/**
	 * Tests toString
	 */
	@Test
	public void testToString() {
		assertEquals(t4.toString(), "four\n  six\n");
		assertEquals(t1.toString(), "one\n  three\n  four\n    six\n");
		assertEquals(t5.toString(), "five\n  seven\n  eight\n  nine\n  ten\n");
	}

	/**
	 * Tests setValue
	 */
	@Test
	public void testSetValue() {
		t0.setValue("a billion");
		assertEquals(t0.getValue(), "a billion");
		integerTrees.get(0).setValue(100);
		assertEquals(integerTrees.get(0).getValue(), (Integer) 100);
	}

	/**
	 * Tests addChild (to end)
	 */
	@Test
	public void testAddChildTreeOfV() {
		assertEquals(t5.numberOfChildren(), 4);
		Tree<String> t11 = new Tree<String>("eleven");
		t5.addChild(t11);
		assertEquals(t5.numberOfChildren(), 5);
		assertEquals(t5.child(4).getValue(), "eleven");
	}

	/**
	 * Tests addChild's IllegalArgumentException for duplicate children
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddChildTreeOfVDuplicateException() {
		t1.addChild(t7);
		t0.addChild(t7);
	}

	/**
	 * Tests addChild's IllegalArgumentException for created a cycle
	 * in a tree.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddChildTreeOfVLoopException() {
		t7.addChild(t0);
		t7.addChild(t2);
		t7.addChild(t5);
	}

	/**
	 * Tests addChild (at index)
	 */
	@Test
	public void testAddChildIntTreeOfV() {
		assertEquals(t5.numberOfChildren(), 4);
		Tree<String> t11 = new Tree<String>("eleven");
		t5.addChild(2, t11);
		assertEquals(t5.numberOfChildren(), 5);
		assertEquals(t5.child(0).getValue(), "seven");
		assertEquals(t5.child(1).getValue(), "eight");
		assertEquals(t5.child(2).getValue(), "eleven");
		assertEquals(t5.child(3).getValue(), "nine");
		assertEquals(t5.child(4).getValue(), "ten");
	}

	/**
	 * Tests addChildren
	 */
	@Test
	public void testAddChildren() {
		t3.addChildren(t7, t8, t9, t10);
		assertEquals(t3.numberOfChildren(), 4);
		assertEquals(t3.child(0).getValue(), "seven");
		assertEquals(t3.child(1).getValue(), "eight");
		assertEquals(t3.child(2).getValue(), "nine");
		assertEquals(t3.child(3).getValue(), "ten");
	}

	/**
	 * Tests addChildren's IllegalArgumentException for duplicate children
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddChildrenDuplicateException() {
		assertEquals(t5.numberOfChildren(), 4);
		t5.addChildren(t6, t8);
		assertEquals(t5.numberOfChildren(), 5);
		assertEquals(t5.child(4).getValue(), "six");
	}
	
	/**
	 * Tests addChild's IllegalArgumentException for created a cycle
	 * in a tree.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddChildrenLoopException() {
		assertEquals(t4.numberOfChildren(), 1);
		t4.addChildren(t7, t8, t1);
		assertEquals(t4.numberOfChildren(), 3);
		assertEquals(t4.child(0).getValue(), "six");
		assertEquals(t4.child(1).getValue(), "seven");
		assertEquals(t4.child(2).getValue(), "eight");
	}

	/**
	 * Tests removeChild
	 */
	@Test
	public void testRemoveChild() {
		assertEquals(t5.numberOfChildren(), 4);
		assertEquals(t7, t5.removeChild(0));
		assertEquals(t5.numberOfChildren(), 3);
		assertEquals(t5.child(0).getValue(), "eight");
		assertEquals(t9, t5.removeChild(1));
		assertEquals(t5.numberOfChildren(), 2);
		assertEquals(t5.child(0).getValue(), "eight");
		assertEquals(t5.child(1).getValue(), "ten");
		assertEquals(t10, t5.removeChild(1));
		assertEquals(t5.numberOfChildren(), 1);
		assertEquals(t5.child(0).getValue(), "eight");

	}

	/**
	 * Tests removeChild's NoSuchElementException for invalid indexes.
	 */
	@Test(expected = NoSuchElementException.class)
	public void testRemoveChildException() {
		t5.removeChild(4);
		t6.removeChild(0);
	}

}
