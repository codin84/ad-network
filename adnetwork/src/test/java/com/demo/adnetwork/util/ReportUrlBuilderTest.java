package com.demo.adnetwork.util;

import com.demo.adnetwork.entity.AdNetworkSource;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ReportUrlBuilderTest
{

  @Test
  void buildUrl_SuperNetwork()
  {
    assertThat(ReportUrlBuilder.build(new AdNetworkSource(1L, "SuperNetwork", "https://storage.googleapis.com/expertise-test/supernetwork/report/daily/&dateParam.importer.csv", "uuuu-MM-dd"), LocalDate.of(2017, 10, 3))).isEqualTo("https://storage.googleapis.com/expertise-test/supernetwork/report/daily/2017-10-03.importer.csv");
  }

  @Test
  void buildUrl_AdUmbrella()
  {
    assertThat(ReportUrlBuilder.build(new AdNetworkSource(2L, "AdUmbrella", "https://storage.googleapis.com/expertise-test/reporting/adumbrella/adumbrella-&dateParam.importer.csv", "d_M_uuuu"), LocalDate.of(2017, 10, 3))).isEqualTo("https://storage.googleapis.com/expertise-test/reporting/adumbrella/adumbrella-3_10_2017.importer.csv");
  }
}
