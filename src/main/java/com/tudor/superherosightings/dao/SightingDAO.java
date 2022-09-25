package com.tudor.superherosightings.dao;

import java.util.List;

import com.tudor.superherosightings.dto.Sighting;

public interface SightingDAO {
    Sighting addSighting(Sighting sighting);
    Sighting getSightingById(int id);
    List<Sighting> getAllSightings();
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
}
