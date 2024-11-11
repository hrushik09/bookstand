package com.hrushi.bookstand.domain.works;

import com.hrushi.bookstand.domain.authors.AuthorEntity;
import com.hrushi.bookstand.domain.authors.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class WorkService {
    private static final String BASE_URL = "https://covers.openlibrary.org/b/id/";
    private static final String SUFFIX = "-L.jpg";
    private final WorkRepository workRepository;
    private final AuthorService authorService;
    private final WorkRatingRepository workRatingRepository;

    WorkService(WorkRepository workRepository, AuthorService authorService, WorkRatingRepository workRatingRepository) {
        this.workRepository = workRepository;
        this.authorService = authorService;
        this.workRatingRepository = workRatingRepository;
    }

    @Transactional
    public void createWork(CreateWorkCommand cmd) {
        Set<AuthorEntity> authorEntities = cmd.authorOpenLibraryKeys().stream()
                .map(authorService::findByOpenLibraryKey)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        WorkEntity workEntity = new WorkEntity();
        workEntity.setOpenLibraryKey(cmd.openLibraryKey());
        workEntity.setTitle(cmd.title());
        workEntity.setSubtitle(cmd.subtitle());
        workEntity.setCoverId(cmd.coverId());
        workEntity.setAuthors(authorEntities);
        workRepository.save(workEntity);
    }

    public Work getWork(Long userId, Long workId) {
        WorkEntity workEntity = workRepository.findWork(workId)
                .orElseThrow(() -> new WorkDoesNotExist(workId));
        Integer rating = workRatingRepository.findByUserIdAndWorkId(userId, workId)
                .map(WorkRatingEntity::getRating)
                .orElse(null);
        return mapper(workEntity, rating);
    }

    private Work mapper(WorkEntity workEntity, Integer rating) {
        String coverUrl;
        if (workEntity.getCoverId() != null) {
            coverUrl = BASE_URL + workEntity.getCoverId() + SUFFIX;
        } else {
            coverUrl = "";
        }
        List<WorkAuthor> workAuthors = workEntity.getAuthors().stream()
                .map(authorEntity -> new WorkAuthor(authorEntity.getId(), authorEntity.getName()))
                .toList();
        return new Work(workEntity.getId(), workEntity.getTitle(), workEntity.getSubtitle(), coverUrl, rating, workAuthors);
    }

    @Transactional
    public void updateRating(UpdateRatingCommand cmd) {
        WorkRatingEntity workRatingEntity = workRatingRepository.findByUserIdAndWorkId(cmd.userId(), cmd.workId())
                .orElse(new WorkRatingEntity(cmd.userId(), cmd.workId()));
        workRatingEntity.setRating(cmd.rating());
        workRatingRepository.save(workRatingEntity);
    }
}
