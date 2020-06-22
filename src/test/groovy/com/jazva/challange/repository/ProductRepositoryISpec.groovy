package com.jazva.challange.repository

import com.jazva.challange.AppIntegrationTest
import com.jazva.challenge.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired

class ProductRepositoryISpec extends AppIntegrationTest{
    @Autowired ProductRepository productRepository

    def "list products"() {
        when:
        def products = productRepository.findAll()

        then:
        products.size() == 5
    }

}
