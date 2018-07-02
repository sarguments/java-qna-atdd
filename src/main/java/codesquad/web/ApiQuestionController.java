package codesquad.web;

import codesquad.CannotDeleteException;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.dto.QuestionDto;
import codesquad.security.LoginUser;
import codesquad.service.QnaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/qna")
public class ApiQuestionController {
    @Resource(name = "qnaService")
    private QnaService qnaService;

    @PostMapping("")
    public ResponseEntity<Question> create(@Valid @RequestBody QuestionDto question, @LoginUser User loginUser) {
        Question savedQuestion = qnaService.addQuestion(loginUser, question);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/qna/" + savedQuestion.getId()));
        return new ResponseEntity<>(savedQuestion, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public QuestionDto show(@PathVariable long id) {
        Question question = qnaService.findQuestionById(id).orElseThrow(EntityExistsException::new);
        return question.toQuestionDto();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody QuestionDto updateQuestion) {
        qnaService.update(loginUser, id, updateQuestion);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/qna/" + updateQuestion.getId()));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@LoginUser User loginUser, @PathVariable long id) throws CannotDeleteException {
        qnaService.deleteQuestion(loginUser, id);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
