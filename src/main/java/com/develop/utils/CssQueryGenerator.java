package com.develop;

public class CssQueryGenerator {

    private String classAttribute;
    private String onClickAttirbute;
    private String hrefAttribute;

    public CssQueryGenerator hasClassAttributeContains(String classAttribute) {
        this.classAttribute = classAttribute;
        return this;
    }

    public CssQueryGenerator hasHrefAttributeContains(String hrefAttribute) {
        this.hrefAttribute = hrefAttribute;
        return this;
    }

    public CssQueryGenerator hasOnClickAttirbuteStarted(String onClickAttirbute) {
        this.onClickAttirbute = onClickAttirbute;
        return this;
    }

    private String getClassAttributeQuery() {
        return classAttribute == null ? "" : "[class*=\"" + classAttribute + "\"]";
    }

    private String getOnClickAttirbuteQuery() {
        return onClickAttirbute == null ? "" : "[onclick^=\"" + onClickAttirbute + "\"]";
    }

    private String getHrefAttributeQuery() {
        return hrefAttribute == null ? "" : "[href*=\"" + hrefAttribute + "\"]";
    }

    public String build() {
        StringBuilder resultQuery = new StringBuilder();
        resultQuery.append("a");
        resultQuery.append(getClassAttributeQuery());
        resultQuery.append(getHrefAttributeQuery());
        return resultQuery.toString();
    }
}
