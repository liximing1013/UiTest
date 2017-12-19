package tv.fun.appsautotest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Mysql utilities Class
// Author: pengww
// Modified by xuzx at 2016.10.21
public class MysqlUtils {
	public Connection m_Conn = null;
	private final static String m_sDBServer = "172.17.12.41:3306";  //数据库服务器地址
	private final static String m_sUser= "qa";                         //登录名
	private final static String m_sPassword = "123456";               //密码
	/**
	 * 获取测试数据库链接
	 * @param sDBName 数据库名
	 */
	public Connection getDBConnection(String sDBName){
		return getConnection(m_sDBServer,m_sUser,m_sPassword, sDBName);
	}

	private Connection getConnection(String server, String user, String password, String dbName){
		String sMysqlConn = "jdbc:mysql://" + server + "/" +
                dbName + "?characterEncoding=UTF-8";
		try{
			// 加载驱动程序以连接数据库  
			Class.forName("com.mysql.jdbc.Driver");
			//m_Conn = DriverManager.getConnection(sMysqlConn, user, password);
            Connection conn = DriverManager.getConnection("jdbc:mysql://172.17.12.41:3306/qa?user=qa&password=123456&useUnicode=true&characterEncoding=UTF8");
            conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return m_Conn;
	}

	/**
	 * 断开数据库链接
	 * @param conn db连接
	 */
	public static boolean deconnMysql(Connection conn) {
		try{
			if(conn != null){
				conn.close();
				return true;
			} else {
				return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 执行查询
	 * @param conn db连接
	 * @param sSQL sql语句
	 */
	public static ResultSet querySQL(Connection conn, String sSQL) {  
        ResultSet rs = null;
        try {
        	PreparedStatement stmt = conn.prepareStatement(sSQL);  
            rs = stmt.executeQuery(sSQL);
        } catch (SQLException e) {  
            e.printStackTrace();
        }
        return rs;
    }
	
	/**
	 * 执行更新
     * @param conn db连接
     * @param sSQL sql语句
	 */
	public static int operateSQL(Connection conn, String sSQL){
		int iAffected = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement(sSQL);
            iAffected = stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
		return iAffected;
	}
	
	//执行插入
	public static int operateSQL2(Connection conn, String sSQL){
		int iAffected = -1;
        try {
            Statement stmt = conn.createStatement();
            iAffected = stmt.executeUpdate(sSQL, Statement.RETURN_GENERATED_KEYS);
        }catch (SQLException e){
            e.printStackTrace();
        }
		return iAffected;
	}
	
	public static int rsRowCount(ResultSet rs){
		int iRowCount = -1;
		try{
			int iCurRow = rs.getRow();
			rs.last();
			iRowCount = rs.getRow();
			rs.absolute(iCurRow);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return iRowCount;
	}
}