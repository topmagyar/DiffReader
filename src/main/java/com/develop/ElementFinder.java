package com.develop;

import com.develop.utils.JsoupFindByIdSnippet;
import com.develop.utils.Utils;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ElementFinder {

    private String originalFileName;
    private String elementId;
    private String diffFileName;

    private File originalFile;
    private Map<String, String> attrMap;

    public ElementFinder(String[] args) throws Exception {
        elementId = args[0];
        originalFileName = args[1];
        diffFileName = args[2];
        init();
    }

    private void init() throws Exception {
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

    private Optional<Element> findOriginalElement() throws Exception {
        originalFile = Utils.getFileByFilepath(originalFileName);
        return new JsoupFindByIdSnippet().findElementById(originalFile, elementId);
    }
}
