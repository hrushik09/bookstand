package com.hrushi.bookstand.batch;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Component
public class SyncData {
    private static final Logger log = LoggerFactory.getLogger(SyncData.class);
    private final AddAuthors addAuthors;
    private final AddWorks addWorks;

    SyncData(AddAuthors addAuthors, AddWorks addWorks) {
        this.addAuthors = addAuthors;
        this.addWorks = addWorks;
    }

    @PostConstruct
    private void sync() {
        String directory = "/Users/hrushikeshkandale/Downloads/dummy";
        Consumer<Path> consumer = addWorks::fromLocalFile;
        process(directory, consumer);
    }

    private void process(String directory, Consumer<Path> consumer) {
        if (directory == null || directory.isEmpty()) {
            throw new RuntimeException("directory is null");
        }
        log.info("start sync to local database");
        try (Stream<Path> files = Files.list(Path.of(directory))) {
            files.forEach(file -> {
                log.info("processing file {}", file.getFileName());
                consumer.accept(file);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
