package devkor.carrot.post.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostInfoDto {
    private String category;
    private String title;
    private String thumbnailUrl;
    private String description;
    private Integer price;
    private Integer viewCount;

    private Boolean status;

    private Long id;
}
