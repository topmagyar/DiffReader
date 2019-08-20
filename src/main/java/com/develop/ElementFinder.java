package com.develop;

import com.develop.utils.JsoupCssSelectSnippet;
import com.develop.utils.JsoupFindByIdSnippet;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ElementFinder {

    private String originalFileName;
    private String elementId;
    private String diffFileName;

    private File originalFile;
    private File diffFile;

    private Map<String, String> attrMap;

    public ElementFinder(String[] args) {
        elementId = args[0];
        originalFileName = args[1];
        diffFileName = args[2];
    }

    public void findOriginalElementAttributes() throws FileNotFoundException {
        originalFile = getFileByFilepath(originalFileName);

        Optional<Element> originalElement = findOriginalElement();
        attrMap = originalElement.map(element ->
                {
                    Map<String, String> stringifiedAttrs = new HashMap<>();
                    stringifiedAttrs = element.attributes().asList().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    return stringifiedAttrs;
                }).get();

        diffFile = getFileByFilepath(diffFileName);
        int q = 1;
    }

    public void findDiffElementByAttributes() {
        String originalClassValue = attrMap.get("class");
        String cssQuery = new CssQueryGenerator()
                .hasClassAttributeContains(originalClassValue)
                .hasHrefAttributeContains(attrMap.get("href")+"1")
                .build();
        Optional<Elements> elements = new JsoupCssSelectSnippet().findElementsByQuery(diffFile, cssQuery);
        int q = 2;
    }

    public File getFileByFilepath(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        if (!file.exists()) {
            throw new FileNotFoundException("File " + filepath + " not found");
        }
        return file;
    }

    private Optional<Element> findOriginalElement() {
        return new JsoupFindByIdSnippet().findElementById(originalFile, elementId);
    }
}
