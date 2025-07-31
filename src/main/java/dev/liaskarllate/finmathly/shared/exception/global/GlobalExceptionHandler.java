package dev.liaskarllate.finmathly.shared.exception.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import dev.liaskarllate.finmathly.shared.dto.ThrownExceptionDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ControllerAdvice
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ThrownExceptionDTO.class), examples = @ExampleObject(value = "{\"message\": \"Interest rate is reaching for the stars. Please provide a value that's more down-to-earth.\"}"))),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ThrownExceptionDTO.class), examples = @ExampleObject(value = "{\"message\": \"The resourse you're looking for seems to be playing hide and seek. Please BigDecimal-check the URL and try again.\"}"))),
        @ApiResponse(responseCode = "422", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ThrownExceptionDTO.class), examples = @ExampleObject(value = "{\"message\": \"The operation was aborted. The inflation index for the provided date seems to have taken a vacation.\"}"))),
        @ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ThrownExceptionDTO.class), examples = @ExampleObject(value = "{\"message\": \"We're sorry, but our calculator just went on an unexpected coffee break.\"}")))
})
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ThrownExceptionDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        StringBuilder errorsMessage = new StringBuilder(
                "We noticed some issues with what you submitted. Please review the following: ");

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorsMessage.append("[%s: %s] ".formatted(error.getField(), error.getDefaultMessage()));
        }

        return ResponseEntity
                .badRequest()
                .body(new ThrownExceptionDTO(errorsMessage.toString()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ThrownExceptionDTO> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {
        String message = "The value '%s' for parameter '%s' is not valid. Please, provide a value that matches the expected type."
                .formatted(
                        ex.getValue(), ex.getName());

        return ResponseEntity
                .badRequest()
                .body(new ThrownExceptionDTO(message));
    }

    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<ThrownExceptionDTO> handleBusinessRuleViolationException(BusinessRuleViolationException ex) {
        return ResponseEntity
                .unprocessableEntity()
                .body(new ThrownExceptionDTO(ex.getMessage()));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ThrownExceptionDTO> handleInvalidInputException(InvalidInputException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ThrownExceptionDTO(ex.getMessage()));
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ThrownExceptionDTO> handleResourceConflictException(InvalidInputException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ThrownExceptionDTO(ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ThrownExceptionDTO> handlResourceNotFoundException(InvalidInputException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ThrownExceptionDTO(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ThrownExceptionDTO> handleGeneralException(Exception ex) {
        return ResponseEntity
                .internalServerError()
                .body(new ThrownExceptionDTO(ex.getMessage()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ThrownExceptionDTO> handleGeneralException(Throwable th) {
        return ResponseEntity
                .internalServerError()
                .body(new ThrownExceptionDTO(
                        "We're sorry, but an unexpected issue occurred. Please try again later. If necessary, contact our support team."));
    }
}