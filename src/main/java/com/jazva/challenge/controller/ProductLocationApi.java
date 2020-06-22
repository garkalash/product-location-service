package com.jazva.challenge.controller;

import com.jazva.challenge.entity.LocationProduct;
import com.jazva.challenge.model.LocationProductModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/public/products")
@Validated public interface ProductLocationApi {
    /**
     * @param productId - the id of product
     * @return the sum of quantities in all locations for product
     *
     * @throws com.jazva.challenge.exception.impl.EntityNotFoundException - in case of illegal product id
     */
    @GetMapping(value= "/{id}/sum")
    Integer getProductQuantity(@PathVariable("id") Long productId);


    /**
     * @param productId - the id of product
     * @return map as report about quantities in locations for product
     *
     * @throws com.jazva.challenge.exception.impl.EntityNotFoundException - in case of illegal product id
     */
    @GetMapping(value = "/{id}/quantities")
    Map<String, Integer> getQuantities(@PathVariable("id") Long productId);

    /**
     * @param productId - the id of product
     * @return false - if there isn't any instance to reset
     *         true - if resets any instance
     *
     * @throws com.jazva.challenge.exception.impl.EntityNotFoundException - in case of illegal product id
     */
    @GetMapping(value= "/{id}/reset")
    boolean reset(@PathVariable("id") Long productId);

    /**
     * Creates new one if doesn't exist otherwise updates the relation
     *
     * @param productId - the id of product
     * @param locationId - the id of location
     * @return map as report about quantities in locations for product
     *
     * @throws com.jazva.challenge.exception.impl.EntityNotFoundException - in case of illegal product or location id
     * @throws com.jazva.challenge.exception.impl.InvalidQuantityException - in case of quantity which makes the quantity
     * of product negative
     */
    @PutMapping(value = "/{productId}/locations/{locationId}")
    LocationProduct save(@Valid @RequestBody LocationProductModel locationProductModel,
                         @PathVariable("productId") Long productId,
                         @PathVariable("locationId") Long locationId);
}
