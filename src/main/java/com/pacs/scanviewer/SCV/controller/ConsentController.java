package com.pacs.scanviewer.SCV.controller;


import com.pacs.scanviewer.SCV.domain.Consent;
import com.pacs.scanviewer.SCV.domain.ConsentDto;
import com.pacs.scanviewer.SCV.domain.User;
import com.pacs.scanviewer.SCV.service.ConsentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@RequiredArgsConstructor
@RequestMapping("/consent")
@Controller
public class ConsentController {
    private final ConsentService consentService;

    @GetMapping("/{studykey}")
    public ModelAndView getConsents(@PathVariable int studykey, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Optional<Consent> consent = consentService.findConsentByStudyKeyAndUserCode(studykey, user.getUserCode());
        System.out.println("study key: " + studykey);
        if (consent.isPresent()) {
            // 검색 결과가 있을 경우 다른 페이지로 리디렉션
            ModelAndView modelAndView = new ModelAndView("redirect:/worklist");
            modelAndView.addObject("consent", consent.get());
            return modelAndView;
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

        //스터디 뷰페이지로 이동
        ModelAndView modelAndView = new ModelAndView("redirect:/worklist");
        return modelAndView;
    }


}
