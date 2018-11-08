package com.vverecze.repository;

import com.vverecze.model.Currency;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Viki
 */
@Stateless
public class CurrencyRepository implements CurrencyRepositoryInterface {
    @PersistenceContext(name = "currency-PU")
    private EntityManager entityManager;

    @Override
    public void create(Currency entity) {
        entityManager.persist(entity);
    }

    @Override
    public List<Currency> readByType(String type) {
        final TypedQuery<Currency> findCurrenciesByType
                = entityManager.createQuery("SELECT c FROM Currency c WHERE c.currencyType = :type ORDER BY c.date ASC", Currency.class);
        findCurrenciesByType.setParameter("type", type);
        return findCurrenciesByType.getResultList();
    }

    @Override
    public List<Currency> readAll() {
        final TypedQuery<Currency> findAllUsers 
                = entityManager.createQuery("SELECT c FROM Currency c", Currency.class);
        return findAllUsers.getResultList();
    }

    @Override
    public LocalDate getLastDate() {
        final TypedQuery<LocalDate> findLastDateQuery
                = entityManager.createQuery("SELECT c.date FROM Currency c ORDER BY c.date DESC", LocalDate.class);
        if(findLastDateQuery.getResultList().isEmpty()) {
            LocalDate dummy = LocalDate.parse("1970-01-01");
            return dummy;
        } else {
            return findLastDateQuery.getResultList().get(0);
        }
    }

    @Override
    public List<String> getCurrencyTypeList(String date) {
        final TypedQuery<String> findTypeList
                = entityManager.createQuery("SELECT c.currencyType FROM Currency c WHERE c.date = :date", String.class);
        LocalDate dateloc = LocalDate.parse(date);
        findTypeList.setParameter("date", dateloc);
        return findTypeList.getResultList();
    }

    @Override
    public Double getRateByType(String type) {
        final TypedQuery<Double> findRate
                = entityManager.createQuery("SELECT c.rate FROM Currency c WHERE c.currencyType = :type ORDER BY c.date DESC", Double.class);
        findRate.setParameter("type", type);
        
        if(findRate.getResultList() != null) {
            return findRate.getResultList().get(0);
        } else {
            return 1.0;
        }
        
    }

}
