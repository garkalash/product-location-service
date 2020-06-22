package com.jazva.challenge.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LocationProductKey implements Serializable {

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "product_id")
    private Long productId;

    public LocationProductKey(){}

    public LocationProductKey(Long locationId, Long productId) {
        this.locationId = locationId;
        this.productId = productId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationProductKey that = (LocationProductKey) o;
        return locationId.equals(that.locationId) &&
                productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, productId);
    }
}
