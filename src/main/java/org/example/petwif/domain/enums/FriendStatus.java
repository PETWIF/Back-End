package org.example.petwif.domain.enums;

public enum FriendStatus {
    PENDING,    // 친구 요청이 발송되었지만 아직 처리되지 않음
    ACCEPTED,   // 친구 요청이 수락됨
    REJECTED,   // 친구 요청이 거부됨
    CANCELLED,  // 친구 요청이 취소됨
    EXPIRED     // 친구 요청이 만료됨
}
