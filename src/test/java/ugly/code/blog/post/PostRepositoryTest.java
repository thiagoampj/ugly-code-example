/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2015
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package ugly.code.blog.post;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class PostRepositoryTest {

    @Mock
    HttpClient client;

    @Mock
    HttpEntity entity;

    @Mock
    HttpResponse response;

    @Mock
    StatusLine status;

    @InjectMocks
    PostRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    public void shouldCreateBlogPost() throws ClientProtocolException, IOException {
        final BlogPost post = createPost();
        when(client.execute(Mockito.any(HttpPost.class))).thenReturn(response);
        when(response.getStatusLine()).thenReturn(status);
        when(status.getStatusCode()).thenReturn(200);

        when(response.getEntity()).thenReturn(entity);
        when(mapper.writeValueAsString(post)).thenReturn("{}");
        when(entity.getContent()).thenReturn(new ByteArrayInputStream("100".getBytes()));
        final BlogPost createPost = repository.persist(post);
        assertThat(createPost, CoreMatchers.notNullValue());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldHandleWrongResponseFromHttp() throws ClientProtocolException, IOException {
        final BlogPost post = createPost();
        when(client.execute(Mockito.any(HttpPost.class))).thenReturn(response);
        when(mapper.writeValueAsString(post)).thenReturn("{}");
        when(response.getStatusLine()).thenReturn(status);
        when(status.getStatusCode()).thenReturn(500);
        try {
            final BlogPost createPost = repository.persist(post);
        } finally {
            Mockito.verifyZeroInteractions(entity);
        }
    }

    @Test
    public void shouldGetPostWithValidId() throws ClientProtocolException, IOException {
        when(client.execute(Mockito.any(HttpGet.class))).thenReturn(response);
        when(response.getEntity()).thenReturn(entity);
        when(response.getStatusLine()).thenReturn(status);
        when(status.getStatusCode()).thenReturn(200);
        when(mapper.convertValue(Mockito.any(Object.class), Mockito.eq(BlogPost.class))).thenReturn(createPost());

        final BlogPost post = repository.getPost(123L);

        assertNotNull(post);
        assertThat(post.getId(), CoreMatchers.is(123L));
    }

    /**
     * @return
     */
    private BlogPost createPost() {

        final BlogPost post = new BlogPost();
        post.setId(123L);
        post.setPost("Hello World");
        return post;
    }
}
