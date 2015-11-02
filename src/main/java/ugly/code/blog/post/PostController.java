package ugly.code.blog.post;

import static java.lang.Long.parseLong;
import static org.apache.http.impl.client.HttpClients.createDefault;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author thiagoampj
 *
 */
public class PostController {

    @Inject
    HttpServletRequest request;

    public BlogPost createPost(final String message) throws ParseException, IOException {
        // Instantiate
        if (request.getSession() == null) {
            throw new IllegalStateException("There is no session ! ");
        }
        final BlogPost post = new BlogPost();
        post.setPost(message);
        post.setAuthor((User) request.getSession().getAttribute("user"));
        //Using Http Client
        final CloseableHttpClient httpclient = createDefault();
        final HttpPost httpPost = new HttpPost("http://localhost:9009/db/data");
        //Sending post
        final CloseableHttpResponse response = httpclient.execute(httpPost);
        //Should be 200
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new IllegalStateException("Something wrong happened! Call 0800 7070 7070123");
        }
        //Returns only the id
        final String id = EntityUtils.toString(response.getEntity());
        post.setId(parseLong(id));
        return post;
    }

    public void addComment(final Long postId, final String comment) {

    }

    public void like(final Long postId) {

    }

    public void likeComment(final Comment comment) {

    }

}
