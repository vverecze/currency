package com.vverecze.service;

import antlr.build.ANTLR;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import com.vverecze.model.Currency;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Viki
 */
@Stateless
public class DOMParserService implements ParserInterface {
    private CurrencyServiceInterface service;
    
    @Inject
    public DOMParserService(CurrencyServiceInterface service) {
        this.service = service;
    }
    
    @Override
    public Node getNode(String tagName, NodeList nodes) {
        for (int x = 0; x < nodes.getLength(); x++) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                return node;
            }
        }
 
        return null;
    }
    
    @Override
    public String getNodeAttr(String attrName, Node node ) {
        NamedNodeMap attrs = node.getAttributes();
        
        for (int y = 0; y < attrs.getLength(); y++ ) {
            Node attr = attrs.item(y);
            if (attr.getNodeName().equalsIgnoreCase(attrName)) {
                return attr.getNodeValue();
            }
        }
        
        return "";
    }
    
    @Schedule(hour = "0", minute = "5", second = "0")
    @Override
    public void processCurrencyXml() {
        try {
            URL url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url.openStream());

            NodeList root = doc.getChildNodes();
            Node gesmesE = getNode("gesmes:Envelope", root);
            NodeList currencyDates = gesmesE.getLastChild().getChildNodes();
            LocalDate lastInDb = service.getLastDate();
            
            if(currencyDates != null) {
                int length = currencyDates.getLength();
                for(int i = 0; i < length; i++) {
                    if (currencyDates.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        String currencyDate = currencyDates.item(i).getAttributes().getNamedItem("time").getNodeValue();
                        LocalDate currDate = LocalDate.parse(currencyDate);
                        
                        if(currDate.compareTo(lastInDb) > 0) {
                            NodeList dataForDate = currencyDates.item(i).getChildNodes();
                        
                            if(dataForDate != null) {
                                int len = dataForDate.getLength();
                                for(int j = 0; j <len; j++) {
                                    if(dataForDate.item(j).getNodeType() == Node.ELEMENT_NODE) {
                                        String currencyType = dataForDate.item(j).getAttributes().getNamedItem("currency").getNodeValue();
                                        String rateString = dataForDate.item(j).getAttributes().getNamedItem("rate").getNodeValue();
                                        Double rate = Double.parseDouble(rateString);
                                        Currency currency = new Currency();
                                        currency.setDate(currDate);
                                        currency.setCurrencyType(currencyType);
                                        currency.setRate(rate);
                                        service.createCurrency(currency);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getCurrencyTypeList() {
        String lastDate = service.getLastDate().toString();
        return service.getCurrencyTypeList(lastDate);
    }

}
