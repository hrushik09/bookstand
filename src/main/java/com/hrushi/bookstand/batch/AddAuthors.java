package com.hrushi.bookstand.batch;

import com.hrushi.bookstand.domain.authors.AuthorService;
import com.hrushi.bookstand.domain.authors.CreateAuthorCommand;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
class AddAuthors {
    private static final Logger log = LoggerFactory.getLogger(AddAuthors.class);
    private final AuthorService authorService;

    AddAuthors(AuthorService authorService) {
        this.authorService = authorService;
    }

    public void fromLocalFile(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            long lineNumber = 1L;
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String substring = line.substring(line.indexOf("{"));
                    JSONObject jsonObject = new JSONObject(substring);
                    String keyWithPrefix = jsonObject.getString("key");
                    String openLibraryKey = keyWithPrefix.substring(9);
                    String name;
                    try {
                        name = jsonObject.getString("name");
                    } catch (JSONException e) {
                        name = "";
                    }
                    if (name.length() > 200) {
                        name = name.substring(0, 200);
                    }
                    authorService.createAuthor(new CreateAuthorCommand(openLibraryKey, name));
                    lineNumber++;
                } catch (Exception e) {
                    log.error("lineNumber: {}, line: {}, could not create author: {}", lineNumber, line, e.getMessage());
                }
            }
            log.info("processed total {} lines", lineNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
