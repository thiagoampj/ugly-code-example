package ugly.code.blog.post;

import static java.lang.Long.parseLong;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PostRepository {

    public static final String BASE_URL = "http://localhost:9009/posts";
    @Inject
    HttpClient httpClient;

    @Inject
    private ObjectMapper mapper;

    /**
     * @param post
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public BlogPost persist(final BlogPost post) throws ClientProtocolException, IOException {
        final HttpRequestBase httpPost = createRequest(post);
        final HttpResponse response = getResponse(httpPost);
        final String id = extractResponse(response);
        post.setId(parseLong(id));
        return post;
    }

    String extractResponse(final HttpResponse response) throws IOException {
        final String id = EntityUtils.toString(response.getEntity());
        return id;
    }

    HttpRequestBase createRequest(final BlogPost post) throws UnsupportedEncodingException, JsonProcessingException {
        final HttpPost httpPost = new HttpPost(BASE_URL);
        final StringEntity entity = new StringEntity(mapper.writeValueAsString(post));
        httpPost.setEntity(entity);
        return httpPost;
    }

    HttpResponse getResponse(final HttpRequestBase request) throws ClientProtocolException, IOException {
        final HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new IllegalStateException("Something wrong happened! Call 0800 7070 7070123");
        }
        return response;
    }

    /**
     * @param l
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public BlogPost getPost(final long postId) throws ClientProtocolException, IOException {
        final HttpGet httpGet = new HttpGet(BASE_URL + "/" + postId);
        final HttpResponse response = getResponse(httpGet);
        final String blogPostAsString = extractResponse(response);
        return mapper.convertValue(blogPostAsString, BlogPost.class);
    }

}
