/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vverecze.service;

import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Viki
 */
public interface ParserInterface {
    Node getNode(String tagName, NodeList nodes);
    String getNodeAttr(String attrName, Node node );
    void processCurrencyXml();
    List<String> getCurrencyTypeList();
}
