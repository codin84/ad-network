package com.demo.adnetwork.repository;

import com.demo.adnetwork.entity.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public interface DailyReportRepository extends JpaRepository<DailyReport, Long>
{

  Stream<DailyReport> findByApp(String app);
}