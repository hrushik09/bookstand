package com.hrushi.bookstand.domain.works;

import com.hrushi.bookstand.domain.authors.AuthorEntity;
import com.hrushi.bookstand.domain.authors.AuthorService;
import com.hrushi.bookstand.domain.users.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final UserService userService;
    private final WorkReviewRepository workReviewRepository;
    private final WorkReviewLikeRepository workReviewLikeRepository;

    WorkService(WorkRepository workRepository, AuthorService authorService, WorkRatingRepository workRatingRepository, UserService userService, WorkReviewRepository workReviewRepository, WorkReviewLikeRepository workReviewLikeRepository) {
        this.workRepository = workRepository;
        this.authorService = authorService;
        this.workRatingRepository = workRatingRepository;
        this.userService = userService;
        this.workReviewRepository = workReviewRepository;
        this.workReviewLikeRepository = workReviewLikeRepository;
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

    private static List<WorkAuthor> getWorkAuthors(WorkEntity workEntity) {
        return workEntity.getAuthors().stream()
                .map(authorEntity -> new WorkAuthor(authorEntity.getId(), authorEntity.getName()))
                .toList();
    }

    private static String getCoverUrl(WorkEntity workEntity) {
        if (workEntity.getCoverId() == null) {
            return "";
        }
        return BASE_URL + workEntity.getCoverId() + SUFFIX;
    }

    public Work getWork(Long userId, Long workId) {
        WorkEntity workEntity = workRepository.findWork(workId)
                .orElseThrow(() -> new WorkDoesNotExist(workId));
        Integer rating = getRating(userId, workId);
        String currentUserReview = getReview(userId, workId);
        List<WorkReview> allOtherReviews = getAllOtherReviews(userId, workId);
        String coverUrl = getCoverUrl(workEntity);
        List<WorkAuthor> workAuthors = getWorkAuthors(workEntity);
        return new Work(workEntity.getId(), workEntity.getTitle(), workEntity.getSubtitle(), coverUrl, rating, currentUserReview, workAuthors, allOtherReviews);
    }

    private List<WorkReview> getAllOtherReviews(Long userId, Long workId) {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "updatedAt"));
        return workReviewRepository.findByWorkId(workId, pageRequest).stream()
                .filter(workReviewEntity -> !workReviewEntity.getUserEntity().getId().equals(userId))
                .map(workReviewEntity -> new WorkReview(workReviewEntity.getId(), workReviewEntity.getUserEntity().getId(), workReviewEntity.getReview(), workReviewEntity.getCreatedAt(), workReviewEntity.getUpdatedAt()))
                .toList();
    }

    private Integer getRating(Long userId, Long workId) {
        return workRatingRepository.findByUserIdAndWorkId(userId, workId)
                .map(WorkRatingEntity::getRating)
                .orElse(null);
    }

    @Transactional
    public void updateRating(UpdateRatingCommand cmd) {
        WorkRatingEntity workRatingEntity = workRatingRepository.findByUserIdAndWorkId(cmd.userId(), cmd.workId())
                .orElse(new WorkRatingEntity(userService.getReferenceById(cmd.userId()), workRepository.getReferenceById(cmd.workId())));
        workRatingEntity.setRating(cmd.rating());
        workRatingRepository.save(workRatingEntity);
    }

    @Transactional
    public void updateReview(UpdateReviewCommand cmd) {
        WorkReviewEntity workReviewEntity = workReviewRepository.findByUserIdAndWorkId(cmd.userId(), cmd.workId())
                .orElse(new WorkReviewEntity(userService.getReferenceById(cmd.userId()), workRepository.getReferenceById(cmd.workId())));
        workReviewEntity.setReview(cmd.review());
        workReviewRepository.save(workReviewEntity);
    }

    public String getReview(Long userId, Long workId) {
        return workReviewRepository.findByUserIdAndWorkId(userId, workId)
                .map(WorkReviewEntity::getReview)
                .orElse(null);
    }

    public WorkReviewLikes getLikesForReview(Long userId, Long workReviewId) {
        List<WorkReviewLikeEntity> workReviewLikeEntities = workReviewLikeRepository.findByWorkReviewId(workReviewId);
        List<Long> likedByUserIds = new ArrayList<>();
        boolean hasCurrentUserLiked = false;
        for (WorkReviewLikeEntity workReviewLikeEntity : workReviewLikeEntities) {
            likedByUserIds.add(workReviewLikeEntity.getUserEntity().getId());
            if (workReviewLikeEntity.getUserEntity().getId().equals(userId)) {
                hasCurrentUserLiked = true;
            }
        }
        return new WorkReviewLikes(workReviewId, hasCurrentUserLiked, likedByUserIds);
    }

    @Transactional
    public void addLike(Long userId, Long workReviewId) {
        workReviewLikeRepository.findByUserIdAndWorkReviewId(userId, workReviewId)
                .ifPresent(workReviewLikeEntity -> {
                    throw new WorkReviewLikeAlreadyExists(workReviewLikeEntity.getId());
                });
        WorkReviewLikeEntity workReviewLikeEntity = new WorkReviewLikeEntity();
        workReviewLikeEntity.setUserEntity(userService.getReferenceById(userId));
        workReviewLikeEntity.setWorkReviewEntity(workReviewRepository.getReferenceById(workReviewId));
        workReviewLikeRepository.save(workReviewLikeEntity);
    }

    @Transactional
    public void removeLike(Long userId, Long workReviewId) {
        WorkReviewLikeEntity workReviewLikeEntity = workReviewLikeRepository.findByUserIdAndWorkReviewId(userId, workReviewId)
                .orElseThrow(() -> new WorkReviewLikeDoesNotExist(userId, workReviewId));
        workReviewLikeRepository.delete(workReviewLikeEntity);
    }
}
