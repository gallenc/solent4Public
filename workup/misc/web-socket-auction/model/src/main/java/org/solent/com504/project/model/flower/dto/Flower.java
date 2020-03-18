/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.flower.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gallenc
 */
// swagger
@Schema(description = "Flower Object which contains details of flowers")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Flower {

    private String symbol;
    private String synonymSymbol;
    private String scientificNamewithAuthor;
    private String commonName;
    private String family;
    private String dataUrl; // https://plants.usda.gov/core/profile?symbol=ABGR4

    public Flower() {
    }

    public Flower(String symbol, String synonymSymbol, String scientificNamewithAuthor, String commonName, String family, String dataUrl) {
        this.symbol = symbol;
        this.synonymSymbol = synonymSymbol;
        this.scientificNamewithAuthor = scientificNamewithAuthor;
        this.commonName = commonName;
        this.family = family;
        this.dataUrl = dataUrl;
    }

    @Schema(description = "plants.usda.gov plant identification Symbol")
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Schema(description = "plants.usda.gov plant identification synonym Symbol")
    public String getSynonymSymbol() {
        return synonymSymbol;
    }

    public void setSynonymSymbol(String synonymSymbol) {
        this.synonymSymbol = synonymSymbol;
    }

    @Schema(description = "plants.usda.gov plant Scientific name with author")
    public String getScientificNamewithAuthor() {
        return scientificNamewithAuthor;
    }

    public void setScientificNamewithAuthor(String scientificNamewithAuthor) {
        this.scientificNamewithAuthor = scientificNamewithAuthor;
    }

    @Schema(description = "plants.usda.gov plant common name")
    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    @Schema(description = "plants.usda.gov plant family. May be NONE")
    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    @Schema(description = "plants.usda.gov url to access the detailed web page for plant")
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
