package core.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private static final int COUNT_OF_HANDLER_MAPPINGS = 2;
    private static final String BASE_PACKAGE = "next.controller";

    private List<HandlerMapping> handlerMappings = new ArrayList<>(COUNT_OF_HANDLER_MAPPINGS);

    private LegacyHandlerMapping lrm;
    private AnnotationHandlerMapping ahm;

    @Override
    public void init() throws ServletException {
        ahm = new AnnotationHandlerMapping(BASE_PACKAGE);
        ahm.initialize();

        lrm = new LegacyHandlerMapping();
        lrm.initMapping();

        handlerMappings.add(ahm);
        handlerMappings.add(lrm);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);
        try {
            ModelAndView mav = null;
            for (HandlerMapping mapper : handlerMappings) {
                Object handler = mapper.getHandler(req);
                logger.debug("handler : {}", handler);
                if (handler instanceof Controller) {
                    mav = ((Controller) handler).execute(req, resp);
                } else if (handler instanceof HandlerExecution) {
                    mav = ((HandlerExecution) handler).handle(req, resp);
                }
            }
            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        } catch (Exception e) {
            logger.error("Exception : {}", e);
        }
    }
}
