package com.cineverse.cineversebackend.user.report.service;

import com.cineverse.cineversebackend.user.report.dto.ReportDTO;
import com.cineverse.cineversebackend.user.report.entity.Report;

import java.util.List;

public interface ReportService {
    void registReport(Report report);

    Report processReportDeny(int boardReportId, ReportDTO reportDTO);

    Report processReportAccept(int boardReportId, ReportDTO reportDTO);

    ReportDTO findBoardReportById(int boardReportId);

    List<Report> findBoardReportList();
}
