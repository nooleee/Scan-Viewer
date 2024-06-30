package com.pacs.scanviewer.SCV.Consent.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;


@Repository
public interface ConsentRepository  extends JpaRepository<Consent, Integer> {
    public void deleteByRegDateBefore(Timestamp timestamp);
    Optional<Consent> findByStudyKeyAndUserCode(int studykey, String userCode);
}
