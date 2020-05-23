package com.github.enesusta.belge.controller;

import com.github.enesusta.belge.repository.BelgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/pdf")
@RequiredArgsConstructor
public class BelgeController {

    private final BelgeRepository belgeRepository;

    @Value(value = "test.pdf")
    private File pdf;

    //    @PostConstruct
    public void init() throws IOException {

        belgeRepository.save(Files.readAllBytes(pdf.toPath()));
    }

    @GetMapping
    public ResponseEntity<byte[]> getSource() {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header("Content-Disposition", "inline; filename=test.pdf")
                .body(belgeRepository.getSource(1, "a"));

    }

}
