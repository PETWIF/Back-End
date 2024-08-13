package org.example.petwif.apiPayload.exception.handler;

import org.example.petwif.apiPayload.code.BaseErrorCode;
import org.example.petwif.apiPayload.exception.GeneralException;
import org.example.petwif.repository.AlbumRepository;

public class AlbumHandler extends GeneralException {
    public AlbumHandler(BaseErrorCode errorCode){super(errorCode);}
}
