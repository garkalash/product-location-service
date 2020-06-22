package com.jazva.challange.controller

import com.jazva.challange.AppIntegrationTest
import com.jazva.challenge.controller.ProductLocationApi
import com.jazva.challenge.controller.impl.LocationProductController
import com.jazva.challenge.entity.LocationProductKey
import com.jazva.challenge.exception.impl.EntityNotFoundException
import com.jazva.challenge.exception.impl.InvalidQuantityException
import com.jazva.challenge.model.LocationProductModel
import com.jazva.challenge.repository.LocationProductRepository
import org.springframework.beans.factory.annotation.Autowired

import javax.validation.ConstraintViolationException

class LocationProductControllerISpec extends AppIntegrationTest {
    @Autowired ProductLocationApi locationProductController
    @Autowired LocationProductRepository locationProductRepository

    def "save"() {
        given:
        def gyumriId = 1
        def tvId = 1
        def yerevanId = 2
        def cellPhone = 2
        def tvsToGyumri = new LocationProductModel(qty: 5, price: 50.00);
        def cellPhonesToYerevan = new LocationProductModel(qty: 6, price: 20.00)

        when:
        locationProductController.save(tvsToGyumri, tvId, gyumriId)
        locationProductController.save(cellPhonesToYerevan, cellPhone, yerevanId)

        then:
        def savedTvsInGyumri = locationProductRepository.findByLocationProductKeyProductId(tvId).get(0);
        savedTvsInGyumri.qty == 5
        savedTvsInGyumri.price == 50.00
        savedTvsInGyumri.location.id == gyumriId

        and:
        def savedCellsInYerevan = locationProductRepository.findByLocationProductKeyProductId(cellPhone).get(0)
        savedCellsInYerevan.qty == 6
        savedCellsInYerevan.price == 20.00
        savedCellsInYerevan.location.id == yerevanId

        when: "we decrease quantity"
        tvsToGyumri.qty = -3

        and:
        locationProductController.save(tvsToGyumri, tvId, gyumriId)
        savedTvsInGyumri = locationProductRepository.findByLocationProductKeyProductId(tvId).get(0)

        then:
        savedTvsInGyumri.qty == 2

        cleanup:
        locationProductRepository.deleteById(new LocationProductKey(locationId: gyumriId, productId: tvId))
        locationProductRepository.deleteById(new LocationProductKey(locationId: yerevanId, productId: cellPhone))

    }

    def "save with invalid parameters"() {
        given:
        def invalidLocationId = 10
        def invalidProductId = 10
        def gyumriId = 1
        def tvId = 1
        def yerevanId = 2
        def cellPhone = 2
        def tvsToGyumri = new LocationProductModel(qty: 5, price: 50.00);
        def cellPhonesToYerevan = new LocationProductModel(qty: 6, price: 20.00)

        when:
        locationProductController.save(tvsToGyumri, invalidProductId, gyumriId)

        then:
        thrown(EntityNotFoundException)

        when:
        locationProductController.save(tvsToGyumri, tvId, invalidLocationId)

        then:
        thrown(EntityNotFoundException)

        when: "pass negative qty without already saved relation"
        tvsToGyumri.qty = -1

        and:
        locationProductController.save(tvsToGyumri, tvId, gyumriId)

        then:
        thrown(InvalidQuantityException)

        when: "passing invalid quantity when we have already saved relation"
        locationProductController.save(cellPhonesToYerevan, cellPhone, yerevanId)

        and:
        cellPhonesToYerevan.qty = 0 - cellPhonesToYerevan.qty - 1

        and:
        locationProductController.save(cellPhonesToYerevan, cellPhone, yerevanId)

        then:
        thrown(InvalidQuantityException)

        when:
        tvsToGyumri.qty = null

        and:
        locationProductController.save(tvsToGyumri, tvId, gyumriId)

        then:
        thrown(ConstraintViolationException)

        when:
        tvsToGyumri.qty = 5
        tvsToGyumri.price = -1.00

        and:
        locationProductController.save(tvsToGyumri, tvId, gyumriId)

        then:
        thrown(ConstraintViolationException)


        cleanup:
        locationProductRepository.deleteById(new LocationProductKey(locationId: yerevanId, productId: cellPhone))
    }

