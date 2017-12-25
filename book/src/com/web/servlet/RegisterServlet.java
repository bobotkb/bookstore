package com.web.servlet;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import com.domain.User;
import com.exception.UserException;
import com.service.UserService;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取表单输入的验证码
		String ckcode = request.getParameter("ckcode");
		//获取生成并写入到session中的验证码
		String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
		
		//如果验证码为空输出提示信息，并跳转至注册页面
		if(ckcode == null || ckcode.isEmpty()){
			request.setAttribute("ckcode_msg", "验证码不能为空");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		//如果验证码不正确输出提示信息，并跳转至注册页面
		if(!checkcode_session.equals(ckcode)){
			request.setAttribute("ckcode_msg", "验证码不正确");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		
		//验证码正确获取参数并封装
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			//生成并添加激活码
			user.setActiveCode(UUID.randomUUID().toString());
			//执行业务逻辑
			UserService userService = new UserService();
			userService.regist(user);
			//添加成功后跳转到注册成功的页面
			request.getRequestDispatcher("/registersuccess.jsp").forward(request, response);
		} catch (UserException e){
			request.setAttribute("user_msg", e.getMessage());
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
