package com.develop;

import com.develop.utils.CssQueryGenerator;
import com.develop.utils.JsoupCssSelectSnippet;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiffQueryRunner {

    private Map<String, String> originalAttrMap;
    private File diffFile;
    private List<Boolean> uses = new ArrayList<>();
    private boolean found = false;

    public DiffQueryRunner(Map<String, String> originalAttrMap) {
        this.originalAttrMap = originalAttrMap;
    }

    public void run(String filename) {
        diffFile = new File(filename);
        checkDiffFile();
    }

    private void checkDiffFile() {
        findSameElement();
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
            List<Map<String, String>> result = runQuery(cssQuery);
            if (result.size() > 0) {
                for (Map<String, String> mp : result) {
                    System.out.println(mp.toString());
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
            List<Map<String, String>> result = runQuery(cssQuery);
            if (result.size() > 0) {
                for (Map<String, String> mp : result) {
                    System.out.println(mp.toString());
                }
                found = true;
                return;
            }
        }
    }

    public List<Map<String, String>> runQuery(String query) {
        return new JsoupCssSelectSnippet()
                .findElementsByQuery(diffFile, query)
                .map(elements -> {
                    List<Map<String, String>> result = new ArrayList<>();
                    elements.iterator().forEachRemaining(element -> {
                        Map<String, String> attr = element
                                .attributes()
                                .asList()
                                .stream()
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                        result.add(attr);
                    });
                    return result;
                }).get();
    }

}
