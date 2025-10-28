package by.tms.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    //Возвращает массив классов конфигурации для корневого контекста
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    //Возвращает массив классов конфигурации для контекста DispatcherServlet
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    //Возвращает массив строк которые будет мапить DispatcherServlet
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
