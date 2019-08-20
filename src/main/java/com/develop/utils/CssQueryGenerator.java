package com.develop.utils;

import java.util.ArrayList;
import java.util.List;

public class CssQueryGenerator {

    private List<String> params;

    private static final String containsElement = "=";
    private static final String startWithElement = "^=";
    private static final String endWithElement = "$=";


    public CssQueryGenerator() {
        params = new ArrayList();
    }

    public CssQueryGenerator hasContainsElement(String attributeName, String attributeValue) {
        params.add(createQueryForElement(attributeName, attributeValue, containsElement));
        return this;
    }

    public CssQueryGenerator hasElementStartWith(String attributeName, String attributeValue) {
        params.add(createQueryForElement(attributeName, attributeValue, startWithElement));
        return this;
    }

    public CssQueryGenerator hasElementEndWith(String attributeName, String attributeValue) {
        params.add(createQueryForElement(attributeName, attributeValue, endWithElement));
        return this;
    }

    private String createQueryForElement(String attrName, String attrValue, String attrOption) {
        return "[" +
                attrName +
                attrOption +
                "\"" + attrValue + "\"]";
    }

    public String build() {
        StringBuilder resultQuery = new StringBuilder();
        resultQuery.append("a");
        for (String s : params) {
            resultQuery.append(s);
        }
        return resultQuery.toString();
    }
}
