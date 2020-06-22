package com.jazva.challenge.repository;

import com.jazva.challenge.entity.LocationProduct;
import com.jazva.challenge.entity.LocationProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LocationProductRepository extends JpaRepository<LocationProduct, LocationProductKey> {

    List<LocationProduct> findByLocationProductKeyProductId(Long productId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE f_location_product SET qty = 0 WHERE f_location_product.product_id = ?1",
            nativeQuery = true)
    Integer resetQuantitiesByProductId(Long productId);
}