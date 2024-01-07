package org.example.jetty;

import jakarta.servlet.MultipartConfigElement;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.jetty.servlet.Base64DeSerializerServlet;
import org.example.jetty.servlet.BinaryDeSerializerServlet;

/**
 * @author Whoopsunix
 */
public class RunJetty {
    public static void main(String[] args) throws Exception {
        // 创建 Jetty 服务器
        Server server = new Server(8080);

        // 创建 Servlet 上下文处理器
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // 添加 Servlet 到上下文
        context.addServlet(new ServletHolder(new Base64DeSerializerServlet()), "/base64");

        ServletHolder binaryServletHolder = new ServletHolder(new BinaryDeSerializerServlet());
        binaryServletHolder.getRegistration().setMultipartConfig(createMultipartConfig());
        context.addServlet(binaryServletHolder, "/binary");

        // 将上下文添加到服务器
        server.setHandler(context);

        // 启动服务器
        server.start();
        server.join();
    }

    private static MultipartConfigElement createMultipartConfig() {
        return new MultipartConfigElement("/tmp", 1024 * 1024, 1024 * 1024 * 5, 1024 * 1024);
    }

}
