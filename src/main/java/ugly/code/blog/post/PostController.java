package ugly.code.blog.post;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.ParseException;

/**
 * Blogs are created
 *
 * @author thiagoampj
 *
 */
public class PostController {

    @Inject
    HttpServletRequest request;

    @Inject
    PostService service;

    public BlogPost createPost(final String message) throws ParseException, IOException {

        if (request.getSession() == null) {
            throw new IllegalStateException("There is no session ! ");
        }
        final BlogPost post = new BlogPost();
        post.setPost(message);
        post.setAuthor((User) request.getSession().getAttribute("user"));
        service.createPost(post);
        return post;
    }

    public long addComment(final Long postId, final String comment) {

        return 123123L;
    }

    public void like(final Long postId) {

    }

    public void likeComment(final Comment comment) {

    }

}
