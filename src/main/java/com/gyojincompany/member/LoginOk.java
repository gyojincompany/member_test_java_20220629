package com.gyojincompany.member;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginOk")
public class LoginOk extends HttpServlet{
	
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	
	private String id, pw, name, email, phone, gender;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		actionDo(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		actionDo(req, resp);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");//한글깨짐 방지
		
		String mid = request.getParameter("userId");
		String mpw = request.getParameter("userPw");
		
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/odbo";
		String username = "root";
		String password = "12345";
		
		String sql = "SELECT * FROM testmember WHERE id = '"+mid+"' AND pw = '"+mpw+"'";
		
		try{
			Class.forName(driverName);//드라이버 로딩
			conn = DriverManager.getConnection(url, username, password);//데이터베이스 연동
			stmt = conn.createStatement();//sql을 실행해주는 statement 객체 생성
			
			rs = stmt.executeQuery(sql);			
	
			int loginFlag = 0;
			
			HttpSession session = request.getSession();
			
			while(rs.next()) {
				id = rs.getString("id");
				pw = rs.getString("pw");
				name = rs.getString("name");
				email = rs.getString("email");
				phone = rs.getString("phone");
				gender = rs.getString("gender");
				System.out.println("아이디:"+ id);
				
				session.setAttribute("name", name);
				session.setAttribute("id", id);
				session.setAttribute("pw", pw);
				loginFlag++;
			}
			
			if(loginFlag == 0) {
				response.sendRedirect("login.jsp");
			} else {			
				response.sendRedirect("loginSucess.jsp");
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) {
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}			
		
	}
}
