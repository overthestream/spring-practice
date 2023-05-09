package devkor.carrot.post;

import devkor.carrot.post.dto.ModifyPostDto;
import devkor.carrot.post.dto.PostInfoDto;
import devkor.carrot.post.dto.WritePostDto;
import devkor.carrot.post.entity.BookmarkEntity;
import devkor.carrot.post.entity.PostEntity;
import devkor.carrot.user.UserEntity;
import devkor.carrot.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("postService")
public class PostService {
    private final PostRepository postRepository;
    private final PostPagingRepository postPagingRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private static final int PAGE_SIZE = 20;

    @Autowired
    public PostService(PostRepository postRepository, PostPagingRepository postPagingRepository, UserRepository userRepository, BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.postRepository = postRepository;
        this.postPagingRepository = postPagingRepository;
        this.userRepository = userRepository;
    }

    public PostInfoDto getPostInfoById(Long id) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(id);
        if(postEntityOptional.isEmpty()) {
            // TODO
            throw new Error();
        }
        PostEntity post = postEntityOptional.get();
        Integer viewCount = post.getViewCount();
        post.setViewCount(viewCount + 1);
        postRepository.save(post);

        return PostInfoDto.builder()
                .price(post.getPrice())
                .title(post.getTitle())
                .description(post.getDescription())
                .status(post.getStatus())
                .category(post.getCategory())
                .thumbnailUrl(post.getThumbnailUrl())
                .id(post.getId())
                .viewCount(post.getViewCount())
                .build();
    }

    public void writePost(WritePostDto writePostDto) {
        Optional<UserEntity> user = userRepository.findById(writePostDto.getUserId());
        if(user.isEmpty()){
            // TODO
            throw new Error();
        }

        PostEntity newPost = PostEntity.builder()
                .price(writePostDto.getPrice())
                .status(false)
                .category(writePostDto.getCategory())
                .thumbnailUrl(writePostDto.getThumbnailUrl())
                .title(writePostDto.getTitle())
                .description(writePostDto.getDescription())
                .viewCount(0)
                .user(user.get())
                .build();

        postRepository.save(newPost);
    }

    public void modifyPost(ModifyPostDto modifyPostDto) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(modifyPostDto.getId());
        if(postEntityOptional.isEmpty()) {
            //TODO
            throw new Error();
        }

        PostEntity post = postEntityOptional.get();

        post.setCategory(modifyPostDto.getCategory());
        post.setDescription(modifyPostDto.getDescription());
        post.setPrice(modifyPostDto.getPrice());
        post.setTitle(modifyPostDto.getTitle());
        post.setThumbnailUrl(modifyPostDto.getThumbnailUrl());

        postRepository.save(post);

    }

    public void deletePost(Long id){
        Optional<PostEntity> post = postRepository.findById(id);
        if(post.isEmpty()){
            //TODO
            throw new Error();
        }

        postRepository.delete(post.get());
    }

    public void addBookmark(Long userId, Long postId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()){
            // TODO
            throw new Error();
        }

        Optional <PostEntity> post = postRepository.findById(postId);
        if(post.isEmpty()) {
            // TODO
            throw new Error();
        }

        BookmarkEntity newBookmark = BookmarkEntity.builder()
                .post(post.get())
                .user(user.get())
                .build();
        bookmarkRepository.save(newBookmark);
    }
    public void removeBookmark(Long userId, Long postId) {
        Optional<BookmarkEntity> bookmark = bookmarkRepository.findByUserIdAndPostId(userId, postId);
        if(bookmark.isEmpty()){
            //TODO
            throw new Error();
        }
        bookmarkRepository.delete(bookmark.get());
    }

    public List<PostInfoDto> searchPosts(String searchQuery, Integer pageNo){
        Page<PostEntity> posts = postPagingRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(searchQuery, searchQuery, PageRequest.of(pageNo, PAGE_SIZE, Sort.by("title").ascending()));
        Page<PostInfoDto> postInfoDtos = posts.map(post -> PostInfoDto.builder()
                .price(post.getPrice())
                .category(post.getCategory())
                .status(post.getStatus())
                .title(post.getTitle())
                .description(post.getDescription())
                .thumbnailUrl(post.getThumbnailUrl())
                .id(post.getId())
                .build()
        );
        return postInfoDtos.getContent();
    }

    public List<PostInfoDto> sortPosts(Integer pageNo){
        Page<PostEntity> posts = postPagingRepository.findAll(PageRequest.of(pageNo, PAGE_SIZE, Sort.by("viewCount").descending()));
        System.out.println(pageNo);
        System.out.println(posts);
        Page<PostInfoDto> postInfoDtos = posts.map(post -> PostInfoDto.builder()
                .price(post.getPrice())
                .category(post.getCategory())
                .status(post.getStatus())
                .title(post.getTitle())
                .description(post.getDescription())
                .thumbnailUrl(post.getThumbnailUrl())
                .id(post.getId())
                .build()
        );
        return postInfoDtos.getContent();
    }
}
