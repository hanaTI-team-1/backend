package kr.ac.kopo.jeonse.global.payload.exception.handler;

import kr.ac.kopo.jeonse.global.payload.code.BaseErrorCode;
import kr.ac.kopo.jeonse.global.payload.exception.GeneralException;

public class ErrorHandler extends GeneralException {

    public ErrorHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }

}
