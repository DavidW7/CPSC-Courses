package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.addressbook.Contact;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ContactTests {
	@Test
	public void testOneParamConstructor() {
		Contact c = new Contact("Joey");
		assertEquals("joey", c.getName());
	}
	
	@Test
	public void testTwoParamConstructor() {
		Contact c = new Contact("Joey", "joey@joey.com");
		assertEquals("Joey", c.getName());
		assertEquals("joey@joey.com", c.getEmailAddress());
	}
	
	@Test
	public void testGetAddressList() {
		Contact c = new Contact("Joey", "joey@joey.com");
		List<String> addressList = c.getAddressList();
		
		assertEquals(1, addressList.size());
	}
}
