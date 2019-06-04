package ca.ubc.cs.cpsc210.resourcefinder.tests;

import ca.ubc.cs.cpsc210.resourcefinder.model.Resource;
import ca.ubc.cs.cpsc210.resourcefinder.model.ResourceRegistry;
import ca.ubc.cs.cpsc210.resourcefinder.model.SelectionState;
import ca.ubc.cs.cpsc210.resourcefinder.model.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


// unit tests for SelectionState class
public class SelectionStateTest {
    private SelectionState testSelectionState;
    private ResourceRegistry registry;
    private Resource r1;
    private Resource r2;
    private Resource r3;
    private Resource r4;

    @BeforeEach
    public void runBefore() {
        registry = new ResourceRegistry();
        loadResources();

        testSelectionState = new SelectionState(registry);
    }

    @Test
    public void testNoServiceSelected() {
        Set<Service> testServices;

        testServices = new HashSet<>();
        testServices.add(Service.SHELTER);
        testServices.add(Service.YOUTH);

        testSelectionState.setSelectedServices(testServices);
        testSelectionState.selectService(Service.LEGAL);
        testSelectionState.selectService(Service.SENIOR);
        testSelectionState.deselectService(Service.SENIOR);
        testSelectionState.deselectService(Service.LEGAL);
        testSelectionState.deselectService(Service.SHELTER);
        testSelectionState.deselectService(Service.YOUTH);

        assertEquals(registry.getResources().size(), testSelectionState.getResourcesWithSelectedServices().size());
    }


    @Test
    public void testAnyServiceSelected() {
        testSelectionState.setSelectAll();
        testSelectionState.setSelectAny();

        runAny();
    }

    @Test
    public void testAllServiceSelected() {
        testSelectionState.setSelectAll();

        runAll();
    }

    // MODIFIES: this
    // EFFECTS:  adds services to resources and resources to resource registry
    private void loadResources() {
        r1 = new Resource("Res 1", null);
        r2 = new Resource("Res 2", null);
        r3 = new Resource("Res 3", null);
        r4 = new Resource("Res 4", null);

        r1.addService(Service.FOOD);
        r1.addService(Service.SHELTER);
        r2.addService(Service.YOUTH);
        r2.addService(Service.FOOD);
        r3.addService(Service.SENIOR);
        r3.addService(Service.COUNSELLING);
        r4.addService(Service.SHELTER);
        r4.addService(Service.FOOD);
        r4.addService(Service.LEGAL);

        registry.addResource(r1);
        registry.addResource(r2);
        registry.addResource(r3);
        registry.addResource(r4);
    }

    // MODIFIES: this
    // EFFECTS: run the tests as if selected service is any
    private void runAny() {
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

        testSelectionState.setSelectedServices(serviceWanted1);
        Set<Resource> resourceOffer1 = testSelectionState.getResourcesWithSelectedServices();
        assertEquals(4, resourceOffer1.size());
        assertTrue(resourceOffer1.contains(r1));
        assertTrue(resourceOffer1.contains(r2));
        assertTrue(resourceOffer1.contains(r3));
        assertTrue(resourceOffer1.contains(r4));

        testSelectionState.setSelectedServices(serviceWanted2);
        Set<Resource> resourceOffer2 = testSelectionState.getResourcesWithSelectedServices();
        assertEquals(3, resourceOffer2.size());
        assertTrue(resourceOffer2.contains(r1));
        assertTrue(resourceOffer2.contains(r2));
        assertTrue(resourceOffer2.contains(r4));

        testSelectionState.setSelectedServices(serviceWanted3);
        Set<Resource> resourceOffer3 = testSelectionState.getResourcesWithSelectedServices();
        assertEquals(3, resourceOffer3.size());
        assertTrue(resourceOffer3.contains(r1));
        assertTrue(resourceOffer3.contains(r2));
        assertTrue(resourceOffer3.contains(r4));
    }

    // MODIFIES: this
    // EFFECTS: run the tests as if selected service is all
    private void runAll() {
        Set<Service> serviceWanted1 = new HashSet<Service>();
        Set<Service> serviceWanted2 = new HashSet<Service>();
        Set<Service> serviceWanted3 = new HashSet<Service>();

        serviceWanted1.add(Service.FOOD);
        serviceWanted2.add(Service.FOOD);
        serviceWanted2.add(Service.SHELTER);
        serviceWanted3.add(Service.FOOD);
        serviceWanted3.add(Service.SHELTER);
        serviceWanted3.add(Service.LEGAL);

        testSelectionState.setSelectedServices(serviceWanted1);
        Set<Resource> resourceOffer1 = testSelectionState.getResourcesWithSelectedServices();
        assertEquals(3, resourceOffer1.size());
        assertTrue(resourceOffer1.contains(r1));
        assertTrue(resourceOffer1.contains(r2));
        assertTrue(resourceOffer1.contains(r4));

        testSelectionState.setSelectedServices(serviceWanted2);
        Set<Resource> resourceOffer2 = testSelectionState.getResourcesWithSelectedServices();
        assertEquals(2, resourceOffer2.size());
        assertTrue(resourceOffer2.contains(r1));
        assertTrue(resourceOffer2.contains(r4));

        testSelectionState.setSelectedServices(serviceWanted3);
        Set<Resource> resourceOffer3 = testSelectionState.getResourcesWithSelectedServices();
        assertEquals(1, resourceOffer3.size());
        assertTrue(resourceOffer3.contains(r4));
    }
}