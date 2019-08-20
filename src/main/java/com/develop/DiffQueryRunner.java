package com.develop;

import com.develop.utils.CssQueryGenerator;
import com.develop.utils.JsoupCssSelectSnippet;
import com.develop.utils.Utils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.*;

public class DiffQueryRunner {

    private Map<String, String> originalAttrMap;
    private File diffFile;
    private List<String> uses = new ArrayList<>();
    private List<String> attrNames;
    private Element resultElement;
    private int maxSameAttributesCount = 0;

    public DiffQueryRunner(Map<String, String> originalAttrMap) {
        this.originalAttrMap = originalAttrMap;
        attrNames = new ArrayList<>(originalAttrMap.keySet());
    }

    public void run(String filename) throws Exception {
        diffFile = Utils.getFileByFilepath(filename);
        checkDiffFile();
    }

    private void checkDiffFile() {
        binaryElementsUsage(0);
        if (resultElement == null) {
            System.out.println("Not found similar element");
        } else {
            System.out.println(getElementPath(resultElement));
        }
    }

    private void binaryElementsUsage(int pos) {
        if (pos == attrNames.size()) {
            findElementWithSelectedAttributes();
            return;
        }

        uses.add(attrNames.get(pos));
        binaryElementsUsage(pos + 1);
        uses.remove(uses.get(uses.size() - 1));
        binaryElementsUsage(pos + 1);
    }

    private void findElementWithSelectedAttributes() {
        CssQueryGenerator cssQueryGenerator = new CssQueryGenerator();
        for (String attr : uses) {
            cssQueryGenerator.hasContainsElement(attr, originalAttrMap.get(attr));
        }
        String cssQuery = cssQueryGenerator.build();
        Elements result = runQuery(cssQuery);
        if (result.size() > 0 && uses.size() > maxSameAttributesCount) {
            maxSameAttributesCount = uses.size();
            resultElement = result.first();
        }
    }

    private String getElementPath(Element element) {
        Stack<String> elementPath = new Stack<>();
        while (!element.tag().getName().equals("html")) {
            elementPath.push(element.tag().getName() + "[" + element.attributes() + "]");
            element = element.parent();
        }
        elementPath.push("html");

        StringBuilder outputBuilder = new StringBuilder();
        while (elementPath.size() > 0) {
            outputBuilder.append(elementPath.pop() + " > ");
        }
        return outputBuilder.substring(0, outputBuilder.lastIndexOf(">") - 1);
    }

    private Elements runQuery(String query) {
        Elements elements = new JsoupCssSelectSnippet()
                .findElementsByQuery(diffFile, query).get();

        return elements;
    }

}
