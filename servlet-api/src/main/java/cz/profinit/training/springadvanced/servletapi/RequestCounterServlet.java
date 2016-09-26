package cz.profinit.training.springadvanced.servletapi;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestCounterServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());

        RequestCounterBean sessionRequestCounterBean = applicationContext.getBean("sessionRqCounter", RequestCounterBean.class);
        RequestCounterBean singletonRequestCounterBean = applicationContext.getBean("singletonRqCounter", RequestCounterBean.class);
        RequestCounterBean prototypeRequestCounterBean = applicationContext.getBean("prototypeRqCounter", RequestCounterBean.class);

        response.getWriter().write("Session counter: " + sessionRequestCounterBean.inc() + "\n");
        response.getWriter().write("Singleton counter: " + singletonRequestCounterBean.inc() + "\n");
        response.getWriter().write("Prototype counter: " + prototypeRequestCounterBean.inc() + "\n");
    }
}
