package kr.ac.kopo.jeonse.global.payload.exception;

public abstract class BaseException extends RuntimeException{
    public abstract BaseExceptionType getExceptionType();
}