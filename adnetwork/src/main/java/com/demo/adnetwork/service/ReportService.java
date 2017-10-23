package com.demo.adnetwork.service;

import com.demo.adnetwork.entity.AdNetworkSource;
import com.demo.adnetwork.entity.DailyReport;
import com.demo.adnetwork.entity.DailyReportImportLog;
import com.demo.adnetwork.repository.AdNetworkSourceRepository;
import com.demo.adnetwork.repository.DailyReportRepository;
import com.demo.adnetwork.repository.ReportImportLogRepository;
import com.demo.adnetwork.util.StringUtils;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Component
public class ReportService {
    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private ReportImportLogRepository dailyReportImportLogRepository;

    @Autowired
    private AdNetworkSourceRepository adNetworkSourceRepository;

    @Transactional
    public AdNetworkSource findAdNetworkSource(@Nonnull final String name) {
        return adNetworkSourceRepository.findByName(StringUtils.checkNotBlank(name, "Name must not be blank!"));
    }

    @Transactional
    public List<AdNetworkSource> findAdNetworkSources() {
        return adNetworkSourceRepository.findAll();
    }

    @Transactional
    public void saveDailyReport(@Nonnull final DailyReport dailyReport) {
        Assert.notNull(dailyReport, "DailyReport must not be null!");

        dailyReportRepository.save(dailyReport);
    }

    @Transactional
    public Stream<DailyReport> findDailyReportsByApp(@Nonnull final String app) {
        Assert.notNull(app, "App must not be null!");

        return dailyReportRepository.findByApp(app);
    }

    @Transactional
    public void saveDailyReportImportLog(@Nonnull final DailyReportImportLog dailyReportImportLog) {
        Assert.notNull(dailyReportImportLog, "DailyReportImportLog must not be null!");

        dailyReportImportLogRepository.save(dailyReportImportLog);
    }

    @Transactional
    public boolean isReportAlreadyImported(@Nonnull final AdNetworkSource adNetworkSource, @Nonnull final LocalDate date) {
        Preconditions.checkNotNull(adNetworkSource, "AdNetworkSource must not be null!");
        Preconditions.checkNotNull(date, "Date must not be null!");

        return dailyReportImportLogRepository.findByAdNetworkSourceAndReportDate(adNetworkSource, date).count() != 0;
    }

}
