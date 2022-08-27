package DAO;

import java.sql.*;
import java.util.ArrayList;

import Entity.User;

public class UserDAO {
	/**
	 * get the list of users
	 * 
	 * @return the list of users
	 */
	public static ArrayList<User> getUsers() {
		String sql = "SELECT u.*, s.ip, s.port FROM user as u, server as s WHERE u.server_id = s.server_id";
		ArrayList<User> users = new ArrayList<User>();
		Connection conn = DAO.DataProvide.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();	
			while(rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFullName(rs.getString("full_name"));
				user.setStatus(rs.getInt("status"));
				user.setIp(rs.getString("ip"));
				user.setPort(rs.getInt("port"));
				users.add(user);
			}
			conn.close();
			return users;
		} catch (SQLException e) {
			System.out.println("Lấy danh sách user thất bại");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * get the information of user
	 * 
	 * @return user information
	 */
	public static User getUser(int userId) {
		String sql = "SELECT u.*, s.ip, s.port FROM user as u, server as s WHERE u.server_id = s.server_id AND u.user_id = " + userId;
		Connection conn = DAO.DataProvide.getConnection();
		User userInfo = new User();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();			
			if(rs.next()) {
				userInfo.setUserId(rs.getInt("user_id"));
				userInfo.setUsername(rs.getString("username"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setFullName(rs.getString("full_name"));
				userInfo.setStatus(rs.getInt("status"));
				userInfo.setIp(rs.getString("ip"));
				userInfo.setPort(rs.getInt("port"));
				conn.close();
				return userInfo;
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.out.println("Lấy thông tin User thất bại");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * update the status of user
	 * 
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) statements 
	 * or (2) 0 for SQL statements that return nothing
	 */
	public static int updateUserStatus(int userId, int status) {
		String sql = "UPDATE user SET status = ? WHERE user_id = ?";
		Connection conn = DAO.DataProvide.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setInt(2, userId);
			int result = ps.executeUpdate();
            conn.close();
            return result;
		} catch (SQLException e) {
			System.out.println("Cập nhật trạng thái User thất bại!");
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * update the password of user
	 * 
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) statements 
	 * or (2) 0 for SQL statements that return nothing
	 */
	public static int updateUserPassword(int userId, String newPassword, int status) {
		String sql = "UPDATE user SET password = ?, status = ? WHERE user_id = ?";
		Connection conn = DAO.DataProvide.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newPassword);
			ps.setInt(2, status);
			ps.setInt(3, userId);
			int result = ps.executeUpdate();
            conn.close();
            return result;
		} catch (SQLException e) {
			System.out.println("Cập nhật mật khẩu User thất bại!");
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * insert new user
	 * 
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) statements 
	 * or (2) 0 for SQL statements that return nothing
	 */
	public static int insertNewUser(User newUser, int serverId) {
        Connection con = DataProvide.getConnection();
        String sql = "INSERT INTO user (username, password, full_name, status, server_id) VALUES (?,?,?,?,?)";
		try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newUser.getUsername());
            ps.setString(2, newUser.getPassword());
            ps.setString(3, newUser.getFullName());
            ps.setInt(4, newUser.getStatus());
            ps.setInt(5, serverId);
            int result = ps.executeUpdate();
            con.close();
            return result;
        } catch (Exception e) {
        	System.out.println("Thêm User thất bại!");
			e.printStackTrace();
            return 0;
        }
	}
}
