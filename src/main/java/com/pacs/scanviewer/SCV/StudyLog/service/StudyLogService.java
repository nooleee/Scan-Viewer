package com.pacs.scanviewer.SCV.StudyLog.service;

import com.pacs.scanviewer.SCV.StudyLog.domain.StudyLog;
import com.pacs.scanviewer.SCV.StudyLog.domain.StudyLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class StudyLogService {

    private final StudyLogRepository studyLogRepository;

    public StudyLog createLog(StudyLog studyLog) {
        return studyLogRepository.save(studyLog);
    }


    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void deleteLogsOlderThanThreeMonths() {
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minus(3, ChronoUnit.MONTHS);
        Timestamp threeMonthsAgoTimestamp = Timestamp.valueOf(threeMonthsAgo);
        studyLogRepository.deleteByRegDateBefore(threeMonthsAgoTimestamp);
    }


}
