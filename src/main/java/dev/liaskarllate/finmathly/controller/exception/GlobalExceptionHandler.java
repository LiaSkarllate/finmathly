package dev.liaskarllate.finmathly.controller.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.liaskarllate.finmathly.dto.ObjectFactoryDTO;
import dev.liaskarllate.finmathly.exception.BusinessException;
import dev.liaskarllate.finmathly.exception.InputNotValidException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder errorsMessage = new StringBuilder("We noticed some issues with what you submitted. Please review the following errors: ");
        
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorsMessage.append(String.format("[%s: %s] ", error.getField(), error.getDefaultMessage()));
        }

        return ResponseEntity
        		.badRequest()
        		.body(ObjectFactoryDTO.getThrownExceptionDTO(errorsMessage.toString()));
    }
	
    @ExceptionHandler(InputNotValidException.class)
    public ResponseEntity<?> handleInputNotValidException(InputNotValidException ex) {
        return ResponseEntity
        		.badRequest()
                .body(ObjectFactoryDTO.getThrownExceptionDTO(ex.getMessage()));
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .unprocessableEntity()
                .body(ObjectFactoryDTO.getThrownExceptionDTO(ex.getMessage()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        return ResponseEntity
                .internalServerError()
                .body(ObjectFactoryDTO.getThrownExceptionDTO(ex.getMessage()));
    }
    
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleGeneralException(Throwable th) {
        return ResponseEntity
                .internalServerError()
                .body(ObjectFactoryDTO.getThrownExceptionDTO("We're sorry, but an unexpected error occurred. Please try again later. If necessary, contact our support team."));
    }
}
