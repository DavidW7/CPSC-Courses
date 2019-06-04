package ca.ubc.cs.cpsc210.resourcefinder.parser;

import ca.ubc.cs.cpsc210.resourcefinder.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

// Handler for XML resource parsing
public class ResourceHandler extends DefaultHandler {
    private ResourceRegistry registry;
    private StringBuilder accumulator;

    private String name = null;
    private String address = null;
    private String lat = null;
    private String lon = null;
    private String webaddress = null;
    private String phone = null;
    private Set<Service> services = null;

    // EFFECTS: constructs resource handler for XML parser
    public ResourceHandler(ResourceRegistry registry) {
        this.registry = registry;
        accumulator = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        switch (qName.toLowerCase()) {
            case "services":
                services = new HashSet<>();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        String data = accumulator.toString().trim();   // trim leading and trailing whitespace
        accumulator.setLength(0);                      // clear accumulator


        switch (qName.toLowerCase()) {
            case "resources":
                if (registry.getResources().size() == 0) {
                    throw new SAXException();
                }
                break;
            case "resource":
                if (address == null || lat == null || lon == null || name == null || webaddress == null || phone == null || services == null || services.size() == 0) {
                    services = null;
                    name = null;
                    address = null;
                    lat = null;
                    lon = null;
                    webaddress = null;
                    phone = null;
                    break;
                }
                Double latCord;
                Double lonCord;
                try{
                    latCord = Double.parseDouble(lat);
                    lonCord = Double.parseDouble(lon);
                } catch (NumberFormatException e) {
                    throw new SAXException();
                }
                GeoPoint newGeoPoint = new GeoPoint(latCord, lonCord);
                URL url;
                try {
                    url = new URL(webaddress);
                } catch (MalformedURLException e) {
                    throw new SAXException();
                }
                ContactInfo newContactInfo = new ContactInfo(address, newGeoPoint, url, phone);
                Resource newResource = new Resource(name, newContactInfo);
                for (Service s : services) {
                    newResource.addService(s);
                }
                registry.addResource(newResource);
                services = null;
                name = null;
                address = null;
                lat = null;
                lon = null;
                webaddress = null;
                phone = null;

                break;
            case "name":
                name = data;
                break;
            case "address":
                address = data;
                break;
            case "lat":
                lat = data;
                break;
            case "lon":
                lon = data;
                break;
            case "webaddress":
                webaddress = data;
                break;
            case "phone":
                phone = data;
                break;
            case "service":
                if (data.toLowerCase().contains("legal")) {
                    services.add(Service.LEGAL);
                }
                if (data.toLowerCase().contains("food")) {
                    services.add(Service.FOOD);
                }
                if (data.toLowerCase().contains("senior")) {
                    services.add(Service.SENIOR);
                }
                if (data.toLowerCase().contains("shelter")) {
                    services.add(Service.SHELTER);
                }
                if (data.toLowerCase().contains("youth")) {
                    services.add(Service.YOUTH);
                }
                if (data.toLowerCase().contains("counselling")) {
                    services.add(Service.COUNSELLING);
                }
                break;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        accumulator.append(ch, start, length);
    }
}
