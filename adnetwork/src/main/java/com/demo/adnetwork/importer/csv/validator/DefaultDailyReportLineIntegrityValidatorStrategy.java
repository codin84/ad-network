package com.demo.adnetwork.importer.csv.validator;

import com.google.common.base.Preconditions;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Arrays;

@Component
public class DefaultDailyReportLineIntegrityValidatorStrategy implements DailyReportLineIntegrityValidatorStrategy
{

    /**
     * This is the most basic form of String data validation. Simple strategy to validate if a line in report is valid.
     * It checks if all elements in values array are non empty.
     *
     * @param values
     * @return true is all elements of array are not blank
     */
    public boolean isValid(@Nonnull final String[] values)
    {
        Preconditions.checkNotNull(values, "Values should not be null!");

        // if any column value is an empty string, row is not valid
        return Arrays.stream(values).noneMatch(StringUtils::isBlank);
    }
}
