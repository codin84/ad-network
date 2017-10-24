package com.demo.adnetwork.importer.csv;


import com.demo.adnetwork.app.AdNetworkApplication;
import com.demo.adnetwork.entity.Currency;
import com.demo.adnetwork.importer.exception.MalformedReportException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@ComponentScan("com.demo.adnetwork.importer.csv")
@DataJpaTest
@ContextConfiguration(classes = AdNetworkApplication.class)
public class CsvParserTest
{

  @Autowired
  private CsvParser csvParser;

  @SuppressWarnings("ConstantConditions")
  @Test
  public void parseMalformed_3col() throws IOException
  {

    final Throwable exception = assertThrows(MalformedReportException.class, () -> csvParser.parseCsvInputStream(CsvParserTest.class.getClassLoader().getResource("malformed_3col.csv")));
    assertEquals("Report csv must consist of 6 columns!", exception.getMessage());
  }

  @SuppressWarnings("ConstantConditions")
  @Test
  public void parseMalformed_empty() throws IOException
  {

    Throwable exception = assertThrows(MalformedReportException.class, () -> csvParser.parseCsvInputStream(CsvParserTest.class.getClassLoader().getResource("malformed_empty.csv")));
    assertEquals("File must not be empty!", exception.getMessage());
  }

  @SuppressWarnings("ConstantConditions")
  @Test
  public void parseMalformed_wrongColName() throws IOException
  {

    Throwable exception = assertThrows(MalformedReportException.class, () -> csvParser.parseCsvInputStream(CsvParserTest.class.getClassLoader().getResource("malformed_wrongCols.csv")));
    assertEquals("Report csv header not found (IMPRESSIONS)!", exception.getMessage());
  }

  @SuppressWarnings("ConstantConditions")
  @Test
  public void parseMalformed_headerAndValueDifferInLength() throws IOException
  {

    Throwable exception = assertThrows(MalformedReportException.class, () -> csvParser.parseCsvInputStream(CsvParserTest.class.getClassLoader().getResource("malformed_headerValueSizeDiffer.csv")));
    assertEquals("Header and value differ in length!", exception.getMessage());
  }

  @SuppressWarnings("ConstantConditions")
  @Test
  public void parse_SuperNetwork_2017_09_15() throws IOException
  {

    final List<DailyReportData> dailyReportData = csvParser.parseCsvInputStream(CsvParserTest.class.getClassLoader().getResource("SuperNetwork_2017_09_15.csv"));
    Assert.isTrue(dailyReportData.stream().allMatch(dailyReport -> dailyReport.getCurrency() == Currency.EUR));

    Assertions.assertThat(dailyReportData.size()).isEqualTo(3);
  }

  @SuppressWarnings("ConstantConditions")
  @Test
  public void parse_AdUmbrella_15_9_2017() throws IOException
  {

    final List<DailyReportData> dailyReportData = csvParser.parseCsvInputStream(CsvParserTest.class.getClassLoader().getResource("AdUmbrella-15_9_2017.csv"));
    Assert.isTrue(dailyReportData.stream().allMatch(dailyReport -> dailyReport.getCurrency() == Currency.USD));
    Assertions.assertThat(dailyReportData.size()).isEqualTo(3);
  }
}
