package com.tudor.superherosightings.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tudor.superherosightings.dto.Superhero;

@Repository
@Transactional
public class SuperheroDAODBImpl implements SuperheroDAO {
	
    @Autowired
    JdbcTemplate jdbc;

	@Override
	public Superhero addHero(Superhero superhero) {
        final String INSERT_HERO = "INSERT INTO superhero(name, description, power) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getPower());

        return superhero;
	}

	@Override
	public Superhero getHeroById(int id) {
        try {
            final String SELECT_HERO_BY_ID = "SELECT * FROM superhero WHERE id = ?";
            return jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
	}

	@Override
	public List<Superhero> getAllHeroes() {
        final String SELECT_ALL_HEROES = "SELECT * FROM superhero";
        return jdbc.query(SELECT_ALL_HEROES, new HeroMapper());
	}

	@Override
	public void updateHero(Superhero superhero) {
        final String UPDATE_HERO = "UPDATE superhero SET name = ?, description = ?, power = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_HERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getPower(),
                superhero.getId());
	}

	@Override
	public void deleteHeroById(int id) {
        final String DELETE_SUPER_ORG = "DELETE FROM super_organization WHERE superId = ?";
        jdbc.update(DELETE_SUPER_ORG, id);

        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE superId = ?";
        jdbc.update(DELETE_SIGHTING, id);

        final String DELETE_HERO = "DELETE FROM superhero WHERE id = ?";
        jdbc.update(DELETE_HERO, id);
	}

    public static final class HeroMapper implements RowMapper<Superhero> {

        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero hero = new Superhero();
            hero.setId(rs.getInt("id"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            hero.setPower(rs.getString("power"));

            return hero;
        }
    }
}
