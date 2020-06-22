package com.jazva.challenge.service;

import com.jazva.challenge.entity.Location;
import com.jazva.challenge.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service public class LocationService {
    @Autowired LocationRepository locationRepository;

    public Optional<Location> getLocation(Long id) {
        return locationRepository.findById(id);
    }
}
