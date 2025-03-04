package dev.liaskarllate.finmathly.controller.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.liaskarllate.finmathly.dto.ObjectFactoryDTO;
import dev.liaskarllate.finmathly.dto.ThrownExceptionDTO;
import dev.liaskarllate.finmathly.exception.BusinessException;
import dev.liaskarllate.finmathly.exception.InputNotValidException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ControllerAdvice
@ApiResponses(value = {
    @ApiResponse(
    	responseCode = "400", 
		content = @Content(
            mediaType = "application/json", 
            schema = @Schema(implementation = ThrownExceptionDTO.class),
            examples = @ExampleObject(
                value = "{\"message\": \"Interest rate is reaching for the stars. Please provide a value that's more down-to-earth.\"}"))),
    @ApiResponse(
		responseCode = "404", 
		content = @Content(
            mediaType = "application/json", 
            schema = @Schema(implementation = ThrownExceptionDTO.class),
            examples = @ExampleObject(
               value = "{\"message\": \"The resourse you're looking for seems to be playing hide and seek. Please double-check the URL and try again.\"}"))),
    @ApiResponse(
		responseCode = "422", 
		content = @Content(
            mediaType = "application/json", 
            schema = @Schema(implementation = ThrownExceptionDTO.class),
            examples = @ExampleObject(
                value = "{\"message\": \"The operation was aborted. The inflation index for the provided date seems to have taken a vacation.\"}"))),
    @ApiResponse(
		responseCode = "500", 
		content = @Content(
            mediaType = "application/json", 
            schema = @Schema(implementation = ThrownExceptionDTO.class),
            examples = @ExampleObject(
               value = "{\"message\": \"We're sorry, but our calculator just went on an unexpected coffee break.\"}")))
	}
)
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ThrownExceptionDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder errorsMessage = new StringBuilder("We noticed some issues with what you submitted. Please review the following: ");
        
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorsMessage.append("[%s: %s] ".formatted(error.getField(), error.getDefaultMessage()));
        }

        return ResponseEntity
        		.badRequest()
        		.body(ObjectFactoryDTO.getThrownExceptionDTO(errorsMessage.toString()));
    }
	
    @ExceptionHandler(InputNotValidException.class)
    public ResponseEntity<ThrownExceptionDTO> handleInputNotValidException(InputNotValidException ex) {
        return ResponseEntity
        		.badRequest()
                .body(ObjectFactoryDTO.getThrownExceptionDTO(ex.getMessage()));
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ThrownExceptionDTO> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .unprocessableEntity()
                .body(ObjectFactoryDTO.getThrownExceptionDTO(ex.getMessage()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ThrownExceptionDTO> handleGeneralException(Exception ex) {
        return ResponseEntity
                .internalServerError()
                .body(ObjectFactoryDTO.getThrownExceptionDTO(ex.getMessage()));
    }
    
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ThrownExceptionDTO> handleGeneralException(Throwable th) {
        return ResponseEntity
                .internalServerError()
                .body(ObjectFactoryDTO.getThrownExceptionDTO("We're sorry, but an unexpected issue occurred. Please try again later. If necessary, contact our support team."));
    }
}