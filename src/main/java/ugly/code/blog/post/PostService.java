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

import java.io.IOException;

import javax.inject.Inject;

import org.apache.http.client.ClientProtocolException;

public class PostService {

    @Inject
    PostRepository repository;

    BlogPost createPost(final BlogPost post) throws IOException, ClientProtocolException {
        return repository.persist(post);

    }

    BlogPost getPost(final long postId) throws ClientProtocolException, IOException {
        return repository.getPost(postId);
    }
}
