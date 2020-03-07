/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.dto;

/**
 *
 * @author gallenc
 */
public class Flower {

    private String symbol;
    private String synonymSymbol;
    private String scientificNamewithAuthor;
    private String commonName;
    private String family;
    private String dataUrl; // https://plants.usda.gov/core/profile?symbol=ABGR4

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
    
    

}
