package org.example.petwif.service.albumService;


import lombok.RequiredArgsConstructor;
import org.example.petwif.apiPayload.code.status.ErrorStatus;
import org.example.petwif.apiPayload.exception.GeneralException;
import org.example.petwif.domain.entity.Album;
import org.example.petwif.domain.entity.AlbumReport;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.repository.AlbumRepository;
import org.example.petwif.repository.MemberRepository;
import org.example.petwif.repository.albumRepository.AlbumReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlbumReportServiceImpl implements AlbumReportService{
    private final AlbumRepository albumRepository;
    private final AlbumReportRepository albumReportRepository;
    private final MemberRepository memberRepository;

    public void doReport(Long albumId, Long memberId, String reason){
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ALBUM_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        AlbumReport albumReport = AlbumReport.builder()
                .album(album)
                .member(member)
                .reason(reason).build();
        albumReportRepository.save(albumReport);
    }

}
