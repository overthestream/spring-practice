package devkor.carrot.post.dto;

import lombok.Getter;

@Getter
public class ModifyPostDto {
    private String category;
    private String title;
    private String thumbnailUrl;
    private String description;
    private Integer price;
    private Long id;
}
