package com.pacs.scanviewer.pacs.Search.controller;

import com.pacs.scanviewer.pacs.Search.domain.SearchRequestDTO;
import com.pacs.scanviewer.pacs.Search.domain.SearchResponseDTO;
import com.pacs.scanviewer.pacs.Search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @CrossOrigin
    @GetMapping("/studies")
    @ResponseBody
    public Page<SearchResponseDTO> searchStudies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            SearchRequestDTO searchDTO) {
        return searchService.searchStudies(searchDTO, PageRequest.of(page, size));
    }
}
