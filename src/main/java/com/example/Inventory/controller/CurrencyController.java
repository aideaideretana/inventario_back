package com.example.Inventory.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Inventory.DTO.CurrencyRequest;
import com.example.Inventory.DTO.CurrencyResponse;
import com.example.Inventory.DTO.Mapper;
import com.example.Inventory.service.CurrencyService;
import com.example.Inventory.model.Currency;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Currency", description = "Currency Catalog Management")
@RestController
@RequestMapping("/currency")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @Operation(summary = "Add Currency", description = "Register a currency with short name, name, symbol and value.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully registered"),
        @ApiResponse(responseCode = "409", description = "Currency already exists")
    })
    @PostMapping("/addCurrency")
    public ResponseEntity<?> addCurrency(@Valid @RequestBody CurrencyRequest currencyRequest) {
        try {
            Currency currency = this.currencyService.addCurrency(Mapper.toCurrency(currencyRequest));
            return new ResponseEntity<>(Mapper.toCurrencyResponse(currency), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(409).build();
        }
    }

    @Operation(summary = "Update Currency", description = "Update the details of a currency.")
    @PutMapping("/updateCurrency/{id}")
    public ResponseEntity<CurrencyResponse> updateCurrency(@PathVariable Long id, @RequestBody CurrencyRequest request) {
        Currency updatedCurrency = this.currencyService.updateCurrency(id, Mapper.toCurrency(request));
        return new ResponseEntity<>(Mapper.toCurrencyResponse(updatedCurrency), HttpStatus.OK);
    }

    @Operation(summary = "Get Currency by Name", description = "Retrieve a currency by its name.")
    @GetMapping("/getCurrencyByName/{name}")
    public ResponseEntity<CurrencyResponse> getCurrency(@PathVariable String name) {
        Currency currency = this.currencyService.findCurrencyByName(name);
        return new ResponseEntity<>(Mapper.toCurrencyResponse(currency), HttpStatus.OK);
    }

    @Operation(summary = "List all Currencies", description = "Retrieve all currencies.")
    @GetMapping("/listCurrency")
    public ResponseEntity<List<CurrencyResponse>> listCurrency() {
        List<CurrencyResponse> response = this.currencyService.listCurrency()
            .stream()
            .map(Mapper::toCurrencyResponse)
            .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete Currency", description = "Delete a currency by ID.")
    @DeleteMapping("/deleteCurrency/{id}")
    public ResponseEntity<?> deleteCurrency(@PathVariable Long id) {
        this.currencyService.deleteCurrency(id);
        return ResponseEntity.status(200).build();
    }
}