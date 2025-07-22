package com.example.webapp.controllerAdvice

import com.example.webapp.exceptions.ExpiredJwtException
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.security.SignatureException


@RestControllerAdvice
class GlobalExceptionHandler {
    public fun handleSecurityException(exception: Exception): ProblemDetail {
        var errorDetail: ProblemDetail? = null


        // TODO send this stack trace to an observability tool
        exception.printStackTrace()

        if (exception is AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.message)
            errorDetail.setProperty("description", "You are not authorized to access this resource")
        }

        if (exception is SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.message)
            errorDetail.setProperty("description", "The JWT signature is invalid")
        }

        if (exception is ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.message)
            errorDetail.setProperty("description", "The JWT token has expired")
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.message)
            errorDetail.setProperty("description", "Unknown internal server error.")
        }

        return errorDetail
    }
}
