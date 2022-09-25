package com.tudor.superherosightings.dao;

import java.time.LocalDate;
import java.util.ArrayList;
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
public class SuperheroDAODBImplTest {
	
    @Autowired
    SuperheroDAO superheroDAO;

    @Autowired
    OrganisationDAO organisationDAO;

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    SightingDAO sightingDAO;
    
    public SuperheroDAODBImplTest(){
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
    public void testAddAndGetHero() {
        Superhero hero = new Superhero();
        hero.setName("Homelander");
        hero.setPower("Super duche");
        hero = superheroDAO.addHero(hero);

        Superhero fromDao = superheroDAO.getHeroById(hero.getId());
        assertEquals(hero, fromDao);
    }
    
    @Test
    public void testGetAllHeroes() {
        Superhero hero = new Superhero();
        hero.setName("Homelander");
        hero.setPower("Super duche");
        hero = superheroDAO.addHero(hero);

        Superhero hero2 = new Superhero();
        hero2.setName("A-Train");
        hero2.setPower("Super Speed");
        hero2 = superheroDAO.addHero(hero2);

        List<Superhero> heroes = superheroDAO.getAllHeroes();

        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(hero));
        assertTrue(heroes.contains(hero2));
    }
    
    @Test
    public void testUpdateHero() {
        Superhero hero = new Superhero();
        hero.setName("A-Train");
        hero.setPower("Super Speed");
        hero = superheroDAO.addHero(hero);

        Superhero fromDao = superheroDAO.getHeroById(hero.getId());
        assertEquals(hero, fromDao);

        hero.setName("B-Train");
        superheroDAO.updateHero(hero);

        assertNotEquals(hero, fromDao);

        fromDao = superheroDAO.getHeroById(hero.getId());

        assertEquals(hero, fromDao);
    }
    
    @Test
    public void testDeleteHeroById() {
        Superhero hero = new Superhero();
        hero.setName("Homelander");
        hero.setPower("Super duche");
        hero = superheroDAO.addHero(hero);
        List<Superhero> heroes = new ArrayList<>();
        heroes.add(hero);

        Organisation org = new Organisation();
        org.setName("The Seven");
        org.setAddress("New York?");
        org.setMembers(heroes);
        org = organisationDAO.addOrg(org);

        Location location = new Location();
        location.setName("The Tower");
        location.setAddress("New York?");
        location.setLatitude(7534);
        location.setLongitude(3874);
        location = locationDAO.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setSuperhero(hero);
        sighting.setLocation(location);
        sighting = sightingDAO.addSighting(sighting);

        Superhero fromDao = superheroDAO.getHeroById(hero.getId());
        assertEquals(hero, fromDao);

        superheroDAO.deleteHeroById(hero.getId());

        fromDao = superheroDAO.getHeroById(hero.getId());
        assertNull(fromDao);
    }
}
