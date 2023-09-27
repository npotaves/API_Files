package com.challenge.challenge_backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ResponseDocumentDTO {
    private String fileName;
    private String hashSha256;
    private String hashSha512;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime lastUpload;
}
