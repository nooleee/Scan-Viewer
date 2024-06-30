package com.pacs.scanviewer.SCV.Consent.service;


import com.pacs.scanviewer.SCV.Consent.domain.Consent;
import com.pacs.scanviewer.SCV.Consent.domain.ConsentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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

}
