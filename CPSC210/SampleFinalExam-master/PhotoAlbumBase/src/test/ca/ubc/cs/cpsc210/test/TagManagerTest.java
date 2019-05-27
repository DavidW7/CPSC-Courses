package ca.ubc.cs.cpsc210.test;

import ca.ubc.cs.cpsc210.photo.Tag;
import ca.ubc.cs.cpsc210.photo.TagManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests basic operations of TagManager
 */
public class TagManagerTest {
	
	TagManager aTagManager;

	@BeforeEach
	public void setUp() throws Exception {
		aTagManager = new TagManager();
	}

	@Test
	public void testCreateUniqueTags()  {
		aTagManager.createTag("birthday");
		aTagManager.createTag("AuntBetty");
		assertEquals(2, aTagManager.getTags().size());
		assertNotNull(aTagManager.findTag("birthday"));
		assertNotNull(aTagManager.findTag("AuntBetty"));
	}
	
	@Test 
	public void testRemoveTag()  {
		aTagManager.createTag("birthday");
		aTagManager.createTag("AuntBetty");
		assertEquals(2, aTagManager.getTags().size());
		assertTrue(aTagManager.removeTag("birthday"));
		assertEquals(1, aTagManager.getTags().size());
		assertNotNull(aTagManager.findTag("AuntBetty"));
	}
	
	@Test
	public void testRemoveTagTwice()  {
		aTagManager.createTag("birthday");
		aTagManager.removeTag("birthday");
		assertFalse(aTagManager.removeTag("birthday"));
	}

	@Test
	public void renameTag()  {
		aTagManager.createTag("birthday");
		Tag birthdayTag = aTagManager.findTag("birthday");
		aTagManager.renameTag("birthday", "newBirthday");
		assertNull(aTagManager.findTag("birthday"));
		assertNotNull(aTagManager.findTag("newBirthday"));
		assertEquals(birthdayTag, aTagManager.findTag("newBirthday"));
		aTagManager.renameTag("newBirthday", "birthday");
	}
}
