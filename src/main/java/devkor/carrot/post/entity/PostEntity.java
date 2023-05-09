package devkor.carrot.post.entity;

import devkor.carrot.user.UserEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "post")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "title")
    private String title;

    @Column(name ="thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    private UserEntity user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PhotoEntity> photos;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<BookmarkEntity> bookmarks;
}
