package org.example.petwif.service.albumService;

import org.springframework.stereotype.Service;

public interface AlbumReportService {
    public void doReport(Long albumId, Long memberId, String reason);
}
