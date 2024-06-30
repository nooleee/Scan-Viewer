package com.pacs.scanviewer.SCV.Consent.controller;


import com.pacs.scanviewer.SCV.Consent.domain.Consent;
import com.pacs.scanviewer.SCV.Consent.domain.ConsentDto;
import com.pacs.scanviewer.SCV.StudyLog.domain.StudyLog;
import com.pacs.scanviewer.SCV.StudyLog.service.StudyLogService;
import com.pacs.scanviewer.SCV.User.domain.User;
import com.pacs.scanviewer.SCV.Consent.service.ConsentService;
import com.pacs.scanviewer.pacs.Image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RequestMapping("/consent")
@Controller
public class ConsentController {
    private final ConsentService consentService;
    private final ImageService imageService;
    private final StudyLogService studyLogService;

    @GetMapping("/{studykey}")
    public ModelAndView getConsents(@PathVariable int studykey, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Optional<Consent> consent = consentService.findConsentByStudyKeyAndUserCode(studykey, user.getUserCode());
        System.out.println("study key: " + studykey);
        if (consent.isPresent()) {
            // 검색 결과가 있을 경우 다른 페이지로 리디렉션
            // 주어진 studykey에 대한 모든 serieskey를 가져옵니다.
            long studykeyLong = (long) studykey;
            List<Long> seriesKeys = imageService.getSeriesKeysByStudyKey(studykeyLong);
            if (!seriesKeys.isEmpty()) {
                StudyLog studyLog = new StudyLog(studykey,user.getUserCode());
                studyLogService.createLog(studyLog);
                ModelAndView modelAndView = new ModelAndView("redirect:/images/" + studykey + "/" + seriesKeys.get(0));
                return modelAndView;
            } else {
                // 시리즈 키를 찾을 수 없는 경우를 처리합니다.
                ModelAndView modelAndView = new ModelAndView("error/noSeriesFound");
                return modelAndView;
            }
        } else {
            // 검색 결과가 없을 경우 작성 페이지로 리디렉션
            ModelAndView modelAndView = new ModelAndView("consent/consent");
            modelAndView.addObject("studyKey", studykey);
            return modelAndView;
        }
    }

    @PostMapping("/submit")
    public ModelAndView submitConsent(@ModelAttribute ConsentDto consentDto, HttpSession session) {
        Consent consent = new Consent(consentDto);
        System.out.println("studykey: " + consentDto.getStudyKey());
        consentService.createConsent(consent);

        return new ModelAndView("redirect:/consent/" + consentDto.getStudyKey());
    }



}
