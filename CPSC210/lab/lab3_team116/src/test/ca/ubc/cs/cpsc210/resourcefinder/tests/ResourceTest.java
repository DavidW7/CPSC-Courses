package ca.ubc.cs.cpsc210.resourcefinder.tests;

import ca.ubc.cs.cpsc210.resourcefinder.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

// unit tests for Resource class
public class ResourceTest {
    private Resource testResource;

    private Set<Service> allService = new HashSet<Service>();
    private Set<Service> someService = new HashSet<Service>();
    private Set<Service> noService = new HashSet<Service>();

    private String name = "Family Services";
    private ContactInfo contact = null;

    @BeforeEach
    public void runBefore() {
        testResource = new Resource(name, contact);
        allService.add(Service.SENIOR);
        allService.add(Service.FOOD);
        allService.add(Service.YOUTH);
        allService.add(Service.LEGAL);
        allService.add(Service.SHELTER);
        allService.add(Service.COUNSELLING);

        someService.add(Service.FOOD);
        someService.add(Service.YOUTH);
        someService.add(Service.LEGAL);
    }

    @Test
    public void testGetNameAndContact() {
        assertEquals(name, testResource.getName());
        assertEquals(contact, testResource.getContactInfo());
    }

    @Test
    public void testAddRemoveService() {
        testResource.addService(Service.FOOD);
        testResource.addService(Service.SHELTER);
        assertEquals(2, testResource.getServices().size());
        assertTrue(testResource.getServices().contains(Service.FOOD));
        assertTrue(testResource.getServices().contains(Service.SHELTER));
        testResource.removeService(Service.FOOD);
        assertEquals(1, testResource.getServices().size());
        assertFalse(testResource.getServices().contains(Service.FOOD));
    }

    @Test
    public void testRemoveNonExistResource() {
        testResource.addService(Service.SHELTER);
        testResource.removeService(Service.FOOD);
        assertEquals(1, testResource.getServices().size());
        assertTrue(testResource.getServices().contains(Service.SHELTER));
        assertFalse(testResource.getServices().contains(Service.FOOD));
    }

    @Test
    public void testAddDuplicate() {
        testResource.addService(Service.SHELTER);
        testResource.addService(Service.SHELTER);
        assertEquals(1, testResource.getServices().size());
        assertTrue(testResource.getServices().contains(Service.SHELTER));
    }

    @Test
    public void testHaveAllService() {
        testResource.addService(Service.SHELTER);
        testResource.addService(Service.FOOD);
        testResource.addService(Service.YOUTH);
        testResource.addService(Service.LEGAL);
        testResource.addService(Service.SENIOR);
        testResource.addService(Service.COUNSELLING);

        assertTrue(testResource.offersAllServicesInSet(allService));
        assertTrue(testResource.offersAnyServicesInSet(allService));

        assertTrue(testResource.offersAllServicesInSet(someService));
        assertTrue(testResource.offersAnyServicesInSet(someService));

        assertTrue(testResource.offersAllServicesInSet(noService));
        assertFalse(testResource.offersAnyServicesInSet(noService));

        assertTrue(testResource.offersService(Service.FOOD));
        assertTrue(testResource.offersService(Service.SHELTER));
        assertTrue(testResource.offersService(Service.YOUTH));
        assertTrue(testResource.offersService(Service.LEGAL));
        assertTrue(testResource.offersService(Service.SENIOR));
        assertTrue(testResource.offersService(Service.COUNSELLING));
    }

    @Test
    public void testHaveNoService() {
        assertFalse(testResource.offersAllServicesInSet(allService));
        assertFalse(testResource.offersAnyServicesInSet(allService));

        assertFalse(testResource.offersAllServicesInSet(someService));
        assertFalse(testResource.offersAnyServicesInSet(someService));

        assertTrue(testResource.offersAllServicesInSet(noService));
        assertFalse(testResource.offersAnyServicesInSet(noService));

        assertFalse(testResource.offersService(Service.FOOD));
        assertFalse(testResource.offersService(Service.SHELTER));
        assertFalse(testResource.offersService(Service.YOUTH));
        assertFalse(testResource.offersService(Service.LEGAL));
        assertFalse(testResource.offersService(Service.SENIOR));
        assertFalse(testResource.offersService(Service.COUNSELLING));
    }

    @Test
    public void testSomeService() {
        testResource.addService(Service.YOUTH);
        testResource.addService(Service.LEGAL);
        testResource.addService(Service.SENIOR);

        assertFalse(testResource.offersAllServicesInSet(allService));
        assertTrue(testResource.offersAnyServicesInSet(allService));

        assertFalse(testResource.offersAllServicesInSet(someService));
        assertTrue(testResource.offersAnyServicesInSet(someService));

        assertTrue(testResource.offersAllServicesInSet(noService));
        assertFalse(testResource.offersAnyServicesInSet(noService));

        assertFalse(testResource.offersService(Service.FOOD));
        assertFalse(testResource.offersService(Service.SHELTER));
        assertTrue(testResource.offersService(Service.YOUTH));
        assertTrue(testResource.offersService(Service.LEGAL));
        assertTrue(testResource.offersService(Service.SENIOR));
        assertFalse(testResource.offersService(Service.COUNSELLING));

        testResource.addService(Service.FOOD);

        assertFalse(testResource.offersAllServicesInSet(allService));
        assertTrue(testResource.offersAnyServicesInSet(allService));

        assertTrue(testResource.offersAllServicesInSet(someService));
        assertTrue(testResource.offersAnyServicesInSet(someService));

        assertTrue(testResource.offersAllServicesInSet(noService));
        assertFalse(testResource.offersAnyServicesInSet(noService));

        assertTrue(testResource.offersService(Service.FOOD));
        assertFalse(testResource.offersService(Service.SHELTER));
        assertTrue(testResource.offersService(Service.YOUTH));
        assertTrue(testResource.offersService(Service.LEGAL));
        assertTrue(testResource.offersService(Service.SENIOR));
        assertFalse(testResource.offersService(Service.COUNSELLING));

        testResource.removeService(Service.FOOD);
        testResource.removeService(Service.LEGAL);
        testResource.removeService(Service.YOUTH);

        assertFalse(testResource.offersAnyServicesInSet(someService));
    }

}