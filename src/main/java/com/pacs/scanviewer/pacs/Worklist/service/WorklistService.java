package com.pacs.scanviewer.pacs.Worklist.service;

import com.pacs.scanviewer.pacs.Study.domain.Study;
import com.pacs.scanviewer.pacs.Worklist.domain.WorklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WorklistService {
    // 페이징 처리
    private final WorklistRepository worklistRepository;

    public Page<Study> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return worklistRepository.findAll(pageable);
    }
}
