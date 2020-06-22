package com.jazva.challenge.repository;

import com.jazva.challenge.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
