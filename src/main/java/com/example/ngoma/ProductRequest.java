package com.example.ngoma;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    @NotBlank
    private String name;
    private String description;

    @PositiveOrZero
    private double price;

    @PositiveOrZero
    private int stock;

    private String imageUrl;

    @NotNull
    private Long categoryId;
}
