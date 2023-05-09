package devkor.carrot.post;

import devkor.carrot.post.dto.ModifyPostDto;
import devkor.carrot.post.dto.PostInfoDto;
import devkor.carrot.post.dto.WritePostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService =postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostInfoDto> getPostInfoById(@PathVariable("id") Long id) {
        PostInfoDto post = postService.getPostInfoById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<NullValue> write(@RequestBody WritePostDto writePostDto) {
        postService.writePost(writePostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<NullValue> modify(@RequestBody ModifyPostDto modifyPostDto) {
        postService.modifyPost(modifyPostDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NullValue> delete(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // TODO : user id 다른 방식으로 받기
    @PostMapping("/bookmark/{id}/{userid}")
    public ResponseEntity<NullValue> addBookmark(@PathVariable("id") Long id, @PathVariable("userid") Long userid) {
        postService.addBookmark(userid, id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/bookmark/{id}/{userid}")
    public ResponseEntity<NullValue> deleteBookmark(@PathVariable("id") Long id, @PathVariable("userid") Long userid) {
        postService.removeBookmark(userid, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/search")
    public ResponseEntity<List<PostInfoDto>> search(@RequestParam("keyword") String keyword, @RequestParam("pageNo") Integer pageNo) {
        List<PostInfoDto> postInfoDtos = postService.searchPosts(keyword, pageNo);
        return new ResponseEntity<>(postInfoDtos, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PostInfoDto>> getPostList(@RequestParam("pageNo") Integer pageNo) {
        List<PostInfoDto> postInfoDtos = postService.sortPosts(pageNo);
        return new ResponseEntity<>(postInfoDtos, HttpStatus.OK);
    }

}
