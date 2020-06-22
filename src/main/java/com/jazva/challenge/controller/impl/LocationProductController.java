package com.jazva.challenge.controller.impl;


import com.jazva.challenge.controller.ProductLocationApi;
import com.jazva.challenge.entity.LocationProduct;
import com.jazva.challenge.entity.LocationProductKey;
import com.jazva.challenge.exception.impl.EntityNotFoundException;
import com.jazva.challenge.model.LocationProductModel;
import com.jazva.challenge.service.LocationProductService;
import com.jazva.challenge.service.LocationService;
import com.jazva.challenge.service.ProductService;
import com.jazva.challenge.validator.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

 @Service public class LocationProductController implements ProductLocationApi{
    @Autowired LocationProductService locationProductService;
    @Autowired ProductService productService;
    @Autowired LocationService locationService;

    @Autowired ModelValidator modelValidator;


    public Map<String, Integer> getQuantities( Long productId){
        productService.findOne(productId).orElseThrow(EntityNotFoundException::new);
        return locationProductService.getQuantities(productId);
    }


    public Integer getProductQuantity(Long productId) {
        productService.findOne(productId).orElseThrow(EntityNotFoundException::new);
        return locationProductService.getQuantity(productId);
    }


    public boolean reset( Long productId) {
        productService.findOne(productId).orElseThrow(EntityNotFoundException::new);
        return locationProductService.resetQuantities(productId) > 0;
    }


    public LocationProduct save(LocationProductModel locationProductModel, Long productId, Long locationId) {
        productService.findOne(productId).orElseThrow(EntityNotFoundException::new);
        locationService.getLocation(locationId).orElseThrow(EntityNotFoundException::new);
        modelValidator.validateQty(productId, locationId, locationProductModel.qty);
        LocationProduct locationProduct = new LocationProduct();
        Optional<LocationProduct> locationProductOpt = locationProductService.getLocationProduct(productId, locationId);
        if(locationProductOpt.isPresent()) {
            locationProduct.setQty(locationProductOpt.get().getQty() + locationProductModel.qty);
        } else {
            locationProduct.setQty(locationProductModel.qty);
        }

        if(locationProductModel.price != null) {
            locationProduct.setPrice(locationProductModel.price.setScale(2, RoundingMode.HALF_EVEN));
        }

        LocationProductKey locationProductKey = new LocationProductKey();
        locationProductKey.setProductId(productId);
        locationProductKey.setLocationId(locationId);
        locationProduct.setLocationProductKey(locationProductKey);

        return locationProductService.save(locationProduct);

    }
}
