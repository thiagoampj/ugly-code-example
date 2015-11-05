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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.ParseException;
import org.junit.Assert;
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

    @InjectMocks
    PostController controller;

    @Mock
    PostService service;

    @Mock
    BlogPost post;

    @Test()
    public void shouldCreateBlogPostNicely() throws ParseException, IOException {
        when(request.getSession()).thenReturn(session);
        final BlogPost post = controller.createPost("What a nice day!");
        when(service.createPost(Mockito.any(BlogPost.class))).thenReturn(post);
        Assert.assertThat(post, notNullValue());
        Mockito.verify(service).createPost(post);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotCreatePostWhenUserIsNotLogged() throws ParseException, IOException {
        when(request.getSession()).thenReturn(null);
        try {
            final BlogPost post = controller.createPost("What a nice day!");
        } finally {
            Mockito.verifyZeroInteractions(service);
        }

    }
}
