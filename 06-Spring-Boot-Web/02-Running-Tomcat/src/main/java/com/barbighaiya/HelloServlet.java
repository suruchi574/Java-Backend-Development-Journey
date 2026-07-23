package com.barbighaiya;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req,
                           HttpServletResponse res)
            throws IOException {

        System.out.println("Hello World from Servlet");
        res.getWriter().println("Hello World from Servlet");
    }
}

///*
// * The purpose of this Servlet is to greet the users
// * We can convert the normal class to Servlet Class 
// * By extending the HttpServlet class */
//public class HelloServlet extends HttpServlet {
//	/* service():
//	 * service() is one of the important method in servlet
//	 * which will get executed when we will get response from servlet
//	 * It accepts two objects of classes HttpServletRequest and HttpServletResponse
//	 * HttpServletRequest  req : The data  will be coming from client
//	 * HttpServletResponse res : The data which we will be sending to the server
//	 * */
//	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException
//	{
//		System.out.println("Hello world from servlet");
//		res.getWriter().println("Hello World from Servlet");
//	}
//	
//}
