package com.challenge.challenge_backend.repo;

import com.challenge.challenge_backend.model.Document;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IDocumentRepo extends IGenericRepo<Document, Integer> {

    @Query("FROM Document d WHERE d.hashSha256 = decode(:hash, 'hex')")
    Document findByHashSha256(@Param("hash") String hash);

    @Query("FROM Document d WHERE d.hashSha512 = decode(:hash, 'hex')")
    Document findByHashSha512(@Param("hash") String hash);
}
