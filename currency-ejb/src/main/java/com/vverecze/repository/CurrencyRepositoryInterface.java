package com.vverecze.repository;

import com.vverecze.model.Currency;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Viki
 */
public interface CurrencyRepositoryInterface {
    void create(Currency entity);
    List<Currency> readByType(String type);
    List<Currency> readAll();
    LocalDate getLastDate();
    List<String> getCurrencyTypeList(String date);
    Double getRateByType(String type);
}
