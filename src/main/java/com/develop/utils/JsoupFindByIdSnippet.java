package com.develop.utils;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class JsoupFindByIdSnippet {

    private static String CHARSET_NAME = "utf8";

    public Optional<Element> findElementById(File htmlFile, String targetElementId) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(doc.getElementById(targetElementId));

        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
