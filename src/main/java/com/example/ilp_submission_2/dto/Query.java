package com.example.ilp_submission_2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Query {
    private String attribute;
    private String operator;
    private String value;

    @JsonCreator
    public Query(@JsonProperty ("attribute") String attribute, @JsonProperty ("operator") String operator,
                 @JsonProperty ("value") String value){
        this.attribute = attribute;
        this.operator = operator;
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }
}