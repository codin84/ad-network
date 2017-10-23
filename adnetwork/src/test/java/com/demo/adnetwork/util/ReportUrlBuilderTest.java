package com.demo.adnetwork.util;

import com.demo.adnetwork.entity.AdNetworkSource;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ReportUrlBuilderTest {

    @Test
    void buildUrl_SuperNetwork() {
        Assert.assertEquals("https://storage.googleapis.com/expertise-test/supernetwork/report/daily/2017-10-03.importer.csv",
                ReportUrlBuilder.build(
                        new AdNetworkSource(
                                1L,
                                "SuperNetwork",
                                "https://storage.googleapis.com/expertise-test/supernetwork/report/daily/&dateParam.importer.csv",
                                "uuuu-MM-dd"),
                        LocalDate.of(2017, 10, 3)));
    }

    @Test
    void buildUrl_AdUmbrella() {
        Assert.assertEquals("https://storage.googleapis.com/expertise-test/reporting/adumbrella/adumbrella-3_10_2017.importer.csv",
                ReportUrlBuilder.build(
                        new AdNetworkSource(
                                2L,
                                "AdUmbrella",
                                "https://storage.googleapis.com/expertise-test/reporting/adumbrella/adumbrella-&dateParam.importer.csv",
                                "d_M_uuuu"),
                        LocalDate.of(2017, 10, 3)));
    }
}
