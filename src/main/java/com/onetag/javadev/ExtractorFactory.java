package com.onetag.javadev;


/**
 * Factory for Extractor generation
 */
public class ExtractorFactory {

    public static Extractor getExtractor() {
        return new CSVExtractor();
    }
}
