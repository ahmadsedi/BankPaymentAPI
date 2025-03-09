package com.ahmadsedi.ibpts.controller;

import com.ahmadsedi.ibpts.exceptions.InvalidAccountDetailsException;
import com.ahmadsedi.ibpts.exceptions.InsufficientFundsException;
import com.ahmadsedi.ibpts.exceptions.InvalidBalanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

/**
 * The {@code ControllerExceptionHandler} is a Spring's aspect to handle exceptions which are thrown during processing
 * a request. The exceptions may throw in service or controller classes.
 *
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 12:50
 */

@RestControllerAdvice
class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * Handles exceptions of type InvalidAccountDetailsException once they thrown in business or controller classes.
     *
     * @param request represents the HTTP request object, ended up with InvalidAccountDetailsException exception.
     * @param ex      represents the exception object which has thrown when processing a non-existence account-id.
     * @return HTTP response object in case of exception.
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(InvalidAccountDetailsException.class)
    public @ResponseBody
    HttpErrorInfo handleInvalidAccountDetails(
            ServerHttpRequest request, InvalidAccountDetailsException ex) {

        return createHttpErrorInfo(BAD_REQUEST, request, ex);
    }

    /**
     * Handles exceptions of type InvalidBalanceException once they are thrown in business or controller classes.
     *
     * @param request represents the HTTP request object, ended up with InvalidBalanceException exception.
     * @param ex      represents the exception object which has been thrown when processing account creation with negative
     *                balance.
     * @return HTTP response object in case of exception.
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(InvalidBalanceException.class)
    public @ResponseBody
    HttpErrorInfo handleInvalidBalanceException(
            ServerHttpRequest request, InvalidBalanceException ex) {

        return createHttpErrorInfo(BAD_REQUEST, request, ex);
    }


    /**
     * Handles exceptions of type MethodArgumentNotValidException when a request's payload has not a valid format.
     *
     * @param ex represents the exception object which has been thrown when processing account creation with negative
     *           balance.
     * @return HTTP response object in case of exception.
     */
    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpErrorInfo handleValidationExceptions(ServerHttpRequest request,
                                                    WebExchangeBindException ex
    ) {
        String message = ex.getBindingResult().getAllErrors().stream().
                map(error -> ((FieldError) error).getField() + ":" + error.getDefaultMessage()).
                collect(Collectors.joining(",", "{", "}"));
        return new HttpErrorInfo(HttpStatus.BAD_REQUEST, request.getPath().pathWithinApplication().value(), message);
    }

    /**
     * Handles exceptions of type InsufficientFundsException once they thrown in business or controller classes.
     *
     * @param request represents the HTTP request object, ended up with InsufficientFundsException exception.
     * @param ex      represents the exception object which has thrown when processing a transfer with insufficient balance.
     * @return HTTP response object in case of exception.
     */
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InsufficientFundsException.class)
    public @ResponseBody
    HttpErrorInfo handleInsufficientFundsException(
            ServerHttpRequest request, InsufficientFundsException ex) {

        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex);
    }

    private HttpErrorInfo createHttpErrorInfo(
            HttpStatus httpStatus, ServerHttpRequest request, Exception ex) {

        final String path = request.getPath().pathWithinApplication().value();
        final String message = ex.getMessage();

        LOG.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new HttpErrorInfo(httpStatus, path, message);
    }
}
