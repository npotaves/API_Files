package com.challenge.challenge_backend.service;

import com.challenge.challenge_backend.model.Document;
import org.apache.el.stream.Optional;

import java.util.List;

public interface IDocumentService extends ICRUD<Document, Integer> {

    Document findByHashSha256(String hash);

    Document findByHashSha512(String hash);

    Document findByHash(String hashType, String hash);

}
