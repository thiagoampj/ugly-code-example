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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicStatusLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession session;

    @Mock
    HttpClient client;

    @Mock
    HttpResponse response;

    @InjectMocks
    PostController controller;

    @Mock
    HttpEntity entity;

    @Test()
    public void test_PostController_createPost() throws ParseException, IOException {
        when(request.getSession()).thenReturn(session);
        when(client.execute(Mockito.any(HttpPost.class))).thenReturn(response);
        when(response.getEntity()).thenReturn(entity);
        when(response.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, 200, null));
        when(entity.getContent()).thenReturn(new ByteArrayInputStream("4".getBytes()));
        final BlogPost post = controller.createPost("What a nice day!");
        assertTrue(post.getId().equals(4L));
    }

    @Test
    public void test_PostControlloer_createPost2() throws ParseException, IOException {
        when(request.getSession()).thenReturn(session);
        when(client.execute(Mockito.any(HttpPost.class))).thenReturn(response);
        when(response.getEntity()).thenReturn(entity);
        when(response.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, 200, null));
        when(entity.getContent()).thenReturn(new ByteArrayInputStream("G".getBytes()));
        final BlogPost post = controller.createPost("What a nice day 2!");
        assertTrue(post.getId().equals(4L));
    }
}
