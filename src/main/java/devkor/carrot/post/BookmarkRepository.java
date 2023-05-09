package devkor.carrot.post;

import devkor.carrot.post.entity.BookmarkEntity;
import devkor.carrot.post.entity.PostEntity;
import devkor.carrot.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
    Optional<BookmarkEntity> findByUserIdAndPostId(Long userId, Long postId);
}
