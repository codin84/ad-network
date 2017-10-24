package com.demo.adnetwork.service;

import com.demo.adnetwork.app.AdNetworkApplication;
import com.demo.adnetwork.entity.AdNetworkSource;
import com.demo.adnetwork.entity.Currency;
import com.demo.adnetwork.entity.DailyReport;
import com.demo.adnetwork.entity.DailyReportImportLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ComponentScan("com.demo.adnetwork.service")
@DataJpaTest
@ContextConfiguration(classes = AdNetworkApplication.class)
public class ReportServiceTest
{

  @Autowired
  private ReportService reportService;

  @Test
  public void saveDailyReportImportLog_SingleDailyReport()
  {
    final AdNetworkSource superNetwork = new AdNetworkSource(1L, "SuperNetwork", "https://storage.googleapis.com/expertise-test/supernetwork/report/daily/&dateParam.csv", "uuuu-MM-dd");
    final DailyReportImportLog dailyReportImportLog = new DailyReportImportLog("https://storage.googleapis.com/expertise-test/supernetwork/report/daily/2017-09-15.csv", superNetwork, LocalDate.of(2017, 1, 2), LocalDate.of(2017, 1, 3));
    new DailyReport(LocalDate.now(), "My Talking Tom", "iOS", "101", "10", "200", Currency.EUR, dailyReportImportLog);

    reportService.saveDailyReportImportLog(dailyReportImportLog);

    final List<DailyReport> reportList = reportService.findDailyReportsByApp("My Talking Tom").collect(Collectors.toList());

    assertThat(reportList.size()).isEqualTo(1);
    final DailyReport dailyReport = reportList.get(0);
    assertThat(dailyReport.getApp()).isEqualTo("My Talking Tom");
    final DailyReportImportLog dailyReportImportLogT = dailyReport.getDailyReportImportLog();
    assertThat(dailyReportImportLogT.getAdNetworkSource().getName()).isEqualTo("SuperNetwork");
    assertThat(dailyReportImportLogT.getReportUrl()).isEqualTo("https://storage.googleapis.com/expertise-test/supernetwork/report/daily/2017-09-15.csv");
    assertThat(dailyReportImportLogT.getDailyReports().size()).isEqualTo(1);
    assertThat(dailyReportImportLogT.getReportDate()).isEqualTo(LocalDate.of(2017, 1, 2));
    assertThat(dailyReportImportLogT.getImportDate()).isEqualTo(LocalDate.of(2017, 1, 3));
  }

  @Test
  public void saveDailyReportImportLog_MultipleDailyReports()
  {
    final AdNetworkSource superNetwork = new AdNetworkSource(1L, "SuperNetwork", "https://storage.googleapis.com/expertise-test/supernetwork/report/daily/&dateParam.csv", "uuuu-MM-dd");
    final DailyReportImportLog dailyReportImportLog = new DailyReportImportLog("https://storage.googleapis.com/expertise-test/supernetwork/report/daily/2017-09-15.csv", superNetwork, LocalDate.of(2017, 1, 2), LocalDate.of(2017, 1, 3));
    new DailyReport(LocalDate.now(), "My Talking Tom", "iOS", "100", "10", "200", Currency.EUR, dailyReportImportLog);
    new DailyReport(LocalDate.now(), "My Talking Tom", "iOS", "101", "10", "200", Currency.EUR, dailyReportImportLog);
    new DailyReport(LocalDate.now(), "My Talking Angela", "iOS", "101", "10", "200", Currency.EUR, dailyReportImportLog);
    reportService.saveDailyReportImportLog(dailyReportImportLog);

    final List<DailyReport> reportList = reportService.findDailyReportsByApp("My Talking Tom").collect(Collectors.toList());

    assertThat(reportList.size()).isEqualTo(2);
    DailyReportImportLog dailyReportImportLogT = reportList.get(0).getDailyReportImportLog();
    assertThat(dailyReportImportLogT.getDailyReports().size()).isEqualTo(3);
  }

  @Test
  public void isReportAlreadyImported()
  {
    final AdNetworkSource superNetwork = new AdNetworkSource(1L, "SuperNetwork", "https://storage.googleapis.com/expertise-test/supernetwork/report/daily/&dateParam.csv", "uuuu-MM-dd");
    final DailyReportImportLog dailyReportImportLog = new DailyReportImportLog("https://storage.googleapis.com/expertise-test/supernetwork/report/daily/2017-09-15.csv", superNetwork, LocalDate.of(2017, 1, 2), LocalDate.of(2017, 1, 3));
    new DailyReport(LocalDate.now(), "My Talking Tom", "iOS", "101", "10", "200", Currency.EUR, dailyReportImportLog);

    reportService.saveDailyReportImportLog(dailyReportImportLog);

    assertThat(reportService.isReportAlreadyImported(superNetwork, LocalDate.of(2017, 1, 2))).isTrue();
    assertThat(reportService.isReportAlreadyImported(superNetwork, LocalDate.of(2017, 1, 3))).isFalse();
  }
}