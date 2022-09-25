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

import com.tudor.superherosightings.dto.Location;

@Repository
@Transactional
public class LocationDAODBImpl implements LocationDAO {
	
    @Autowired
    JdbcTemplate jdbc;

	@Override
	public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(name, description, address, latitude, longitude) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());

        return location;
	}
    
	@Override
	public Location getLocationById(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
	}

	@Override
	public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
	}

	@Override
	public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE location SET name = ?, description = ?, address = ?, latitude = ?, longitude = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId());
	}

	@Override
	public void deleteLocationById(int id) {
        final String DELETE_LOCATION = "DELETE FROM location WHERE id = ?";
        jdbc.update(DELETE_LOCATION, id);
	}

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            location.setAddress(rs.getString("address"));
            location.setLatitude(rs.getInt("latitude"));
            location.setLongitude(rs.getInt("longitude"));

            return location;
        }
    }
}
