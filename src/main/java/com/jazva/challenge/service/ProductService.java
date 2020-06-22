package com.jazva.challenge.service;

import com.jazva.challenge.entity.Product;
import com.jazva.challenge.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service public class ProductService {
    @Autowired ProductRepository productRepository;

    public Optional<Product> findOne(Long productId) {
        return productRepository.findById(productId);
    }
}
