package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dto.QuestionAnswers;
import next.service.QnaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteQuestionController extends AbstractController {

    private QnaService qnaService;

    public DeleteQuestionController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionAnswers dto = qnaService.ready(questionId);
        try {
            qnaService.delete(questionId, req.getSession());
            return jspView("redirect:/");
        } catch(Exception e) {
            return jspView("show.jsp")
                    .addObject("question", dto.getQuestion())
                    .addObject("answers", dto.getAnswers())
                    .addObject("errorMessage", e.getMessage());
        }
    }
}
