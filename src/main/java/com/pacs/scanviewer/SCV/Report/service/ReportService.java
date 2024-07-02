package com.pacs.scanviewer.SCV.Report.service;

import com.pacs.scanviewer.SCV.Report.domain.Report;
import com.pacs.scanviewer.SCV.Report.domain.ReportId;
import com.pacs.scanviewer.SCV.Report.domain.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final ICDAPIclient icdAPIclient;

    public Report save(Report report) {
        return reportRepository.save(report);
    }

    public Report update(Report report) {
        return reportRepository.save(report);
    }

    public void delete(int studyKey, String userCode) {
        ReportId reportId = new ReportId();
        reportId.setStudyKey(studyKey);
        reportId.setUserCode(userCode);
        reportRepository.deleteById(reportId);
    }

    public Report getReportByStudyKey(int studyKey) {
        Report report = reportRepository.findByStudyKey(studyKey);
        if (report != null && report.getVideoReplay() == Report.VideoReplay.판독불가) {
            report.setContent("");  // 의사 소견 비우기
            report.setPatient("");  // 결론 비우기
        }
        return report;
//        return reportRepository.findByStudyKey(studyKey);
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
                String title = entity.getString("title").replaceAll("\\<.*?\\>", ""); // HTML 태그 제거
                results.add(title);
            }
            return results.toString();
        }
    }

}
