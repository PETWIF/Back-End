package org.example.petwif.apiPayload.exception.handler;

import org.example.petwif.apiPayload.code.BaseErrorCode;
import org.example.petwif.apiPayload.exception.GeneralException;

public class FriendHandler extends GeneralException {
    public FriendHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
