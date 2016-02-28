package com.bjwg.back.test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Demo {

	static String sql = null;
	static DBHelper db1 = null;
	static ResultSet ret = null;

	public static void main(String[] args) {
//		sql = "select * from bjwg_role";//SQL语句
		sql = "insert into bjwg_role(role_name,description) values('测试','JDBC测试有没有乱码')";
		db1 = new DBHelper(sql);//创建DBHelper对象

		try {
//			db1.pst.set
			db1.pst.executeUpdate(sql);
//			ret = db1.pst.executeQuery();//执行语句，得到结果集
//			while (ret.next()) {
////				String uid = ret.getString(1);
//				String ufname = ret.getString(2);
//				String ulname = ret.getString(3);
////				String udate = ret.getString(4);
//				System.out.println(ufname + "\t" + ulname + "\t" );
//			}//显示数据
//			ret.close();
			db1.close();//关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
