package com.tudor.superherosightings.dao;

import java.util.List;

import com.tudor.superherosightings.dto.Organisation;
import com.tudor.superherosightings.dto.Superhero;

public interface OrganisationDAO {
    Organisation addOrg(Organisation organization);
    Organisation getOrgById(int id);
    List<Organisation> getAllOrgs();
    List<Organisation> getOrgsForSuperhero(Superhero superhero);
    void updateOrg(Organisation organization);
    void deleteOrgById(int id);

}
