package com.demo.adnetwork.importer;

import com.demo.adnetwork.entity.AdNetworkSource;
import com.demo.adnetwork.entity.DailyReport;
import com.demo.adnetwork.entity.DailyReportImportLog;
import com.demo.adnetwork.importer.csv.CsvReader;
import com.demo.adnetwork.importer.exception.ReportAlreadyImportedException;
import com.demo.adnetwork.service.ReportService;
import com.demo.adnetwork.util.ReportUrlBuilder;
import com.demo.adnetwork.util.StringUtils;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.LocalDate;

@Component
public class DailyReportImporter {

    @Autowired
    private CsvReader csvReader;

    @Autowired
    private ReportService reportService;

    public void importReport(@Nonnull final String adNetworkSourceName, @Nonnull final LocalDate reportDate) {
        StringUtils.checkNotBlank(adNetworkSourceName, "AdNetworkSourceName must not be blank.");
        Preconditions.checkNotNull(reportDate, "Date must not be null.");

        final AdNetworkSource adNetworkSource = reportService.findAdNetworkSource(adNetworkSourceName);

        final String reportUrl = ReportUrlBuilder.build(adNetworkSource, reportDate);
        if (reportService.isReportAlreadyImported(adNetworkSource, reportDate)) {
            throw new ReportAlreadyImportedException(adNetworkSource.getName(), reportDate);
        }

        csvReader.parse(reportUrl).stream()
                .map(rowData -> new DailyReport(rowData.getDate(),
                        rowData.getApp(),
                        rowData.getPlatform(),
                        rowData.getRequests(),
                        rowData.getImpressions(),
                        rowData.getRevenue(),
                        rowData.getCurrency()))
                .forEach(dailyreport ->
                {
                    reportService.saveDailyReport(dailyreport);
                });

        reportService.saveDailyReportImportLog(new DailyReportImportLog(reportUrl, adNetworkSource, reportDate, LocalDate.now()));
    }
}
