package com.jazva.challenge.service;

import com.jazva.challenge.entity.LocationProduct;
import com.jazva.challenge.entity.LocationProductKey;
import com.jazva.challenge.repository.LocationProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationProductService {
    @Autowired LocationProductRepository locationProductRepository;

    public Map<String, Integer> getQuantities(Long productId){
        return locationProductRepository.findByLocationProductKeyProductId(productId).stream()
                .collect(Collectors.toMap(locationProduct -> locationProduct.getLocation().getName(), LocationProduct::getQty));
    }

    @Transactional
    public LocationProduct save(LocationProduct locationProduct) {
        return locationProductRepository.save(locationProduct);
    }

    public Integer resetQuantities(Long productId) {
        return locationProductRepository.resetQuantitiesByProductId(productId);
    }

    public Integer getQuantity(Long productId) {
        return locationProductRepository.findByLocationProductKeyProductId(productId)
                .stream().mapToInt(LocationProduct::getQty).sum();
    }

    public Optional<LocationProduct> getLocationProduct(Long productId, Long locationId) {
        LocationProductKey locationProductKey = new LocationProductKey();
        locationProductKey.setLocationId(locationId);
        locationProductKey.setProductId(productId);
        return locationProductRepository.findById(locationProductKey);
    }

}
