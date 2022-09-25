package com.tudor.superherosightings.dao;

import java.util.List;

import com.tudor.superherosightings.dto.Superhero;

public interface SuperheroDAO {
    Superhero addHero(Superhero superhero);
    Superhero getHeroById(int id);
    List<Superhero> getAllHeroes();
    void updateHero(Superhero superhero);
    void deleteHeroById(int id);
}
