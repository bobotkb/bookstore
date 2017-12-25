package com.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.domain.User;
import com.util.C3P0Util;

public class UserDao {

	public void add(User user) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "INSERT INTO USER(username, PASSWORD, gender, email, telephone, introduce, activeCode, state, registTime) VALUES(?,?,?,?,?,?,?,?,?)";
		queryRunner.update(sql, user.getUsername(), user.getPassword(),
				user.getGender(), user.getEmail(), user.getTelephone(),
				user.getIntroduce(), user.getActiveCode(), user.getState(),
				user.getRegistTime());

	}
	
	/**
	 * 通过激活码查找用户
	 * @param activeCode
	 * @return
	 * @throws SQLException 
	 */
	public User findUserByActiveCode(String activeCode) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "SELECT * FROM USER WHERE activeCode=?";
		return queryRunner.query(sql , new BeanHandler<User>(User.class), activeCode);
	}
	
	/**
	 * 修改查找到用户的状态
	 *  @param user
	 * @throws SQLException 
	 */
	public void active(User user) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "UPDATE USER SET state = 1 WHERE activeCode = ?";
		queryRunner.update(sql, user.getActiveCode());
	}

}
