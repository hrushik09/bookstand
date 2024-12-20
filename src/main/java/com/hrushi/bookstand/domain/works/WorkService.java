package com.hrushi.bookstand.domain.works;

import com.hrushi.bookstand.domain.Shelf;
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
    private static final String L_SUFFIX = "-L.jpg";
    private static final String S_SUFFIX = "-S.jpg";
    private final WorkRepository workRepository;
    private final AuthorService authorService;
    private final WorkRatingRepository workRatingRepository;
    private final UserService userService;
    private final WorkReviewRepository workReviewRepository;
    private final WorkReviewLikeRepository workReviewLikeRepository;
    private final WorkShelfRepository workShelfRepository;

    WorkService(WorkRepository workRepository, AuthorService authorService, WorkRatingRepository workRatingRepository, UserService userService, WorkReviewRepository workReviewRepository, WorkReviewLikeRepository workReviewLikeRepository, WorkShelfRepository workShelfRepository) {
        this.workRepository = workRepository;
        this.authorService = authorService;
        this.workRatingRepository = workRatingRepository;
        this.userService = userService;
        this.workReviewRepository = workReviewRepository;
        this.workReviewLikeRepository = workReviewLikeRepository;
        this.workShelfRepository = workShelfRepository;
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

    private static String getCoverUrl(WorkEntity workEntity, String suffix) {
        if (workEntity.getCoverId() == null) {
            return "";
        }
        return BASE_URL + workEntity.getCoverId() + suffix;
    }

    public Work getWork(Long userId, Long workId) {
        WorkEntity workEntity = workRepository.findWork(workId)
                .orElseThrow(() -> new WorkDoesNotExist(workId));
        Integer rating = getRating(userId, workId);
        String currentUserReview = getReview(userId, workId);
        List<WorkReview> allOtherReviews = getAllOtherReviews(userId, workId);
        String coverUrl = getCoverUrl(workEntity, L_SUFFIX);
        List<WorkAuthor> workAuthors = getWorkAuthors(workEntity);
        String shelf = getShelf(userId, workId);
        Long currentlyReading = workShelfRepository.findWorkShelfCount(workId, Shelf.CURRENTLY_READING);
        Long wantToRead = workShelfRepository.findWorkShelfCount(workId, Shelf.WANT_TO_READ);
        return new Work(workEntity.getId(), workEntity.getTitle(), workEntity.getSubtitle(), coverUrl, rating, currentUserReview, workAuthors, allOtherReviews, shelf, currentlyReading, wantToRead);
    }

    private String getShelf(Long userId, Long workId) {
        return workShelfRepository.findByUserIdAndWorkId(userId, workId)
                .map(workShelfEntity -> workShelfEntity.getShelf().displayName())
                .orElse("");
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

    @Transactional
    public String updateShelf(UpdateShelfCommand cmd) {
        WorkShelfEntity workShelfEntity = workShelfRepository.findByUserIdAndWorkId(cmd.userId(), cmd.workId())
                .orElse(new WorkShelfEntity(userService.getReferenceById(cmd.userId()), workRepository.getReferenceById(cmd.workId())));
        Shelf shelf = Shelf.valueOf(cmd.shelf());
        workShelfEntity.setShelf(shelf);
        workShelfRepository.save(workShelfEntity);
        return shelf.displayName();
    }

    public AllShelves getAllShelves(Long userId) {
        List<ShelfItem> currentlyReading = getShelfFor(userId, Shelf.CURRENTLY_READING);
        List<ShelfItem> wantToRead = getShelfFor(userId, Shelf.WANT_TO_READ);
        return new AllShelves(currentlyReading, wantToRead);
    }

    private List<ShelfItem> getShelfFor(Long userId, Shelf shelf) {
        return workShelfRepository.findByUserIdAndShelf(userId, shelf).stream()
                .limit(10)
                .map(workShelfEntity -> new ShelfItem(workShelfEntity.getWorkEntity().getId(), getCoverUrl(workShelfEntity.getWorkEntity(), S_SUFFIX), workShelfEntity.getWorkEntity().getTitle()))
                .toList();
    }
}
