package org.example.petwif.service.BlockService;

import org.example.petwif.domain.entity.Block;

public interface BlockCommandService {
    Block blockMember(Long memberId, Long targetId);
    void unblockMember(Long memberId, Long targetId);
}

