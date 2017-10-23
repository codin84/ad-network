package com.demo.adnetwork.importer.csv.validator;

import javax.annotation.Nonnull;

public interface DailyReportRowValidatorStrategy {
    boolean isRowValid(@Nonnull final String[] values);
}
