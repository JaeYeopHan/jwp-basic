package core.support.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.controller.Controller;
import next.controller.HomeController;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String SESSION = "session";
    
    private RequestMapping rm;
    
    @Override
    public void init() throws ServletException {
    	rm = new RequestMapping();
    }
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
		resp.setContentType("text/html; charset=UTF-8");
		String servletPath = req.getServletPath();
		log.debug("Servlet path requested: {}", servletPath);

		try {
			Map<String, Object> modelAndView = new HashMap<>();
			modelAndView.put("session", req.getSession());
			Controller controller = rm.getController(servletPath);
			if (controller == null) {
				throw new Exception("Page Not found");
			}
			
			String path = controller.execute(modelAndView);
	
			for (String key : modelAndView.keySet()) {
				req.setAttribute(key, modelAndView.get(key));
			}
	
			if (path.startsWith(REDIRECT_PREFIX)) {
				resp.sendRedirect(path.substring(REDIRECT_PREFIX.length()));
				return;
			} else {
				RequestDispatcher rd = req.getRequestDispatcher(path);
				rd.include(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", e);
			RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
			rd.forward(req, resp);
		}
    }
}
