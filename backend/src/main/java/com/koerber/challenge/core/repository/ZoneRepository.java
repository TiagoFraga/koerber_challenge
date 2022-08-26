package com.koerber.challenge.core.repository;

import com.koerber.challenge.core.domain.zone.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Integer> {

}
