package com.develop;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        ElementFinder elementFinder = new ElementFinder(args);
        elementFinder.findOriginalElementAttributes();
        elementFinder.findDiffElementByAttributes();
    }
}
