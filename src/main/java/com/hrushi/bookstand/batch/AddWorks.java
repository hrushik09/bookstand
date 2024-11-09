package com.hrushi.bookstand.batch;

import com.hrushi.bookstand.domain.works.CreateWorkCommand;
import com.hrushi.bookstand.domain.works.WorkService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
class AddWorks {
    private static final Logger log = LoggerFactory.getLogger(AddWorks.class);
    private final WorkService workService;

    AddWorks(WorkService workService) {
        this.workService = workService;
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
                    String openLibraryKey = keyWithPrefix.substring(7);

                    String title;
                    try {
                        title = jsonObject.getString("title");
                    } catch (JSONException e) {
                        title = "";
                    }
                    if (title.length() > 200) {
                        title = title.substring(0, 200);
                    }

                    String subtitle;
                    try {
                        subtitle = jsonObject.getString("subtitle");
                    } catch (JSONException e) {
                        subtitle = null;
                    }
                    if (subtitle != null && subtitle.length() > 200) {
                        subtitle = subtitle.substring(0, 200);
                    }

                    String coverId;
                    try {
                        JSONArray covers = jsonObject.getJSONArray("covers");
                        coverId = String.valueOf(covers.get(0));
                    } catch (JSONException e) {
                        coverId = null;
                    }

                    List<String> authorOpenLibraryKeys = new ArrayList<>();
                    try {
                        JSONArray authors = jsonObject.getJSONArray("authors");
                        for (int i = 0; i < authors.length(); i++) {
                            JSONObject object = authors.getJSONObject(i);
                            JSONObject author = object.getJSONObject("author");
                            String authorKeyWithPrefix = author.getString("key");
                            String authorOpenLibraryKey = authorKeyWithPrefix.substring(9);
                            authorOpenLibraryKeys.add(authorOpenLibraryKey);
                        }
                    } catch (JSONException e) {
                        log.error("lineNumber: {}, line: {}, could not parse author: {}", lineNumber, lineNumber, e.getMessage());
                    }

                    workService.createWork(new CreateWorkCommand(openLibraryKey, title, subtitle, coverId, authorOpenLibraryKeys));
                    lineNumber++;
                } catch (Exception e) {
                    log.error("lineNumber: {}, line: {}, could not create work: {}", lineNumber, line, e.getMessage());
                }
            }
            log.info("processed total {} lines", lineNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
