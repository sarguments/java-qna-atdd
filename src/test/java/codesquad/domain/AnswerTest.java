package codesquad.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AnswerTest {
    @Test
    public void delete() {
        User user = new User("test", "test", "test", "test@test.com");
        Answer toDeleteAnswer = new Answer(user, "comment");
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.ANSWER, toDeleteAnswer.getId(), toDeleteAnswer.getWriter());
        assertThat(toDeleteAnswer.delete(), is(deleteHistory));
        assertTrue(toDeleteAnswer.isDeleted());
    }

    @Test(expected = IllegalAccessError.class)
    public void delete_false() {
        User user = new User("test", "test", "test", "test@test.com");
        Answer toDeleteAnswer = new Answer(user, "comment");
        toDeleteAnswer.delete();
        toDeleteAnswer.delete();
    }
}
