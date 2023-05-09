package devkor.carrot.user;

import devkor.carrot.post.entity.BookmarkEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Entity(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_no")
    private String phoneNo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BookmarkEntity> bookmarks;

}
