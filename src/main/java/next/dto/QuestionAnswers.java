package next.dto;

import next.model.Answer;
import next.model.Question;

import java.util.List;

/**
 * Created by Jbee on 2017. 4. 21..
 */
public class QuestionAnswers {
    private Question question;
    private List<Answer> answers;

    public QuestionAnswers(Question question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }

    public Question getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
