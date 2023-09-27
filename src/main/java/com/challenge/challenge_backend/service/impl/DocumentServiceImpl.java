package com.challenge.challenge_backend.service.impl;

import com.challenge.challenge_backend.model.Document;
import com.challenge.challenge_backend.repo.IDocumentRepo;
import com.challenge.challenge_backend.repo.IGenericRepo;
import com.challenge.challenge_backend.service.IDocumentService;
import lombok.RequiredArgsConstructor;
import org.apache.el.stream.Optional;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl extends CRUDImpl<Document, Integer> implements IDocumentService {
    private final IDocumentRepo repo;

    @Override
    protected IGenericRepo<Document, Integer> getRepo() {
        return repo;
    }

    @Override
    public Document findByHashSha256(String hash) {
        return repo.findByHashSha256(hash);
    }

    @Override
    public Document findByHashSha512(String hash) {
        return repo.findByHashSha512(hash);
    }

    @Override
    public Document findByHash(String hashType, String hash) {
        if (hashType.equals("SHA-256")) {
            return repo.findByHashSha256(hash);
        } else {
            return repo.findByHashSha512(hash);
        }
    }
}


