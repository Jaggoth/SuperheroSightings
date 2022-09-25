package com.tudor.superherosightings.dao;

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
public class OrganisationDAODBImplTest {

    @Autowired
    SuperheroDAO superheroDAO;

    @Autowired
    OrganisationDAO organisationDAO;

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    SightingDAO sightingDAO;
    
    public OrganisationDAODBImplTest(){
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
    public void testAddAndGetOrg() {
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

        Organisation fromDao = organisationDAO.getOrgById(org.getId());
        assertEquals(org, fromDao);
    }
    
    @Test
    public void testGetAllOrgs() {
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

        Organisation org2 = new Organisation();
        org2.setName("PayBack");
        org2.setAddress("Not New York?");
        org2.setMembers(heroes);
        org2 = organisationDAO.addOrg(org2);

        List<Organisation> orgs = organisationDAO.getAllOrgs();
        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(org));
        assertTrue(orgs.contains(org2));
    }
    
    @Test
    public void testUpdateOrg() {
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

        Organisation fromDao = organisationDAO.getOrgById(org.getId());
        assertEquals(org, fromDao);

        org.setName("New Test Org Name");
        Superhero hero2 = new Superhero();
        hero2.setName("A-Train");
        hero2.setPower("Super Speed");
        hero2 = superheroDAO.addHero(hero2);
        heroes.add(hero2);
        org.setMembers(heroes);

        organisationDAO.updateOrg(org);

        assertNotEquals(org, fromDao);

        fromDao = organisationDAO.getOrgById(org.getId());
        assertEquals(org, fromDao);
    }
    
    @Test
    public void testDeleteOrgById() {
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

        Organisation fromDao = organisationDAO.getOrgById(org.getId());
        assertEquals(org, fromDao);

        organisationDAO.deleteOrgById(org.getId());

        fromDao = organisationDAO.getOrgById(org.getId());
        assertNull(fromDao);
    }
    
    @Test
    public void testGetOrgsForSuperhero() {
        Superhero hero = new Superhero();
        hero.setName("Homelander");
        hero.setPower("Super duche");
        hero = superheroDAO.addHero(hero);

        Superhero hero2 = new Superhero();
        hero2.setName("A-Train");
        hero2.setPower("Super Speed");
        hero2 = superheroDAO.addHero(hero2);

        List<Superhero> heroes = new ArrayList<>();
        heroes.add(hero);
        heroes.add(hero2);

        List<Superhero> heroes2 = new ArrayList<>();
        heroes2.add(hero2);

        Organisation org = new Organisation();
        org.setName("The Seven");
        org.setAddress("New York?");
        org.setMembers(heroes);
        org = organisationDAO.addOrg(org);

        Organisation org2 = new Organisation();
        org2.setName("PayBack");
        org2.setAddress("Not New York?");
        org2.setMembers(heroes2);
        org2 = organisationDAO.addOrg(org2);

        Organisation org3 = new Organisation();
        org3.setName("idontevenknowanymore");
        org3.setAddress("Maybe New York?");
        org3.setMembers(heroes);
        org3 = organisationDAO.addOrg(org2);

        List<Organisation> orgs = organisationDAO.getOrgsForSuperhero(hero);
        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(org));
        assertFalse(orgs.contains(org2));
        assertTrue(orgs.contains(org3));
    }
}
