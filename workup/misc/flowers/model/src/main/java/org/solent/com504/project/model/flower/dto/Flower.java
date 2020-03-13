/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.flower.dto;

import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gallenc
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Flower {

    private String symbol;
    private String synonymSymbol;
    private String scientificNamewithAuthor;
    private String commonName;
    private String family;
    private String dataUrl; // https://plants.usda.gov/core/profile?symbol=ABGR4
    
    public Flower(){
    }

    public Flower(String symbol, String synonymSymbol, String scientificNamewithAuthor, String commonName, String family, String dataUrl) {
        this.symbol = symbol;
        this.synonymSymbol = synonymSymbol;
        this.scientificNamewithAuthor = scientificNamewithAuthor;
        this.commonName = commonName;
        this.family = family;
        this.dataUrl = dataUrl;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSynonymSymbol() {
        return synonymSymbol;
    }

    public void setSynonymSymbol(String synonymSymbol) {
        this.synonymSymbol = synonymSymbol;
    }

    public String getScientificNamewithAuthor() {
        return scientificNamewithAuthor;
    }

    public void setScientificNamewithAuthor(String scientificNamewithAuthor) {
        this.scientificNamewithAuthor = scientificNamewithAuthor;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    @Override
    public String toString() {
        return "Flower{" + "symbol=" + symbol + ", synonymSymbol=" + synonymSymbol + ", scientificNamewithAuthor=" + scientificNamewithAuthor + ", commonName=" + commonName + ", family=" + family + ", dataUrl=" + dataUrl + '}';
    }
 

}
