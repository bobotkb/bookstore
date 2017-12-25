package com.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exception.UserException;
import com.service.UserService;

public class ActiveServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//用户激活的servlet，通过用户发送过来的激活码，查找对应的用户，并从session域中获取生成的激活码进行比对
		//如果相等，则将获取到的用户的state状态修改为1（即激活状态）
		
		//获取用户传过来的激活码
		String activeCode = request.getParameter("activeCode");
		
		//调用业务逻辑激活用户状态
		UserService userService = new UserService();
		try {
			userService.activeUser(activeCode);
			//激活成功跳转至首页
			request.getRequestDispatcher("/activesuccess.jsp").forward(request, response);
		} catch (UserException e) {
			e.printStackTrace();
			response.getWriter().write("激活失败");
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
