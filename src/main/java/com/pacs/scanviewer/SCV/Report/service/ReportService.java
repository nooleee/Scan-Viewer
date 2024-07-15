package com.pacs.scanviewer.SCV.Report.service;

import com.pacs.scanviewer.SCV.Report.domain.Report;
import com.pacs.scanviewer.SCV.Report.domain.ReportRepository;
import com.pacs.scanviewer.SCV.ReportLog.domain.ReportLog;
import com.pacs.scanviewer.SCV.ReportLog.service.ReportLogService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final ICDAPIclient icdAPIclient;
    private final ReportLogService reportLogService; // 로그 서비스를 주입받습니다.

    public Report save(Report report) {
        Report savedReport = reportRepository.save(report);
        createReportLog(savedReport); // 로그 생성
        return savedReport;
    }

    public Report update(Report report) {
        Report updatedReport = reportRepository.save(report);
        createReportLog(updatedReport); // 로그 생성
        return updatedReport;
    }

    public Report getReportByStudyKey(int studyKey) {
        Report report = reportRepository.findByStudyKey(studyKey);
//        if (report != null && report.getVideoReplay() == Report.VideoReplay.판독취소) {
//            report.setContent("");  // 의사 소견 비우기
//            report.setPatient("");  // 결론 비우기
//        }
        return report;
    }

    public Optional<Report> findReportByStudyKey(int studyKey) {
        return reportRepository.findReportByStudyKey(studyKey);
    }

    public boolean checkIfStudyKeyExists(int studyKey) {
        return reportRepository.existsByStudyKey(studyKey);
    }

    private void createReportLog(Report report) {
        ReportLog reportLog = new ReportLog();
        reportLog.setStudyKey(report.getStudyKey());
        reportLog.setUserCode(report.getUserCode());
        reportLog.setContent(report.getContent());
        reportLog.setPatient(report.getPatient());
        reportLog.setDate(report.getDate());
        reportLog.setDiseaseCode(report.getDiseaseCode());
        reportLog.setVideoReplay(report.getVideoReplay());
        reportLogService.createLog(reportLog); // 로그를 저장합니다.
    }

    public String searchICDCode(String query) throws Exception {
        String token = icdAPIclient.getToken();
        String response = icdAPIclient.getICDCode(token, query);

        JSONObject jsonResponse = new JSONObject(response);
        JSONArray destinationEntities = jsonResponse.optJSONArray("destinationEntities");

        if (destinationEntities == null || destinationEntities.length() == 0) {
            return "검색 결과가 없습니다.";
        } else {
            List<String> results = new ArrayList<>();
            for (int i = 0; i < destinationEntities.length(); i++) {
                JSONObject entity = destinationEntities.getJSONObject(i);
                String title = entity.getString("title").replaceAll("\\<.*?\\>", "").replaceAll(",", "");
                String code = entity.getString("theCode");
                results.add(title + "|" + code);
            }
            return results.toString();
        }
    }
}
