package com.hrushi.bookstand.batch;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Component
public class SyncData {
    private static final Logger log = LoggerFactory.getLogger(SyncData.class);
    private final AddAuthors addAuthors;

    SyncData(AddAuthors addAuthors) {
        this.addAuthors = addAuthors;
    }

    @PostConstruct
    void toLocalDatabase() throws IOException {
        log.info("start sync to local database");
//        fill value when running locally
        String directory = null;
        if (directory == null) {
            throw new RuntimeException("directory is null");
        }
        try (Stream<Path> files = Files.list(Path.of(directory))) {
            files.forEach(file -> {
                log.info("processing file {}", file.getFileName());
                addAuthors.fromLocalFile(file);
            });
        }
        log.info("end sync to local database");
    }
}
