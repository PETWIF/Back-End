package org.example.petwif.service.BlockService;

import lombok.RequiredArgsConstructor;
import org.example.petwif.apiPayload.code.status.ErrorStatus;
import org.example.petwif.apiPayload.exception.handler.BlockHandler;
import org.example.petwif.domain.entity.Block;
import org.example.petwif.repository.BlockRepository;
import org.example.petwif.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockCommandServiceImpl implements BlockCommandService {

    private final MemberRepository memberRepository;

    private final BlockRepository blockRepository;

    @Override
    @Transactional
    public Block blockMember(Long memberId, Long targetId) {

        Block block = new Block();

        if (blockRepository.existsByMember_IdAndTarget_Id(memberId, targetId)) {
            throw new BlockHandler(ErrorStatus.BLOCK_ALREADY_EXIST);
        }

        block.setMember(memberRepository.findById(memberId).get());
        block.setTarget(memberRepository.findById(targetId).get());


        return blockRepository.save(block);
    }

    @Override
    @Transactional
    public void unblockMember(Long memberId, Long targetId) {
        Block block = blockRepository.findByMember_IdAndTarget_Id(memberId, targetId).orElseThrow(() -> new BlockHandler(ErrorStatus.BLOCK_NOT_FOUND));

        blockRepository.delete(block);
    }
}
