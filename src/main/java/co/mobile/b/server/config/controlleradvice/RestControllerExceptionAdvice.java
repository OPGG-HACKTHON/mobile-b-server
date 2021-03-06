package co.mobile.b.server.config.controlleradvice;

import co.mobile.b.server.config.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @RestControllerAdvice + @ExceptionHandler : 컨트롤러에 대한 전역 예외처리
 * 예외처리 클래스, @RestController 어노테이션이 붙은 클래스의 Exception울 Catch 하여 처리한다.
 * 현재는 RuntimeException -> Service에서 발생하는 에러들만 처리되어있다.
 * 추후 파라미터 매핑오류, 시큐리티 오류 등 여러가지 Exception을 추가해야함.
 */
@Slf4j
// @ControllerAdvice + @ResponseBody 특정 메소드를 컨트롤러 전역적으로 사용
@RestControllerAdvice
public class RestControllerExceptionAdvice {

    /**
     * RuntimeException 에러 핸들러
     *
     * @param e   the e
     * @param req the req
     * @return the error response
     */
    // Exception이 발생했을 때 자동으로 특정 status를 response에 등록하여 반환
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    // 특정 예외를 핸들링
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handlerRuntimeException(RuntimeException e, HttpServletRequest req) {
        log.error("=============== Handler RuntimeException ===============");
        e.printStackTrace();
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

    /**
     * 로그인시 아이디 비밀번호 불일치
     * @param e
     * @param req
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException e, HttpServletRequest req) {
        log.error("===================== Handler handleBadCredentialsException =====================");
        e.printStackTrace();
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "아이디 혹은 비밀번호가 일치하지 않습니다.", null);
    }

    /**
     * 메소드 파라미터 valid 불통
     * @param e
     * @param req
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest req) {
        log.error("===================== Handler MethodArgumentNotValidException =====================");
        e.printStackTrace();
        return getErrorResponseByBindingResult(e.getBindingResult(), HttpStatus.BAD_REQUEST, "유효하지 않은 값이 있습니다.");
    }

    private ErrorResponse getErrorResponseByBindingResult(BindingResult bindingResult, HttpStatus status, String defaultMessage) {
        List<String> errorDetails = Optional.ofNullable(bindingResult.getAllErrors()).orElse(Collections.emptyList())
                .stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        return new ErrorResponse(status.value(), errorDetails.isEmpty() ? defaultMessage : errorDetails.get(0), errorDetails);
    }
}
