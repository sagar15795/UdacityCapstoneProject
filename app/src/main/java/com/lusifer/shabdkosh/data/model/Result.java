package com.lusifer.shabdkosh.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("definition")
    private String definition;
    @SerializedName("derivation")
    private List<String> derivation;
    @SerializedName("examples")
    private List<String> examples;
    @SerializedName("hasTypes")
    private List<String> hasTypes;
    @SerializedName("partOfSpeech")
    private String partOfSpeech;
    @SerializedName("synonyms")
    private List<String> synonyms;
    @SerializedName("typeOf")
    private List<String> typeOf;

    public String getDefinition() {
        return definition;
    }

    public List<String> getDerivation() {
        return derivation;
    }

    public List<String> getExamples() {
        return examples;
    }

    public List<String> getHasTypes() {
        return hasTypes;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public List<String> getTypeOf() {
        return typeOf;
    }
}
