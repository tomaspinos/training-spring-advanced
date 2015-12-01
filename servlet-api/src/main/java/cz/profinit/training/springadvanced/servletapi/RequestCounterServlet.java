package cz.profinit.training.springadvanced.servletapi;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import java.io.IOException;

public class RequestCounterServlet extends javax.servlet.http.HttpServlet {

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());

        RequestCounterBean sessionRequestCounterBean = webApplicationContext.getBean("sessionRqCounter", RequestCounterBean.class);
        RequestCounterBean singletonRequestCounterBean = webApplicationContext.getBean("singletonRqCounter", RequestCounterBean.class);
        RequestCounterBean prototypeRequestCounterBean = webApplicationContext.getBean("prototypeRqCounter", RequestCounterBean.class);

        response.getWriter().write("Session counter: " + sessionRequestCounterBean.inc() + "\n");
        response.getWriter().write("Singleton counter: " + singletonRequestCounterBean.inc() + "\n");
        response.getWriter().write("Prototype counter: " + prototypeRequestCounterBean.inc() + "\n");

    }
}
