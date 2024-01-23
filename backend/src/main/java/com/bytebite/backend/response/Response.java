package com.bytebite.backend.response;

import com.bytebite.backend.model.enums.StatusCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private StatusCode statusCode;
    private String message;
}
