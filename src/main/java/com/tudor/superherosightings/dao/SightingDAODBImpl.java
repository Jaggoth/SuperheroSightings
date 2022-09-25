package com.tudor.superherosightings.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tudor.superherosightings.dto.Location;
import com.tudor.superherosightings.dto.Sighting;
import com.tudor.superherosightings.dto.Superhero;

@Repository
@Transactional
public class SightingDAODBImpl implements SightingDAO {

    @Autowired
    JdbcTemplate jdbc;
	
	@Override
	public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(date, superId, locationId) VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                Date.valueOf(sighting.getDate()),
                sighting.getSuperhero().getId(),
                sighting.getLocation().getId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);

        return sighting;
	}
	
	@Override
	public Sighting getSightingById(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE id = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID,
                    new SightMapper(), id);
            sighting.setSuperhero(getHeroForSighting(sighting));
            sighting.setLocation(getLocationForSighting(sighting));
            return sighting;
        } catch(DataAccessException ex) {
            return null;
        }
	}

	@Override
	public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightMapper());

        for(Sighting sighting : sightings) {
            sighting.setSuperhero(getHeroForSighting(sighting));
            sighting.setLocation(getLocationForSighting(sighting));
        }

        return sightings;
	}

	@Override
	public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting "
                + "SET date = ?, superId = ?, locationId = ? WHERE id = ?";
        jdbc.update(UPDATE_SIGHTING,
                Date.valueOf(sighting.getDate()),
                sighting.getSuperhero().getId(),
                sighting.getLocation().getId(),
                sighting.getId());
	}

	@Override
	public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);
	}
	
    private Superhero getHeroForSighting(Sighting sighting) {
        final String SELECT_HERO_FOR_SIGHTING = "SELECT s.* FROM superhero s "
                + "JOIN sighting si ON s.id = si.superId WHERE si.id = ?";
        return jdbc.queryForObject(SELECT_HERO_FOR_SIGHTING, new SuperheroDAODBImpl.HeroMapper(),
                sighting.getId());
    }

    private Location getLocationForSighting(Sighting sighting) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l "
                + "JOIN sighting si ON l.id = si.locationId WHERE si.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationDAODBImpl.LocationMapper(),
                sighting.getId());
    }

    public static final class SightMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setDate(rs.getDate("date").toLocalDate());

            return sighting;
        }
    }
}
