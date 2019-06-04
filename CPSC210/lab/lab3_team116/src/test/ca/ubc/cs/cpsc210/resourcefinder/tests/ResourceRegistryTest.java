package ca.ubc.cs.cpsc210.resourcefinder.tests;

import ca.ubc.cs.cpsc210.resourcefinder.model.Resource;
import ca.ubc.cs.cpsc210.resourcefinder.model.ResourceRegistry;
import ca.ubc.cs.cpsc210.resourcefinder.model.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

// unit tests for ResourceRegistry class
public class ResourceRegistryTest {
    private ResourceRegistry testRegistry;
    private Resource r1;
    private Resource r2;
    private Resource r3;
    private Resource r4;

    @BeforeEach
    public void runBefore() {
        testRegistry = new ResourceRegistry();
        r1 = new Resource("Res 1", null);
        r2 = new Resource("Res 2", null);
        r3 = new Resource("Res 3", null);
        r4 = new Resource("Res 4", null);

        loadResources();
    }

    @Test
    public void testAddOneResource() {
        testRegistry = new ResourceRegistry();
        testRegistry.addResource(r1);
        List<Resource> resources = testRegistry.getResources();
        assertEquals(1, resources.size());
        assertTrue(resources.contains(r1));
    }

    @Test
    public void testAddZeroResource() {
        testRegistry = new ResourceRegistry();
        List<Resource> resources = testRegistry.getResources();
        assertEquals(0, resources.size());
    }

    @Test
    public void testAddMultipleResourceAndCorrectOrder() {
        testRegistry = new ResourceRegistry();
        testRegistry.addResource(r2);
        testRegistry.addResource(r1);
        testRegistry.addResource(r4);
        testRegistry.addResource(r3);
        List<Resource> resources = testRegistry.getResources();
        assertEquals(4, resources.size());
        assertEquals(r2, resources.get(0));
        assertEquals(r1, resources.get(1));
        assertEquals(r4, resources.get(2));
        assertEquals(r3, resources.get(3));
    }

    @Test
    public void testGetResourcesOfferingService() {
        Set<Resource> resourceOffer1 = testRegistry.getResourcesOfferingService(Service.FOOD);
        Set<Resource> resourceOffer2 = testRegistry.getResourcesOfferingService(Service.SENIOR);

        assertEquals(3, resourceOffer1.size());
        assertEquals(1, resourceOffer2.size());

        assertTrue(resourceOffer1.contains(r1));
        assertTrue(resourceOffer1.contains(r2));
        assertTrue(resourceOffer1.contains(r4));
        assertTrue(resourceOffer2.contains(r3));
    }

    @Test
    public void testGetResourcesOfferingAllServicesInSet() {
        Set<Service> serviceWanted1 = new HashSet<Service>();
        Set<Service> serviceWanted2 = new HashSet<Service>();
        Set<Service> serviceWanted3 = new HashSet<Service>();

        serviceWanted1.add(Service.FOOD);
        serviceWanted2.add(Service.FOOD);
        serviceWanted2.add(Service.SHELTER);
        serviceWanted3.add(Service.FOOD);
        serviceWanted3.add(Service.SHELTER);
        serviceWanted3.add(Service.LEGAL);

        Set<Resource> resourceOffer1 = testRegistry.getResourcesOfferingAllServicesInSet(serviceWanted1);
        Set<Resource> resourceOffer2 = testRegistry.getResourcesOfferingAllServicesInSet(serviceWanted2);
        Set<Resource> resourceOffer3 = testRegistry.getResourcesOfferingAllServicesInSet(serviceWanted3);

        assertEquals(3, resourceOffer1.size());
        assertEquals(2, resourceOffer2.size());
        assertEquals(1, resourceOffer3.size());

        assertTrue(resourceOffer1.contains(r1));
        assertTrue(resourceOffer1.contains(r2));
        assertTrue(resourceOffer1.contains(r4));
        assertTrue(resourceOffer2.contains(r1));
        assertTrue(resourceOffer2.contains(r4));
        assertTrue(resourceOffer3.contains(r4));
    }

    @Test
    public void testGetResourcesOfferingAnyServicesInSet() {
        Set<Service> serviceWanted1 = new HashSet<Service>();
        Set<Service> serviceWanted2 = new HashSet<Service>();
        Set<Service> serviceWanted3 = new HashSet<Service>();

        serviceWanted1.add(Service.FOOD);
        serviceWanted1.add(Service.SENIOR);
        serviceWanted2.add(Service.FOOD);
        serviceWanted2.add(Service.SHELTER);
        serviceWanted3.add(Service.FOOD);
        serviceWanted3.add(Service.SHELTER);
        serviceWanted3.add(Service.LEGAL);

        Set<Resource> resourceOffer1 = testRegistry.getResourcesOfferingAnyServicesInSet(serviceWanted1);
        Set<Resource> resourceOffer2 = testRegistry.getResourcesOfferingAnyServicesInSet(serviceWanted2);
        Set<Resource> resourceOffer3 = testRegistry.getResourcesOfferingAnyServicesInSet(serviceWanted3);

        assertEquals(4, resourceOffer1.size());
        assertEquals(3, resourceOffer2.size());
        assertEquals(3, resourceOffer3.size());

        assertTrue(resourceOffer1.contains(r1));
        assertTrue(resourceOffer1.contains(r2));
        assertTrue(resourceOffer1.contains(r3));
        assertTrue(resourceOffer1.contains(r4));
        assertTrue(resourceOffer2.contains(r1));
        assertTrue(resourceOffer2.contains(r2));
        assertTrue(resourceOffer2.contains(r4));
        assertTrue(resourceOffer3.contains(r1));
        assertTrue(resourceOffer3.contains(r2));
        assertTrue(resourceOffer3.contains(r4));
    }

    // MODIFIES: this
    // EFFECTS:  adds services to resources and resources to registry
    private void loadResources() {
        r1.addService(Service.FOOD);
        r1.addService(Service.SHELTER);
        r2.addService(Service.YOUTH);
        r2.addService(Service.FOOD);
        r3.addService(Service.SENIOR);
        r4.addService(Service.SHELTER);
        r4.addService(Service.FOOD);
        r4.addService(Service.LEGAL);

        testRegistry.addResource(r1);
        testRegistry.addResource(r2);
        testRegistry.addResource(r3);
        testRegistry.addResource(r4);
    }
}