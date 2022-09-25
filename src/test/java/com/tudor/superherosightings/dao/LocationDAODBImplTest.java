package com.tudor.superherosightings.dao;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tudor.superherosightings.dto.Location;
import com.tudor.superherosightings.dto.Organisation;
import com.tudor.superherosightings.dto.Sighting;
import com.tudor.superherosightings.dto.Superhero;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LocationDAODBImplTest {

    @Autowired
    SuperheroDAO superheroDAO;

    @Autowired
    OrganisationDAO organisationDAO;

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    SightingDAO sightingDAO;
    
    public LocationDAODBImplTest(){
    }
    
    @BeforeEach
    public void setUp(){
        List<Superhero> heroes = superheroDAO.getAllHeroes();
        for(Superhero superhero : heroes){
        	superheroDAO.deleteHeroById(superhero.getId());
        }

        List<Organisation> orgs = organisationDAO.getAllOrgs();
        for(Organisation org : orgs){
        	organisationDAO.deleteOrgById(org.getId());
        }

        List<Location> locations = locationDAO.getAllLocations();
        for(Location location : locations){
        	locationDAO.deleteLocationById(location.getId());
        }

        List<Sighting> sightings = sightingDAO.getAllSightings();
        for(Sighting sighting : sightings){
        	sightingDAO.deleteSightingById(sighting.getId());
        }
    }
    
    @Test
    public void testAddAndGetLocation() {
        Location location = new Location();
        location.setName("BigBen");
        location.setAddress("London");
        location.setLatitude(2344);
        location.setLongitude(2342);
        location = locationDAO.addLocation(location);

        Location fromDao = locationDAO.getLocationById(location.getId());
        assertEquals(location, fromDao);
    }
    
    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setName("BigBen");
        location.setAddress("London");
        location.setLatitude(2344);
        location.setLongitude(2342);
        location = locationDAO.addLocation(location);

        Location location2 = new Location();
        location2.setName("London Eye");
        location2.setAddress("London");
        location2.setLatitude(2344);
        location2.setLongitude(2342);
        location2 = locationDAO.addLocation(location2);

        List<Location> locations = locationDAO.getAllLocations();

        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }
    
    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("BigBen");
        location.setAddress("London");
        location.setLatitude(2344);
        location.setLongitude(2342);
        location = locationDAO.addLocation(location);

        Location fromDao = locationDAO.getLocationById(location.getId());
        assertEquals(location, fromDao);

        location.setName("London Eye");
        locationDAO.updateLocation(location);

        assertNotEquals(location, fromDao);

        fromDao = locationDAO.getLocationById(location.getId());

        assertEquals(location, fromDao);
    }
    
    @Test
    public void testDeleteLocationById() {
        Location location = new Location();
        location.setName("BigBen");
        location.setAddress("London");
        location.setLatitude(2344);
        location.setLongitude(2342);
        location = locationDAO.addLocation(location);

        Location fromDao = locationDAO.getLocationById(location.getId());
        assertEquals(location, fromDao);

        locationDAO.deleteLocationById(location.getId());

        fromDao = locationDAO.getLocationById(location.getId());
        assertNull(fromDao);
    }
}
