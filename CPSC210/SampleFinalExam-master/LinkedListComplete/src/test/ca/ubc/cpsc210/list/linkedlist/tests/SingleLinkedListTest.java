package ca.ubc.cpsc210.list.linkedlist.tests;

import ca.ubc.cpsc210.list.linkedlist.MyLinkedList;
import org.junit.jupiter.api.BeforeEach;

public class SingleLinkedListTest extends MyLinkedListTests {

	@BeforeEach
	public void setup() {
		aList = new MyLinkedList<Integer>();
	}
}
