package com.example.Inventory.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Inventory.model.Currency;
import com.example.Inventory.repository.CurrencyRepository;
import com.example.Inventory.exception.CurrencyAlreadyExistsException;
import com.example.Inventory.exception.CurrencyNotFoundException;
import com.example.Inventory.exception.CurrencyValueCanNotBeLessThanZeroException;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    // Agregar una nueva Currency validando unicidad y valores
    public Currency addCurrency(Currency currency) {
        this.currencyRepository.findByName(currency.getName())
            .ifPresent(existingCurrency -> {
                throw new CurrencyAlreadyExistsException("Currency already exists");
            });

        validateCurrencyFields(currency);

        return this.currencyRepository.save(currency);
    }

    // Listar todas las Currency
    public List<Currency> listCurrency() {
        return this.currencyRepository.findAll();
    }

    // Buscar una Currency por su nombre
    public Currency findCurrencyByName(String name) {
        return this.currencyRepository.findByName(name)
            .orElseThrow(() -> new CurrencyNotFoundException("Currency not found"));
    }

    // Actualizar una Currency
    public Currency updateCurrency(Long id, Currency currency) {
        Currency existingCurrency = this.currencyRepository.findById(id)
            .orElseThrow(() -> new CurrencyNotFoundException("Currency not found"));

        validateCurrencyFields(currency);

        existingCurrency.setShortName(currency.getShortName());
        existingCurrency.setName(currency.getName());
        existingCurrency.setSymbol(currency.getSymbol());
        existingCurrency.setRate(currency.getRate());

        return this.currencyRepository.save(existingCurrency);
    }

    // Eliminar una Currency
    public void deleteCurrency(Long id) {
        this.currencyRepository.findById(id)
            .orElseThrow(() -> new CurrencyNotFoundException("Currency not found"));
        this.currencyRepository.deleteById(id);
    }

    // Validar que el valor de la Currency no sea negativo
    private void validateCurrencyFields(Currency currency) {
        if (currency.getRate() < 0) {
            throw new CurrencyValueCanNotBeLessThanZeroException("The value of the currency can not be less than zero!");
        }
    }
}