package com.jazva.challenge.repository;

import com.jazva.challenge.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
