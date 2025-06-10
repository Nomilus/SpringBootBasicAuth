package com.example.example_auth.exception;//package com.example.example_auth.exception;
//
//
//import com.example.example_auth.model.dto.res.ErrorResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//
//import java.time.LocalDateTime;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e, WebRequest request) {
//        ErrorResponse response = new ErrorResponse(
//                LocalDateTime.now(),
//                HttpStatus.NOT_FOUND.value(),
//                HttpStatus.NOT_FOUND.getReasonPhrase(),
//                e.getMessage(),
//                request.getContextPath(),
//                null,
//                null
//        );
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//    }
//
//    @ExceptionHandler(DuplicateUserException.class)
//    public ResponseEntity<ErrorResponse> handleDuplicateUser(DuplicateUserException e, WebRequest request) {
//        ErrorResponse response = new ErrorResponse(
//                LocalDateTime.now(),
//                HttpStatus.BAD_REQUEST.value(),
//                HttpStatus.BAD_REQUEST.getReasonPhrase(),
//                e.getMessage(),
//                request.getContextPath(),
//                null,
//                null
//        );
//        return ResponseEntity.badRequest().body(response);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGeneralExceptions(Exception e, WebRequest request) {
//        ErrorResponse response = new ErrorResponse(
//                LocalDateTime.now(),
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
//                "Internal server error",
//                request.getContextPath(),
//                null,
//                null
//        );
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }
//}