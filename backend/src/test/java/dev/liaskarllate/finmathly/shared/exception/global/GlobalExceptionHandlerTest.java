package dev.liaskarllate.finmathly.shared.exception.global;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dev.liaskarllate.finmathly.shared.dto.ThrownExceptionDTO;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleResourceConflictExceptionWithConflictStatus() {
        ResourceConflictException ex = new ResourceConflictException("duplicate name");

        ResponseEntity<ThrownExceptionDTO> response = handler.handleResourceConflictException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().getMessage()).isEqualTo("duplicate name");
    }

    @Test
    void shouldHandleResourceNotFoundExceptionWithNotFoundStatus() {
        ResourceNotFoundException ex = new ResourceNotFoundException("not found");

        ResponseEntity<ThrownExceptionDTO> response = handler.handlResourceNotFoundException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("not found");
    }
}
