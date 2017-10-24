package com.demo.adnetwork.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "daily_report")
public class DailyReport
{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String app;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private String requests;

    @Column(nullable = false)
    private String impressions;

    @Column(nullable = false)
    private String revenue;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne(optional = false)
    private DailyReportImportLog dailyReportImportLog;

    public DailyReport()
    {
    }

    public DailyReport(final LocalDate date, final String app, final String platform, final String requests, final String impressions, final String revenue, final Currency currency, final DailyReportImportLog dailyReportImportLog)
    {
        this.date = date;
        this.app = app;
        this.platform = platform;
        this.requests = requests;
        this.impressions = impressions;
        this.revenue = revenue;
        this.currency = currency;
        this.dailyReportImportLog = dailyReportImportLog;
        dailyReportImportLog.addDailyReport(this);
    }

    public Long getId()
    {
        return id;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public String getApp()
    {
        return app;
    }

    public String getPlatform()
    {
        return platform;
    }

    public String getRequests()
    {
        return requests;
    }

    public String getImpressions()
    {
        return impressions;
    }

    public String getRevenue()
    {
        return revenue;
    }

    public Currency getCurrency()
    {
        return currency;
    }

    public DailyReportImportLog getDailyReportImportLog()
    {
        return dailyReportImportLog;
    }
}