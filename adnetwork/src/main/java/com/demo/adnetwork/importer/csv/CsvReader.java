package com.demo.adnetwork.importer.csv;

import com.demo.adnetwork.importer.exception.UrlResourceIoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
public class CsvReader {

    @Autowired
    private CsvParser csvParser;

    public List<DailyReportData> parse(@Nonnull String csvUrl) {
        Assert.hasText(csvUrl, "CsvUrl must not be null!");

        try {
            return csvParser.parseCsvInputStream(new URL(csvUrl));
        } catch (IOException exception) {
            throw new UrlResourceIoException(csvUrl, exception);
        }
    }
}