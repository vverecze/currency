package com.vverecze.service;

import com.vverecze.model.Currency;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Viki
 */
public interface CurrencyServiceInterface {
    List<Currency> getAllCurrency();
    List<Currency> getCurrenciesByType(String type);
    void createCurrency(Currency currency);
    LocalDate getLastDate();
    List<String> getCurrencyTypeList(String date);
    Double getRateByType(String type);
}
