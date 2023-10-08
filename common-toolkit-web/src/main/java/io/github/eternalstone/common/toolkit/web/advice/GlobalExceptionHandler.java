package io.github.eternalstone.common.toolkit.web.advice;

import io.github.eternalstone.common.toolkit.enums.GlobalErrorCode;
import io.github.eternalstone.common.toolkit.exception.BizException;
import io.github.eternalstone.common.toolkit.exception.GlobalException;
import io.github.eternalstone.common.toolkit.model.ErrorCode;
import io.github.eternalstone.common.toolkit.model.WebApiRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 基础全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * NoHandlerFoundException 404 异常处理
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public WebApiRes handlerNoHandlerFoundException(HttpServletRequest req, NoHandlerFoundException exception) {
        printError(req, exception);
        return failure(HttpStatus.NOT_FOUND);
    }

    /**
     * HttpRequestMethodNotSupportedException 405 异常处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public WebApiRes handlerHttpRequestMethodNotSupportedException(HttpServletRequest req,
                                                                   HttpRequestMethodNotSupportedException exception) {
        printError(req, exception);
        return failure(HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * HttpMediaTypeNotSupportedException 415 异常处理
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public WebApiRes handlerHttpMediaTypeNotSupportedException(HttpServletRequest req,
                                                               HttpMediaTypeNotSupportedException exception) {
        printError(req, exception);
        return failure(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Exception, GlobalException 类捕获 500 异常处理
     */
    @ExceptionHandler(value = {GlobalException.class, Exception.class})
    @ResponseBody
    public WebApiRes handlerGlobalException(HttpServletRequest req, Exception exception) {
        printError(req, exception);
        return WebApiRes.failure(GlobalErrorCode.SYSTEM_ERROR);
    }


    /**
     * BizException
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public WebApiRes handlerBizException(HttpServletRequest req, BizException exception) {
        printError(req, exception);
        ErrorCode errCode = exception.getErrorCode();
        WebApiRes result;
        if (errCode == null) {
            result = WebApiRes.failure(exception.getCode(), exception.getMessage(), exception.getErrorData());
        } else {
            result = WebApiRes.failure(errCode.getCode(), errCode.getDescription(), exception.getErrorData());
        }
        return result;
    }


    /**
     * HttpMessageNotReadableException 参数错误异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public WebApiRes handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String msg = String.format("%s : 错误详情( %s )", GlobalErrorCode.ERROR_PARAM.getDescription(),
                e.getRootCause().getMessage());
        return WebApiRes.failure(GlobalErrorCode.ERROR_PARAM.getCode(), msg);
    }

    /**
     * BindException 参数错误异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public WebApiRes handleMethodArgumentNotValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        return handlerNotValidException(bindingResult);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public WebApiRes handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return handlerNotValidException(bindingResult);
    }


    private WebApiRes handlerNotValidException(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (log.isDebugEnabled()) {
            for (FieldError error : fieldErrors) {
                log.error("{} -> {}", error.getDefaultMessage(), error.getDefaultMessage());
            }
        }

        if (fieldErrors.isEmpty()) {
            log.error("validExceptionHandler error fieldErrors is empty");
            return WebApiRes.failure(GlobalErrorCode.SYSTEM_ERROR);
        }

        return WebApiRes.failure(GlobalErrorCode.ERROR_PARAM.getCode(), fieldErrors.get(0).getDefaultMessage());
    }

    private void printError(HttpServletRequest req, Exception e) {
        log.error("uri={}, method={}", req.getRequestURI(), req.getMethod(), e);
    }

    private WebApiRes failure(HttpStatus status) {
        return WebApiRes.failure(status.value(), status.getReasonPhrase());
    }


}
