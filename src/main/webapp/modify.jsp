<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보수정</title>
</head>
<body>
	<%	
		String id=null;
		String pw=null;
		String name=null;
		String email=null;
		String phone=null;
		String gender=null;
	
		request.setCharacterEncoding("utf-8");//한글깨짐 방지
		
		String mid = (String)session.getAttribute("id");//세션에서 id 값 불러오기
		
		//data source 설정
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/odbo";
		String username = "root";
		String password = "12345";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM testmember WHERE id = '"+mid+"'";
		
			
		Class.forName(driverName);//드라이버 로딩
		conn = DriverManager.getConnection(url, username, password);//데이터베이스 연동
		stmt = conn.createStatement();//sql을 실행해주는 statement 객체 생성
			
		rs = stmt.executeQuery(sql);			
		while(rs.next()) {
				id = rs.getString("id");
				pw = rs.getString("pw");
				name = rs.getString("name");
				email = rs.getString("email");
				phone = rs.getString("phone");
				gender = rs.getString("gender");
		}
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
	
	%>

	<h2>회원 정보 수정</h2>
	<hr>
	<form action="modifyOk.jsp" method="post">
		아이디 : <input type="text" name="memberId" value="<%= id %>" readonly><br><br>
		비밀번호 : <input type="password" name="memberPw">※가입시 입력한 비밀번호를 입력해주세요.<br><br>
		이름 : <input type="text" name="memberName" value="<%= name %>"><br><br>
		이메일 : <input type="text" name="memberEmail" value="<%= email %>"><br><br>
		전화번호 : <input type="text" name="memberPhone" value="<%= phone %>"><br><br>
		<%
			if(gender.equals("man")) {
		%>		
		성별 : <input type="radio" name="memberGender" value="man" checked="checked">남 &nbsp;
		<input type="radio" name="memberGender" value = "woman">여 <br><br>
		<%
			} else {		
		%>
		성별 : <input type="radio" name="memberGender" value="man">남 &nbsp;
		<input type="radio" name="memberGender" value = "woman" checked="checked">여 <br><br>
		<%
			}
		%>		
		
		
		<hr>
		<input type="submit" value="수정완료">

	</form>	
</body>
</html>