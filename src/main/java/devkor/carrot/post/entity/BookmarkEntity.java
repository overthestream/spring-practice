package devkor.carrot.post.entity;

import devkor.carrot.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "bookmark")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private PostEntity post;

}
