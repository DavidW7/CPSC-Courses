package ca.ubc.cs.cpsc210.test;

import ca.ubc.cs.cpsc210.photo.Photo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Basic tests for photo that do not involve tags, image loading or display.
 */
public class PhotoTest {
	
	Photo photo1;
	Date photo1AddedDate;

	@BeforeEach
	public void setUp() throws Exception {
		photo1 = new Photo("1");
		photo1AddedDate = new Date();
		photo1.setDateAdded(photo1AddedDate);
		photo1.setDescription("description");
	}
	
	@Test
	public void testName() {
		assertEquals("1", photo1.getName());
	}
	
	@Test
	public void testDate() {
		assertEquals(photo1AddedDate, photo1.getDateAdded());
	}
	
	@Test
	public void testDescription() {
		assertEquals("description", photo1.getDescription());
	}

}
