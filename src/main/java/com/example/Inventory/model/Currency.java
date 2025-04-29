package com.example.Inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Currency {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String shortName;

    @NotBlank
    private String name;

    @NotBlank
    private String symbol;

    @NotNull
    private Double rate;

    public Currency(String shortName,String name, String symbol, Double rate){
        this.shortName=shortName;
        this.name=name;
        this.symbol=symbol;
        this.rate=rate;
    }

}
