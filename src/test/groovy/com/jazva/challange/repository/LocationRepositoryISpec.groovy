package com.jazva.challange.repository

import com.jazva.challange.AppIntegrationTest
import com.jazva.challenge.repository.LocationRepository
import org.springframework.beans.factory.annotation.Autowired

class LocationRepositoryISpec extends AppIntegrationTest {
    @Autowired LocationRepository locationRepository

    def "list locations"() {
        when:
        def locations = locationRepository.findAll()

        then:
        locations.size() == 2
    }
}
