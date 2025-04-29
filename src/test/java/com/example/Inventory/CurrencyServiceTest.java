package com.example.Inventory.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import com.example.Inventory.exception.CurrencyAlreadyExistsException;
import com.example.Inventory.exception.CurrencyNotFoundException;
import com.example.Inventory.model.Currency;
import com.example.Inventory.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCurrency_Success() {
        Currency currency = new Currency("USD", "Dollar", "$", 1.0);

        when(currencyRepository.findByName("Dollar")).thenReturn(Optional.empty());
        when(currencyRepository.save(currency)).thenReturn(currency);

        Currency result = currencyService.addCurrency(currency);

        assertEquals("Dollar", result.getName());
        verify(currencyRepository, times(1)).save(currency);
    }

    @Test
    void addCurrency_AlreadyExists_ThrowsException() {
        Currency currency = new Currency("USD", "Dollar", "$", 1.0);

        when(currencyRepository.findByName("Dollar")).thenReturn(Optional.of(currency));

        assertThrows(CurrencyAlreadyExistsException.class, () -> {
            currencyService.addCurrency(currency);
        });
    }

    @Test
    void findCurrencyByName_Success() {
        Currency currency = new Currency("USD", "Dollar", "$", 1.0);

        when(currencyRepository.findByName("Dollar")).thenReturn(Optional.of(currency));

        Currency result = currencyService.findCurrencyByName("Dollar");

        assertEquals("Dollar", result.getName());
    }

    @Test
    void findCurrencyByName_NotFound_ThrowsException() {
        when(currencyRepository.findByName("Euro")).thenReturn(Optional.empty());

        assertThrows(CurrencyNotFoundException.class, () -> {
            currencyService.findCurrencyByName("Euro");
        });
    }

    @Test
    void listCurrency_ReturnsList() {
        Currency currency = new Currency("USD", "Dollar", "$", 1.0);

        when(currencyRepository.findAll()).thenReturn(Collections.singletonList(currency));

        List<Currency> result = currencyService.listCurrency();

        assertEquals(1, result.size());
        assertEquals("Dollar", result.get(0).getName());
    }
}