package com.demo.adnetwork.importer.csv.validator;

import com.google.common.base.Preconditions;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Arrays;

@Component
public class DefaultDailyReportRowValidatorStrategy implements DailyReportRowValidatorStrategy {

    public boolean isRowValid(@Nonnull final String[] values) {
        Preconditions.checkNotNull(values, "Values should not be null!");

        // if any column value is an empty string, row is not valid
        return Arrays.stream(values).noneMatch(StringUtils::isBlank);
    }
}