    def "product quantity"() {
        given:
        def invalidProductId = 10
        def playerId = 5
        def tvId = 1
        def gyumriId = 1
        def yerevanId = 2

        when:
        locationProductController.getProductQuantity(invalidProductId)

        then:
        thrown(EntityNotFoundException)

        when:
        def qty = locationProductController.getProductQuantity(playerId)

        then:
        qty == 0

        when:
        locationProductController.save(new LocationProductModel(qty: 5, price: 20.00), playerId, yerevanId)
        locationProductController.save(new LocationProductModel(qty: 6, price: 20.00), playerId, gyumriId)
        locationProductController.save(new LocationProductModel(qty: 6, price: 20.00), tvId, gyumriId)

        and:
        def playerQty = locationProductController.getProductQuantity(playerId)
        def tvQty = locationProductController.getProductQuantity(tvId)

        then:
        playerQty == 11
        tvQty == 6

        cleanup:
        locationProductRepository.deleteById(new LocationProductKey(locationId: yerevanId, productId: playerId))
        locationProductRepository.deleteById(new LocationProductKey(locationId: gyumriId, productId: playerId))
        locationProductRepository.deleteById(new LocationProductKey(locationId: gyumriId, productId: tvId))



    }

    def "quantities"() {
        given:
        def invalidProductId = 10
        def playerId = 5
        def tvId = 1
        def gyumriId = 1
        def yerevanId = 2

        when:
        locationProductController.getQuantities(invalidProductId)

        then:
        thrown(EntityNotFoundException)

        when:
        def players = locationProductController.getQuantities(playerId)

        then:
        players.isEmpty()

        when:
        locationProductController.save(new LocationProductModel(qty: 5, price: 20.00), playerId, yerevanId)
        locationProductController.save(new LocationProductModel(qty: 6, price: 20.00), playerId, gyumriId)
        locationProductController.save(new LocationProductModel(qty: 6, price: 20.00), tvId, gyumriId)

        and:
        players = locationProductController.getQuantities(playerId)
        def tvs = locationProductController.getQuantities(tvId)

        then:
        players.get('Gyumri') == 6
        players.get('Yerevan') == 5
        tvs.get('Gyumri') == 6

        cleanup:
        locationProductRepository.deleteById(new LocationProductKey(locationId: yerevanId, productId: playerId))
        locationProductRepository.deleteById(new LocationProductKey(locationId: gyumriId, productId: playerId))
        locationProductRepository.deleteById(new LocationProductKey(locationId: gyumriId, productId: tvId))
    }

    def "reset"() {
        given:
        def invalidProductId = 10
        def playerId = 5
        def tvId = 1
        def gyumriId = 1
        def yerevanId = 2

        when:
        locationProductController.reset(invalidProductId)

        then:
        thrown(EntityNotFoundException)

        when:
        def reset = locationProductController.reset(playerId)

        then:
        !reset

        when:
        locationProductController.save(new LocationProductModel(qty: 5, price: 20.00), playerId, yerevanId)
        locationProductController.save(new LocationProductModel(qty: 6, price: 20.00), playerId, gyumriId)
        locationProductController.save(new LocationProductModel(qty: 6, price: 20.00), tvId, gyumriId)

        and:
        def resetPlayers = locationProductController.reset(playerId)
        def resetTvs = locationProductController.reset(tvId)

        then:
        resetPlayers
        resetTvs
        locationProductController.getProductQuantity(playerId) == 0
        locationProductController.getProductQuantity(tvId) == 0


        cleanup:
        locationProductRepository.deleteById(new LocationProductKey(locationId: yerevanId, productId: playerId))
        locationProductRepository.deleteById(new LocationProductKey(locationId: gyumriId, productId: playerId))
        locationProductRepository.deleteById(new LocationProductKey(locationId: gyumriId, productId: tvId))
    }


}
