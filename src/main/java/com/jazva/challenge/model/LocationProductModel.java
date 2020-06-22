package com.jazva.challenge.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class LocationProductModel {
    @NotNull public Integer qty;

    @DecimalMin(value = "0.0")
    public BigDecimal price;
}
