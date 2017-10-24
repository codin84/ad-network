package com.demo.adnetwork.entity;

import com.demo.adnetwork.util.StringUtils;
import com.google.common.base.Preconditions;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class DailyReportImportLog
{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate reportDate;

    @ManyToOne(targetEntity = AdNetworkSource.class)
    private AdNetworkSource adNetworkSource;

    @Column(nullable = false)
    private String reportUrl;

    @Column(nullable = false)
    private LocalDate importDate;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<DailyReport> dailyReports = new HashSet<>();

    public DailyReportImportLog()
    {
    }

    public DailyReportImportLog(@Nonnull final String reportUrl, @Nonnull final AdNetworkSource adNetworkSource, @Nonnull final LocalDate reportDate, @Nonnull final LocalDate importDate)
    {
        Assert.notNull(importDate, "ImportDate must not be null!");

        this.reportUrl = StringUtils.checkNotBlank(reportUrl, "ReportUrl must not be blank!");
        this.adNetworkSource = Preconditions.checkNotNull(adNetworkSource, "AdNetworkSource must not be null!");
        this.reportDate = Preconditions.checkNotNull(reportDate, "Data must not be null!");
        this.importDate = Preconditions.checkNotNull(importDate, "ImportDate must not be null!");
    }

    public Long getId()
    {
        return id;
    }

    public String getReportUrl()
    {
        return reportUrl;
    }

    public LocalDate getImportDate()
    {
        return importDate;
    }

    public AdNetworkSource getAdNetworkSource()
    {
        return adNetworkSource;
    }

    public LocalDate getReportDate()
    {
        return reportDate;
    }

    public Set<DailyReport> getDailyReports()
    {
        return dailyReports;
    }

    public void addDailyReport(final DailyReport dailyReport)
    {
        dailyReports.add(dailyReport);
    }
}
