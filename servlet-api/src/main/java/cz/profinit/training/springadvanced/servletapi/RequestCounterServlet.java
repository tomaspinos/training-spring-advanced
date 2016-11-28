package cz.profinit.training.springadvanced.servletapi;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class RequestCounterServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());

        final RequestCounterBean sessionRequestCounterBean = applicationContext.getBean("sessionRqCounter", RequestCounterBean.class);
        final RequestCounterBean singletonRequestCounterBean = applicationContext.getBean("singletonRqCounter", RequestCounterBean.class);
        final RequestCounterBean prototypeRequestCounterBean = applicationContext.getBean("prototypeRqCounter", RequestCounterBean.class);

        response.getWriter().write("Session counter: " + sessionRequestCounterBean.inc() + "\n");
        response.getWriter().write("Singleton counter: " + singletonRequestCounterBean.inc() + "\n");
        response.getWriter().write("Prototype counter: " + prototypeRequestCounterBean.inc() + "\n");
    }
}
