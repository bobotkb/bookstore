package com.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

import com.dao.UserDao;
import com.domain.User;
import com.exception.UserException;
import com.util.SendJMail;

public class UserService {
	/**
	 * 获取本机的IP地址
	 * @param args
	 * @return
	 */
	public String getLocalhostIpAddress() {
		try {
			InetAddress ipAddress = InetAddress.getLocalHost();
			String ip = ipAddress.toString();
			String[] ips = ip.split("/");
			return ips[1];
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void regist(User user) throws UserException {
		UserDao userDao = new UserDao();
		try {
			userDao.add(user);
			String ip = getLocalhostIpAddress();
			// 注册成功之后向注册的邮箱中发邮件
			String emailMsg = "注册成功，请<a href='http://" + ip + ":8080/book/active?activeCode="
					+ user.getActiveCode() + "'>激活</a>后登录";// 邮箱中要发送的邮件内容
			SendJMail.sendMail(user.getEmail(), emailMsg);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("注册失败");
		}
	}

	public void activeUser(String activeCode) throws UserException {
		UserDao userDao = new UserDao();
		//通过激活码查找用户
		try {
			User user = userDao.findUserByActiveCode(activeCode);
			if (user != null) {
				//激活用户
				userDao.active(user);
				
				return;
			}
			//激活失败，抛出异常
			throw new UserException("激活失败!");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("激活失败!");
		}
	}

}
