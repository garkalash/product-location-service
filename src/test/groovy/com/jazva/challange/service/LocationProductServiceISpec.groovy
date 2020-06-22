package com.jazva.challange.service

import com.jazva.challange.AppIntegrationTest
import com.jazva.challenge.entity.LocationProduct
import com.jazva.challenge.entity.LocationProductKey
import com.jazva.challenge.repository.LocationProductRepository
import com.jazva.challenge.service.LocationProductService
import org.springframework.beans.factory.annotation.Autowired

class LocationProductServiceISpec extends AppIntegrationTest {
    @Autowired LocationProductService locationProductService
    @Autowired LocationProductRepository locationProductRepository

    def "save relation"() {
        given:
        def productId = 1 // TV
        def locationId = 1 // Gyumri

        def locationItem = new LocationProduct()
        def relationKey = new LocationProductKey()
        relationKey.setLocationId(locationId)
        relationKey.setProductId(productId)
        locationItem.setLocationProductKey(relationKey)
        locationItem.setPrice(BigDecimal.valueOf(150.00))
        locationItem.setQty(15)

        when:
        def savedRelation = locationProductService.save(locationItem)

        then:
        savedRelation.locationProductKey.locationId == locationId
        savedRelation.locationProductKey.productId == productId
        savedRelation.qty == 15
        savedRelation.price == 150.00

        cleanup:
        locationProductRepository.deleteById(relationKey)
    }

    def "get relation by product and location"() {
        given:
        def productId = 1 // TV
        def locationId = 1 // Gyumri

        def locationItem = new LocationProduct()
        def relationKey = new LocationProductKey()
        relationKey.setLocationId(locationId)
        relationKey.setProductId(productId)
        locationItem.setLocationProductKey(relationKey)
        locationItem.setPrice(BigDecimal.valueOf(150.00))
        locationItem.setQty(15)

        and:
        locationProductService.save(locationItem)

        when:
        def savedRelation = locationProductService.getLocationProduct(productId, locationId)

        then:
        savedRelation.get().qty == 15
        savedRelation.get().price == 150.00

        cleanup:
        locationProductRepository.deleteById(relationKey)
    }

    def "product quantities"() {
        given:
        def tvId = 1
        def gyumriId = 1
        def yerevanId = 2

        def locationItem = new LocationProduct()
        def relationKey = new LocationProductKey()
        relationKey.setLocationId(gyumriId)
        relationKey.setProductId(tvId)
        locationItem.setLocationProductKey(relationKey)
        locationItem.setPrice(BigDecimal.valueOf(150.00))
        locationItem.setQty(15)

        and:
        locationProductService.save(locationItem)

        and:
        relationKey.setLocationId(yerevanId)
        locationItem.setQty(20)
        locationItem.setLocationProductKey(relationKey)
        locationProductService.save(locationItem)

        when:
        def quantities = locationProductService.getQuantities(tvId)

        then:
        quantities.size() == 2
        quantities.get("Gyumri") == 15
        quantities.get("Yerevan") == 20


        cleanup:
        locationProductRepository.findByLocationProductKeyProductId(tvId).forEach({item -> locationProductRepository.delete(item)})
    }

    def "get sum quantity for product"() {
        given:
        def tvId = 1
        def gyumriId = 1
        def yerevanId = 2

        def locationItem = new LocationProduct()
        def relationKey = new LocationProductKey()
        relationKey.setLocationId(gyumriId)
        relationKey.setProductId(tvId)
        locationItem.setLocationProductKey(relationKey)
        locationItem.setPrice(BigDecimal.valueOf(150.00))
        locationItem.setQty(15)

        and:
        locationProductService.save(locationItem)

        and:
        relationKey.setLocationId(yerevanId)
        locationItem.setQty(20)
        locationItem.setLocationProductKey(relationKey)
        locationProductService.save(locationItem)

        when:
        def quantity = locationProductService.getQuantity(tvId)

        then:
        quantity == 35

        cleanup:
        locationProductRepository.findByLocationProductKeyProductId(tvId).forEach({item -> locationProductRepository.delete(item)})

    }

}
