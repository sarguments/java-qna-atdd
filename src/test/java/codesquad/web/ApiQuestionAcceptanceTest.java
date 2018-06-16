package codesquad.web;

import codesquad.domain.User;
import codesquad.dto.QuestionDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApiQuestionAcceptanceTest extends AcceptanceTest {
    @Test
    public void create() throws Exception {
        // TODO id 수동지정 해야하나?
        QuestionDto newQuestion = new QuestionDto(3L, "testtitle", "testContents");
        String location = createResource("/api/qna", newQuestion);
        QuestionDto dbQuestion = basicAuthTemplate().getForObject(location, QuestionDto.class);
        assertThat(dbQuestion, is(newQuestion));
    }

    @Test
    public void create_guest() throws Exception {
        QuestionDto newQuestion = new QuestionDto(4L, "testtitle", "testContents");
        ResponseEntity<String> response = basicAuthTemplate(User.GUEST_USER).postForEntity("/api/qna", newQuestion, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void update() throws Exception {
        // TODO 인자 순서 문제?
        QuestionDto newQuestion = new QuestionDto(5L, "testtitle", "testContents");
        String location = createResource("/api/qna", newQuestion);

        QuestionDto updateQuestion = new QuestionDto(newQuestion.getId(), "aaaaa", "sssss");
        basicAuthTemplate().put(location, updateQuestion);

        QuestionDto dbQuestion = basicAuthTemplate().getForObject(location, QuestionDto.class);
        assertThat(dbQuestion, is(updateQuestion));
    }

    @Test
    public void update_guest() throws Exception {
        QuestionDto newQuestion = new QuestionDto(6L, "testtitle", "testContents");
        String location = createResource("/api/qna", newQuestion);

        QuestionDto updateQuestion = new QuestionDto(newQuestion.getId(), "aaaaa", "sssss");
        basicAuthTemplate(User.GUEST_USER).put(location, updateQuestion);

        QuestionDto dbQuestion = basicAuthTemplate().getForObject(location, QuestionDto.class);

        // 바뀌었나 안바뀌었나 테스트
        assertThat(dbQuestion, is(newQuestion));
    }

    @Test
    public void delete() {
        QuestionDto newQuestion = new QuestionDto(7L, "testtitle", "testContents");
        String location = createResource("/api/qna", newQuestion);

        basicAuthTemplate().delete(location);
    }
}
