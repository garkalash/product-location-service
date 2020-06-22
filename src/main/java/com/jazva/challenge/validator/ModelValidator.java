package com.jazva.challenge.validator;

import com.jazva.challenge.entity.LocationProduct;
import com.jazva.challenge.exception.impl.InvalidQuantityException;
import com.jazva.challenge.service.LocationProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component public class ModelValidator {
    @Autowired LocationProductService locationProductService;

    public void validateQty(Long productId, Long locationId, Integer qty) throws InvalidQuantityException {
        Optional<LocationProduct> locationProductOptional =  locationProductService.getLocationProduct(productId, locationId);
        if(locationProductOptional.isPresent()){
            locationProductOptional.filter(locationProduct -> locationProduct.getQty() + qty < 0)
            .ifPresent(locationProduct -> { throw new InvalidQuantityException(productId, locationId, qty); });
        } else if(qty < 0) {
            throw new InvalidQuantityException(productId, locationId, qty);
        }
    }
}
