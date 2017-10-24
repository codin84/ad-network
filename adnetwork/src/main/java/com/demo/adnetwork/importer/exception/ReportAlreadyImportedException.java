package com.demo.adnetwork.importer.exception;

import java.time.LocalDate;

public class ReportAlreadyImportedException extends RuntimeException
{
  private String adNetwork;
  private LocalDate date;

  public ReportAlreadyImportedException(final String adNetwork, final LocalDate date)
  {
    super();
    this.adNetwork = adNetwork;
    this.date = date;
  }

  public String getAdNetwork()
  {
    return adNetwork;
  }

  public LocalDate getDate()
  {
    return date;
  }
}
