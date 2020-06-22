package com.jazva.challange.repository

import com.jazva.challange.AppIntegrationTest
import com.jazva.challenge.entity.LocationProduct
import com.jazva.challenge.entity.LocationProductKey
import com.jazva.challenge.repository.LocationProductRepository
import org.springframework.beans.factory.annotation.Autowired

class LocationProductRepositoryISpec extends AppIntegrationTest {
    @Autowired LocationProductRepository locationProductRepository

    def "save item"(){
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
        def savedRelation = locationProductRepository.save(locationItem)

        then:
        savedRelation.qty == 15
        savedRelation.price == 150.00

        cleanup:
        locationProductRepository.deleteById(relationKey)
    }

    def "retrieve by product id"() {
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
        locationProductRepository.save(locationItem)

        and:
        def saveRelations = locationProductRepository.findByLocationProductKeyProductId(productId)

        then:
        saveRelations.size() == 1
        saveRelations.get(0).qty == 15
        saveRelations.get(0).price == 150.00

        when:
        relationKey.setLocationId(2) //set to yerevan
        locationItem.setLocationProductKey(relationKey)
        locationItem.setPrice(160.00)
        locationItem.setQty(20)

        and:
        locationProductRepository.save(locationItem)

        and:
        saveRelations = locationProductRepository.findByLocationProductKeyProductId(1)

        then:
        saveRelations.size() == 2

        cleanup:
        saveRelations.forEach({location -> locationProductRepository.delete(location)})
    }

    def "reset quantities for product"() {
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
        locationProductRepository.save(locationItem)

        when:
        def count = locationProductRepository.resetQuantitiesByProductId(productId)

        and:
        def saveRelations = locationProductRepository.findByLocationProductKeyProductId(productId)

        then:
        count > 0
        saveRelations.size() == 1
        saveRelations.get(0).qty == 0
        saveRelations.get(0).price == 150.00

        cleanup:
        saveRelations.forEach({location -> locationProductRepository.delete(location)})
    }

}
