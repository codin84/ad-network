package com.demo.adnetwork.repository;

import com.demo.adnetwork.entity.AdNetworkSource;
import com.demo.adnetwork.entity.DailyReportImportLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Stream;

@Component
public interface ReportImportLogRepository extends JpaRepository<DailyReportImportLog, Long> {
    Stream<DailyReportImportLog> findByAdNetworkSourceAndReportDate(AdNetworkSource adNetworkSource, LocalDate reportDate);
}