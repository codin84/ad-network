package com.demo.adnetwork.importer.csv;

import com.demo.adnetwork.importer.csv.validator.DailyReportLineIntegrityValidatorStrategy;
import com.demo.adnetwork.importer.exception.MalformedReportException;
import com.demo.adnetwork.util.RevenueValueParser;
import com.google.common.base.Preconditions;
import org.apache.logging.log4j.LogManager;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CsvParser
{
    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(CsvParser.class);

    @Autowired
    private DailyReportLineIntegrityValidatorStrategy dailyReportLineIntegrityValidatorStrategy;

    private final String COLUMN_SEPARATOR = ",";

    public List<DailyReportData> parseCsvInputStream(@Nonnull final URL csvFileUrl) throws IOException
    {
        Preconditions.checkNotNull(csvFileUrl, "CsvFileUrl must not be null!");

        try (final InputStreamReader inputStreamReader = new InputStreamReader(csvFileUrl.openConnection().getInputStream()); final BufferedReader reader = new BufferedReader(inputStreamReader))
        {
            final String header = reader.readLine();
            if (StringUtils.isBlank(header))
            {
                throw new MalformedReportException("File must not be empty!");
            }

            final CsvHeader headerFields = parseHeader(header);
            return reader.lines().map(line -> parseLine(line, headerFields)).filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    private DailyReportData parseLine(@Nonnull final String line, @Nonnull final CsvHeader header)
    {
        com.demo.adnetwork.util.StringUtils.checkNotBlank("line", "Line must not be empty!");
        Preconditions.checkNotNull(header, "Header must not be null!");

        final String[] values = Pattern.compile(COLUMN_SEPARATOR).split(line);
        if (header.getCsvFields().size() != values.length)
        {
            throw new MalformedReportException("Header and value differ in length!");
        }

        if (!dailyReportLineIntegrityValidatorStrategy.isValid(values))
        {
            LOG.error("Line is malformed. Skipping it (" + Arrays.toString(values) + ")!");
            return null;
        }

        final Function<String, String> value = csvHeaderField ->
        {

            final int i = header.getCsvFields().indexOf(csvHeaderField);
            if (i == -1)
            {
                throw new MalformedReportException("Report csv header not found (" + csvHeaderField + ")!");
            }
            return values[i];

        };

        final String revenueHeader = header.getCsvFields().stream().filter(headerString -> headerString.toUpperCase().contains(CsvField.REVENUE.name().toUpperCase())).findFirst().orElseThrow(() -> new IllegalArgumentException("Revenue header not found!"));
        final RevenueValueParser.RevenueValue revenueValue = RevenueValueParser.parse(value.apply(revenueHeader), revenueHeader);

        return new DailyReportData(LocalDate.parse(value.apply(CsvField.DATE.name()), DateTimeFormatter.ofPattern("d/M/yyyy")), value.apply(CsvField.APP.name()), value.apply(CsvField.PLATFORM.name()), value.apply(CsvField.REQUESTS.name()), value.apply(CsvField.IMPRESSIONS.name()), revenueValue.getRevenue(), revenueValue.getCurrency());
    }

    private CsvHeader parseHeader(@Nonnull final String header)
    {
        Assert.hasText(header, "Header must not be empty!");

        CsvHeader csvHeader = new CsvHeader(Pattern.compile(COLUMN_SEPARATOR).splitAsStream(header).map(String::toUpperCase).collect(Collectors.toList()));

        if (csvHeader.getCsvFields().size() != 6)
        {
            throw new MalformedReportException("Report csv must consist of 6 columns!");
        }

        return csvHeader;
    }
}
