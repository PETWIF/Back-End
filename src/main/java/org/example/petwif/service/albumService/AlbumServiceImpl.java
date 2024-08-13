package org.example.petwif.service.albumService;


import lombok.RequiredArgsConstructor;
import org.example.petwif.albumConverter.AlbumConverter;
import org.example.petwif.apiPayload.code.status.ErrorStatus;
import org.example.petwif.apiPayload.exception.GeneralException;
import org.example.petwif.apiPayload.exception.handler.AlbumHandler;
import org.example.petwif.apiPayload.exception.handler.BlockHandler;
import org.example.petwif.domain.entity.Album;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.repository.AlbumRepository;
import org.example.petwif.repository.MemberRepository;
import org.example.petwif.web.dto.albumDto.AlbumRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final MemberRepository memberRepository;

    //앨범 생성
    @Override
    @Transactional
    public Album saveAlbum(AlbumRequestDto.SaveRequestDto requestDto){
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        Album album = AlbumConverter.toAlbumEntity(requestDto, member);
        album = albumRepository.save(album);
        return album;
    }

    //앨범 수정
    @Override
    @Transactional
    public Album updateAlbum(Long albumId, Long memberId, AlbumRequestDto.UpdateRequestDto requestDto){
        Album album =  albumRepository.findById(albumId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ALBUM_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        if(!album.getMember().getId().equals(member.getId())){
            throw new GeneralException(ErrorStatus.ALBUM_UNAUTHORIZED);
        }
        album.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getScope());
        return albumRepository.save(album);

    }


    //앨범 삭제
    @Override
    @Transactional
    public void deleteAlbum(Long albumId, Long memberId){
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ALBUM_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        if(!album.getMember().getId().equals(member.getId())){
            throw new GeneralException(ErrorStatus.ALBUM_UNAUTHORIZED);
        }
        albumRepository.delete(album);
    }

    @Override
    @Transactional
    public void increaseView(Long albumId, Long memberId){
        Album album =  albumRepository.findById(albumId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ALBUM_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        if (!album.getMember().getId().equals(member.getId())) {
        album.incrementViewCount();
        albumRepository.save(album);
    }

    }

}
