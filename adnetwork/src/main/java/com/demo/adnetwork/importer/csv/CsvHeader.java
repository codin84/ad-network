package com.demo.adnetwork.importer.csv;

import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.List;

class CsvHeader
{
    private List<String> csvFields;

    CsvHeader(@Nonnull List<String> csvFields)
    {
        Assert.notEmpty(csvFields, "CsvFields must not be empty.");
        this.csvFields = csvFields;
    }

    List<String> getCsvFields()
    {
        return csvFields;
    }
}
