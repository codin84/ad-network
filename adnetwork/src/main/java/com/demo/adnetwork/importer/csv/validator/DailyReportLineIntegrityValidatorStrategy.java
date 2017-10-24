package com.demo.adnetwork.importer.csv.validator;

import javax.annotation.Nonnull;

public interface DailyReportLineIntegrityValidatorStrategy
{
    boolean isValid(@Nonnull final String[] values);
}
