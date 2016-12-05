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
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());

        final RequestCounterBean sessionScoped = ctx.getBean("sessionRqCounter", RequestCounterBean.class);
        final RequestCounterBean singletonScoped = ctx.getBean("singletonRqCounter", RequestCounterBean.class);
        final RequestCounterBean prototypeScoped = ctx.getBean("prototypeRqCounter", RequestCounterBean.class);

        response.getWriter().write("Session counter: " + sessionScoped.inc() + "\n");
        response.getWriter().write("Singleton counter: " + singletonScoped.inc() + "\n");
        response.getWriter().write("Prototype counter: " + prototypeScoped.inc() + "\n");
    }
}
