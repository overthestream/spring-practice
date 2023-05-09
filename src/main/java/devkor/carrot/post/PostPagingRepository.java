package devkor.carrot.post;

import devkor.carrot.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostPagingRepository extends PagingAndSortingRepository<PostEntity, Long> {
    Page<PostEntity> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description, Pageable pageable);
}
