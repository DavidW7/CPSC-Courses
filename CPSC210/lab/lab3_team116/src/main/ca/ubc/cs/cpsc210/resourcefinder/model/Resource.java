package ca.ubc.cs.cpsc210.resourcefinder.model;

import java.util.*;

// Represents a resource in the social services sector
public class Resource {
    private String name;
    private ContactInfo contactInfo;
    private Set<Service> services;

    // EFFECTS: constructs resource with given name and contact information
    public Resource(String name, ContactInfo contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.services = new HashSet<Service>();
    }

    public String getName() {
        return this.name;
    }

    public ContactInfo getContactInfo() {
        return this.contactInfo;
    }

    // EFFECTS: returns true if this resource offers the given service
    public boolean offersService(Service service) {
        return services.contains(service);
    }

    // EFFECTS: returns true if this resource offers all services in the requestedServices set; false otherwise
    //          (returns true if given the empty set)
    public boolean offersAllServicesInSet(Set<Service> requestedServices) {
        if (requestedServices.size() == 0) {
            return true;
        } else {
            return services.containsAll(requestedServices);
        }
    }

    // EFFECTS: returns true if this resources offers any of the services in requestedServices set; false otherwise
    //          (returns false if given the empty set)
    public boolean offersAnyServicesInSet(Set<Service> requestedServices) {
        if (requestedServices.size() ==  0) {
            return false;
        }

        if (services.size() == 0) {
            return false;
        }

        for (Service s : services) {
            if (requestedServices.contains(s)) {
                return true;
            }
        }

        return false;
    }

    // MODIFIES: this
    // EFFECTS: adds service to this resource, if it's not already added
    public void addService(Service service) {
        if (!services.contains(service)) {
            services.add(service);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes service from this resource
    public void removeService(Service service) {
        if (services.contains(service)) {
            services.remove(service);
        }
    }

    // EFFECTS: returns services offered by this resource as unmodifiable set
    public Set<Service> getServices() {
        return services;
    }
}