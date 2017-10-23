package com.demo.adnetwork.app;

import com.demo.adnetwork.importer.DailyReportImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*@SpringBootApplication(scanBasePackages = {"com.demo.adnetwork"})
@EnableJpaRepositories("com.demo.adnetwork")
@EntityScan(basePackages = {"com.demo.adnetwork"})*/
public class AdNetworkApplicationConsole implements CommandLineRunner {

    @Autowired
    private DailyReportImporter dailyReportImporter;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AdNetworkApplicationConsole.class, args);
    }

    //access command line arguments
    @Override
    public void run(String... args) throws Exception {
        Assert.isTrue(args.length == 2, "Two arguments expected: adNetwork, date in format d/M/yyyy!");
        dailyReportImporter.importReport(args[0], LocalDate.parse(args[1], DateTimeFormatter.ofPattern("d/M/yyyy")));
    }
}