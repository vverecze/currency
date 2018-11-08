package com.vverecze.service;

import com.vverecze.model.Currency;
import com.vverecze.repository.CurrencyRepositoryInterface;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Viki
 */
@Stateless
public class CurrencyService implements CurrencyServiceInterface {
    @Inject
    CurrencyRepositoryInterface repository;

    @Override
    public List<Currency> getAllCurrency() {
        return repository.readAll();
    }

    @Override
    public List<Currency> getCurrenciesByType(String type) {
        return repository.readByType(type);
    }

    @Override
    public void createCurrency(Currency currency) {
        repository.create(currency);
    }

    @Override
    public LocalDate getLastDate() {
        return repository.getLastDate();
    }

    @Override
    public List<String> getCurrencyTypeList(String date) {
        return repository.getCurrencyTypeList(date);
    }

    @Override
    public Double getRateByType(String type) {
        System.out.println("logggg    " + type);
        return repository.getRateByType(type);
    }

}
