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
    private List<Boolean> uses = new ArrayList<>();
    private boolean found = false;

    public DiffQueryRunner(Map<String, String> originalAttrMap) {
        this.originalAttrMap = originalAttrMap;
    }

    public void run(String filename) throws Exception {
        diffFile = Utils.getFileByFilepath(filename);
        checkDiffFile();
    }

    private void checkDiffFile() {
        findSameElement();
        //not good solution but I have not time for another ;(
        findElementByAttributes(Arrays.asList("id","class","onclick","href"));
        findElementByAttributes(Arrays.asList("class","onclick","href"));
        findElementByAttributes(Arrays.asList("class","onclick"));
        findElementByAttributes(Arrays.asList("class","href"));
        findElementByAttributes(Arrays.asList("href","onclick"));
        findElementByAttributes(Arrays.asList("class"));
        findElementByAttributes(Arrays.asList("onclick"));
        findElementByAttributes(Arrays.asList("href"));
    }

    private void findElementByAttributes(List<String> attributes) {
        if (!found) {
            CssQueryGenerator cssQueryGenerator = new CssQueryGenerator();
            for (String attr : attributes) {
                cssQueryGenerator.hasContainsElement(attr, originalAttrMap.get(attr));
            }
            String cssQuery = cssQueryGenerator.build();
            Elements result = runQuery(cssQuery);
            if (result.size() > 0) {
                for (Element element : result) {
                    System.out.println(getElementPath(element));
                }
                found = true;
                return;
            }
        }
    }

    private void findSameElement() {
        if (!found) {
            CssQueryGenerator cssQueryGenerator = new CssQueryGenerator();
            for (Map.Entry<String, String> entry : originalAttrMap.entrySet()) {
                cssQueryGenerator.hasContainsElement(entry.getKey(), entry.getValue());
            }
            String cssQuery = cssQueryGenerator.build();
            Elements result = runQuery(cssQuery);
            if (result.size() > 0) {
                for (Element element : result) {
                    System.out.println(getElementPath(element));
                }
                found = true;
                return;
            }
        }
    }

    private String getElementPath(Element element) {
        Stack<String> elementPath = new Stack<>();
        while (!element.tag().getName().equals("html")) {
            elementPath.push(element.tag().getName() + "[" + element.attributes() + "]");
            element = element.parent();
        }
        elementPath.push("html");

        StringBuilder output = new StringBuilder();
        while (elementPath.size() > 0) {
            output.append(elementPath.pop() + " > ");
        }
        return output.substring(0, output.lastIndexOf(">") - 1);
    }

    private Elements runQuery(String query) {
        Elements elements = new JsoupCssSelectSnippet()
                .findElementsByQuery(diffFile, query).get();

        return elements;
    }

}
