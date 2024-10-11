package org.trandy.trandy_server.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trandy.trandy_server.report.domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
