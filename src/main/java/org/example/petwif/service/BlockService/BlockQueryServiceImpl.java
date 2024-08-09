package org.example.petwif.service.BlockService;

import lombok.RequiredArgsConstructor;
import org.example.petwif.domain.entity.Block;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.repository.BlockRepository;
import org.example.petwif.repository.MemberRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockQueryServiceImpl implements BlockQueryService {

    private final MemberRepository memberRepository;

    private final BlockRepository blockRepository;

    @Override
    public Slice<Block> getBlockList(Long memberId, Integer page) {

        Member member = memberRepository.findById(memberId).get();

        return blockRepository.findAllByMember(member, PageRequest.of(page, 2));
    }
}
