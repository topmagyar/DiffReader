package com.develop;

import com.develop.utils.JsoupFindByIdSnippet;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ElementFinder {

    private static Logger LOGGER = LoggerFactory.getLogger(ElementFinder.class);

    private String originalFileName;
    private String elementId;
    private String diffFileName;

    private File originalFile;
    private Map<String, String> attrMap;

    public ElementFinder(String[] args) {
        elementId = args[0];
        originalFileName = args[1];
        diffFileName = args[2];
        init();
    }

    private void init() {
        Optional<Element> originalElement = findOriginalElement();
        attrMap = findOriginalElementAttributes(originalElement);
        DiffQueryRunner diffQueryRunner = new DiffQueryRunner(attrMap);
        diffQueryRunner.run(diffFileName);
    }

    public Map<String, String> findOriginalElementAttributes(Optional<Element> originalElement) {
        return originalElement.map(element ->
                {
                    Map<String, String> attr = element
                            .attributes()
                            .asList()
                            .stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    return attr;
                }).get();
    }

    private Optional<Element> findOriginalElement() {
        originalFile = new File(originalFileName);
        return new JsoupFindByIdSnippet().findElementById(originalFile, elementId);
    }
}
