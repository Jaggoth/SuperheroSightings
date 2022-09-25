package com.tudor.superherosightings.dao;

import java.time.LocalDate;
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
public class SightingDAODBImplTest {

    @Autowired
    SuperheroDAO superheroDAO;

    @Autowired
    OrganisationDAO organisationDAO;

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    SightingDAO sightingDAO;
    
    public SightingDAODBImplTest(){
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
    public void testAddAndGetSighting() {
        Superhero hero = new Superhero();
        hero.setName("Homelander");
        hero.setPower("Super duche");
        hero = superheroDAO.addHero(hero);

        Location location = new Location();
        location.setName("BigBen");
        location.setAddress("London");
        location.setLatitude(2344);
        location.setLongitude(2342);
        location = locationDAO.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setSuperhero(hero);
        sighting.setLocation(location);
        sighting = sightingDAO.addSighting(sighting);

        Sighting fromDao = sightingDAO.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }
    
    @Test
    public void testGetAllSightings() {
        Superhero hero = new Superhero();
        hero.setName("Homelander");
        hero.setPower("Super duche");
        hero = superheroDAO.addHero(hero);

        Superhero hero2 = new Superhero();
        hero2.setName("A-Train");
        hero2.setPower("Super Speed");
        hero = superheroDAO.addHero(hero);

        Location location = new Location();
        location.setName("BigBen");
        location.setAddress("London");
        location.setLatitude(2344);
        location.setLongitude(2342);
        location = locationDAO.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setSuperhero(hero);
        sighting.setLocation(location);
        sighting = sightingDAO.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.now());
        sighting2.setSuperhero(hero2);
        sighting2.setLocation(location);
        sighting2 = sightingDAO.addSighting(sighting2);

        List<Sighting> sightings = sightingDAO.getAllSightings();

        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }
    
    @Test
    public void testUpdateSighting() {
        Superhero hero = new Superhero();
        hero.setName("Homelander");
        hero.setPower("Super duche");
        hero = superheroDAO.addHero(hero);

        Location location = new Location();
        location.setName("BigBen");
        location.setAddress("London");
        location.setLatitude(2344);
        location.setLongitude(2342);
        location = locationDAO.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setSuperhero(hero);
        sighting.setLocation(location);
        sighting = sightingDAO.addSighting(sighting);

        Sighting fromDao = sightingDAO.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        sighting.setDate(LocalDate.of(2000,10,22));
        sightingDAO.updateSighting(sighting);

        assertNotEquals(sighting, fromDao);

        fromDao = sightingDAO.getSightingById(sighting.getId());

        assertEquals(sighting, fromDao);
    }
    
    @Test
    public void testDeleteSightingById() {
        Superhero hero = new Superhero();
        hero.setName("Homelander");
        hero.setPower("Super duche");
        hero = superheroDAO.addHero(hero);

        Location location = new Location();
        location.setName("BigBen");
        location.setAddress("London");
        location.setLatitude(2344);
        location.setLongitude(2342);
        location = locationDAO.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setSuperhero(hero);
        sighting.setLocation(location);
        sighting = sightingDAO.addSighting(sighting);

        Sighting fromDao = sightingDAO.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        sightingDAO.deleteSightingById(sighting.getId());

        fromDao = sightingDAO.getSightingById(sighting.getId());
        assertNull(fromDao);
    }
}
