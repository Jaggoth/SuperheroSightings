package com.tudor.superherosightings.dao;

import java.util.List;

import com.tudor.superherosightings.dto.Location;

public interface LocationDAO {
    Location addLocation(Location location);
    Location getLocationById(int id);
    List<Location> getAllLocations();
    void updateLocation(Location location);
    void deleteLocationById(int id);
}
