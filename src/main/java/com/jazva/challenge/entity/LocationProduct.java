package com.jazva.challenge.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "f_location_product")
public class LocationProduct {

    @EmbeddedId
    private LocationProductKey locationProductKey;

    @ManyToOne
    @MapsId("location_id")
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer qty;
    private BigDecimal price;

    public LocationProductKey getLocationProductKey() {
        return locationProductKey;
    }

    public void setLocationProductKey(LocationProductKey locationProductKey) {
        this.locationProductKey = locationProductKey;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
