package com.demo.adnetwork.importer.csv;

import com.demo.adnetwork.entity.Currency;
import com.demo.adnetwork.util.StringUtils;
import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.time.LocalDate;

public class DailyReportData
{
  private final LocalDate date;
  private final String app;
  private final String platform;
  private final String requests;
  private final String impressions;
  private final String revenue;
  private final Currency currency;

  public DailyReportData(@Nonnull final LocalDate date, @Nonnull final String app, @Nonnull final String platform, @Nonnull final String requests, @Nonnull final String impressions, @Nonnull final String revenue, @Nonnull final Currency currency)
  {
    this.date = Preconditions.checkNotNull(date, "Date must not be null");
    this.app = StringUtils.checkNotBlank(app, "App must not be null");
    this.platform = StringUtils.checkNotBlank(platform, "Platform must not be null");
    this.requests = StringUtils.checkNotBlank(requests, "Requests must not be null");
    this.impressions = StringUtils.checkNotBlank(impressions, "Impressions must not be null");
    this.revenue = StringUtils.checkNotBlank(revenue, "Revenue must not be null");
    this.currency = Preconditions.checkNotNull(currency, "Currency must not be null");
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
}