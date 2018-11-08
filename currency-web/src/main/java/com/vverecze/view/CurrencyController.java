package com.vverecze.view;

import com.vverecze.model.Currency;
import com.vverecze.service.CurrencyServiceInterface;
import com.vverecze.service.DOMParserService;
import com.vverecze.service.ParserInterface;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Viki
 */
@SessionScoped
@Named(value = "currencyController")
public class CurrencyController implements Serializable {
    private ParserInterface parserService;
    private CurrencyServiceInterface currencyService;
    private List<Currency> currencies;
    private Currency currency;
    private Double inputNum;
    private String fromCurrency;
    private String toCurrency;
    private String currencyForHistory;

    public CurrencyController() {
    }
    
    @Inject
    public CurrencyController(ParserInterface parserService, CurrencyServiceInterface currencyService) {
        this.parserService = parserService;
        this.currencyService = currencyService;
        this.currencies = new ArrayList<>();
        this.currency = new Currency();
    }
    
    @PostConstruct
    public void readBasicData() {
        this.inputNum = 0.0;
        this.fromCurrency = "";
        this.toCurrency = "";
        this.currencyForHistory = "";
        parserService.processCurrencyXml();
        parserService.getCurrencyTypeList();
    }
    
    public String getCalculateCurrency() {
        if(fromCurrency.equals("") && toCurrency.equals("")) {
            return "";
        } else {
            Double from = currencyService.getRateByType(fromCurrency);
            Double to = currencyService.getRateByType(toCurrency);
            Double result = inputNum / from * to;
            return result.toString();
        }
    }

    public String getCurrencyHistory() {
        System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        if(currencyForHistory.equals("")) {
            return "";
        } else {
            List<Currency> currencies = currencyService.getCurrenciesByType(currencyForHistory);
            StringBuilder resultString = new StringBuilder();
            resultString.append("[");
            ZoneId zoneId = ZoneId.systemDefault();
            
            for(int i = 0; i < currencies.size(); i++) {
                Long epochtime = currencies.get(i).getDate().atStartOfDay(zoneId).toEpochSecond();
                resultString.append("{\"x\":").append(epochtime).append(",\"y\":").append(currencies.get(i).getRate()).append("},");
            }

            resultString.deleteCharAt(resultString.length()-1);
            resultString.append("]");
            System.out.println("uuuuuuuuuuuuu: "+resultString.toString());
            return resultString.toString();
            
        }
    }

    public ParserInterface getParserService() {
        return parserService;
    }

    public void setParserService(ParserInterface parserService) {
        this.parserService = parserService;
    }

    public CurrencyServiceInterface getCurrencyService() {
        return currencyService;
    }

    public void setCurrencyService(CurrencyServiceInterface currencyService) {
        this.currencyService = currencyService;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getInputNum() {
        return inputNum;
    }

    public void setInputNum(Double inputNum) {
        this.inputNum = inputNum;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public String getCurrencyForHistory() {
        return currencyForHistory;
    }

    public void setCurrencyForHistory(String currencyForHistory) {
        this.currencyForHistory = currencyForHistory;
    }  
    
}
