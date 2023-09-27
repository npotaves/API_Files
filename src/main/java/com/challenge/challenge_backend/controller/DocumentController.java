package com.challenge.challenge_backend.controller;

import com.challenge.challenge_backend.dto.ResponseDocumentDTO;
import com.challenge.challenge_backend.dto.ResponseDocumentRedDTO;
import com.challenge.challenge_backend.dto.ResponseDocumentsDTO;
import com.challenge.challenge_backend.exception.InvalidMethodException;
import com.challenge.challenge_backend.exception.ModelNotFoundException;
import com.challenge.challenge_backend.model.Document;
import com.challenge.challenge_backend.service.IDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import com.challenge.challenge_backend.util.Hash;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final Hash utilHash;
    private final IDocumentService service;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @Operation(summary = "Endpoint que permite listar todos los documentos subidos con sus hash.")
    @GetMapping
    public ResponseEntity<List<ResponseDocumentDTO>> findAll() {
        List<ResponseDocumentDTO> list = service.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "Endpoint para encontrar un archivo por su hash y devolver su hash y última actualización.")
    @GetMapping("/{hashType}/{hash}")
    public ResponseEntity<ResponseDocumentRedDTO> findByHash(@Valid @PathVariable("hashType") String hashType, @Valid @PathVariable("hash") String hash) throws Exception {
        hashType = hashType.toUpperCase();
        if ((!hashType.equals("SHA-256")) && (!hashType.equals("SHA-512"))) {
            throw new Exception("El parámetro 'hash' solo puede ser 'SHA-256' o 'SHA-512'");
        }
        Document doc = service.findByHash(hashType.toUpperCase().trim(), hash);
        if (doc == null) {
            throw new ModelNotFoundException("No hay ningún documento con ese nombre");
        }
        ResponseDocumentRedDTO docDto = new ResponseDocumentRedDTO(doc.getFileName(), hashType.equals("SHA-256") ? utilHash.toHexString(doc.getHashSha256()) : utilHash.toHexString(doc.getHashSha512()), doc.getLastUpload());
        System.out.println(docDto.getFileName());
        return new ResponseEntity<>(docDto, HttpStatus.OK);
    }

    @Operation(summary = "Endpoint para subir documentos.")
    @PostMapping(value = "/hash/{hashType}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDocumentsDTO> uploadDocuments(@Valid @PathVariable("hashType") String hashType, @RequestParam("documents") List<MultipartFile> files) throws InvalidMethodException, NoSuchAlgorithmException {
        if ((!hashType.equals("SHA-256")) && (!hashType.equals("SHA-512"))) {
            throw new InvalidMethodException("El parámetro 'hash' solo puede ser 'SHA-256' o 'SHA-512'");
        }
        hashType = hashType.toUpperCase();
        ResponseDocumentsDTO resp = new ResponseDocumentsDTO();
        List<ResponseDocumentRedDTO> list = new ArrayList<>();
        resp.setAlgorithm(hashType);
        for (MultipartFile file : files) {

            String fileName = file.getOriginalFilename();
            Document doc;
            ResponseDocumentRedDTO rdr = new ResponseDocumentRedDTO();
            rdr.setFileName(fileName);
            if (hashType.equals("SHA-256")) {
                doc = service.findByHashSha256(utilHash.toHexString(utilHash.getSHA(fileName)));
            } else {
                doc = service.findByHashSha512(utilHash.toHexString(utilHash.getSHA(fileName)));
            }

            if (doc != null) {
                LocalDateTime date = LocalDateTime.now();
                doc.setLastUpload(date);
                service.save(doc);
                rdr.setLastUpload(date);
                if (hashType.equals("SHA-256")) {
                    rdr.setHash(utilHash.toHexString(doc.getHashSha256()));
                } else {
                    rdr.setHash(utilHash.toHexString(doc.getHashSha512()));
                }
            } else {
                Document docu = new Document();
                docu.setFileName(fileName);
                docu.setHashSha256(utilHash.getSHA(fileName));
                docu.setHashSha512(utilHash.getSHA512(fileName));
                service.save(docu);
                if (hashType.equals("SHA-256")) {
                    rdr.setHash(utilHash.toHexString(docu.getHashSha256()));
                } else {
                    rdr.setHash(utilHash.toHexString(docu.getHashSha512()));
                }
            }
            list.add(rdr);
        }
        resp.setDocuments(list);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    private ResponseDocumentDTO convertToDto(Document obj) {
        ResponseDocumentDTO dto = mapper.map(obj, ResponseDocumentDTO.class);
        dto.setHashSha256(utilHash.toHexString(obj.getHashSha256()));
        dto.setHashSha512(utilHash.toHexString(obj.getHashSha512()));
        return dto;
    }

}
