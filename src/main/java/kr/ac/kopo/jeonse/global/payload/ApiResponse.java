package kr.ac.kopo.jeonse.global.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import kr.ac.kopo.jeonse.global.payload.code.BaseCode;
import kr.ac.kopo.jeonse.global.payload.code.status.SuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code","message", "data"})
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private final boolean isSuccess;
    private final int code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> ApiResponse<T> onSuccess(T data){
        return new ApiResponse<>(true, SuccessStatus._OK.getReasonHttpStatus().getHttpStatus().value(), SuccessStatus._OK.getReasonHttpStatus().getMessage(), data);
    }

    public static <T> ApiResponse<T> of(BaseCode code, T data){
        return new ApiResponse<>(code.getReasonHttpStatus().isSuccess(), code.getReasonHttpStatus().getHttpStatus().value(), code.getReasonHttpStatus().getMessage(), data);
    }


    public static <T> ApiResponse<T> onFailure(String code, String message, T data){
        return new ApiResponse<>(false, Integer.parseInt(code), message, data);
    }
}
