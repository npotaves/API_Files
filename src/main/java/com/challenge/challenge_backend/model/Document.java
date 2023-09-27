package com.challenge.challenge_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false, length = 500)
    private String fileName;

    @Column(nullable = false, length = 32, name = "hash_sha_256", columnDefinition = "BYTEA")
    private byte[] hashSha256;

    @Column(nullable = false, length = 32, name = "hash_sha_512", columnDefinition = "BYTEA")
    private byte[] hashSha512;
    @Column(nullable = true)
    private LocalDateTime lastUpload;
}
