package com.pacs.scanviewer.SCV.Consent.service;


import com.pacs.scanviewer.SCV.Consent.domain.Consent;
import com.pacs.scanviewer.SCV.Consent.domain.ConsentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ConsentService {
    private final ConsentRepository consentRepository;

    public Optional<Consent> findConsentByStudyKeyAndUserCode(int studykey, String userCode) {
        return consentRepository.findByStudyKeyAndUserCode(studykey, userCode);
    }
    public Consent createConsent(Consent consent) {
        return consentRepository.save(consent);
    }


    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void deleteLogsOlderThanThreeMonths() {
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minus(3, ChronoUnit.MONTHS);
        Timestamp threeMonthsAgoTimestamp = Timestamp.valueOf(threeMonthsAgo);
        consentRepository.deleteByRegDateBefore(threeMonthsAgoTimestamp);
    }
}
