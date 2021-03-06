package com.tom.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import Utils.Config;
import Utils.DBUtil;

import com.tom.Dao.ThemeDao;

public class ThemeImpl implements ThemeDao{

	Connection conn=null;
	PreparedStatement psmt=null;
	String sql = "";
	int result = -1;
	ResultSet rs = null;
	
	@Override
	public ResultSet GetPubTheme(int userId) {
		// TODO Auto-generated method stub
		conn = DBUtil.getConnection(); 
		sql = "select Tid,Cid,Ttitle,Tdate from theme where Uid = "+userId;
		System.out.println(sql);
		
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	
	@Override
	public int CreateTheme(int circleId, int Uid, String title, String content) {
		// TODO Auto-generated method stub
		conn = DBUtil.getConnection(); 
		sql = "insert into theme(Cid,Uid,Ttitle,Tcontent,Tdate) values (?,?,?,?,?)";
		System.out.println(sql);
		System.out.println(title+content);
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, circleId);
			psmt.setInt(2,Uid);
			psmt.setString(3,title);
			psmt.setString(4,content);
			psmt.setTimestamp(5, new Timestamp(new Date().getTime()));
			result = psmt.executeUpdate();
			System.out.println(result);
			if(result > 0)
				result = Config.SUCCESS;
			else
				result = Config.FAILE;
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public ResultSet GetTmemeList(int circleId) {
		// TODO Auto-generated method stub
		conn = DBUtil.getConnection(); 
		sql = "select Tid,Ttitle,Tdate,username,Tcontent from theme,user where theme.Uid = user.Uid and Cid = "+circleId;
		System.out.println(sql);
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}


	@Override
	public ResultSet GetThemeInfo(int Tid) {
		// TODO Auto-generated method stub
		conn = DBUtil.getConnection(); 
		sql = "select username,level,sex,Ttitle,Tcontent,Tpraise,Tdate from theme,user where theme.Uid = user.Uid and Tid = "+Tid;
		System.out.println(sql);
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}


	@Override
	public ResultSet GetThemeReply(int ReTid, int ReType) {
		// TODO Auto-generated method stub
		conn = DBUtil.getConnection(); 
		sql = "select username,level,sex,Reid,Recontent,Repraise,Redate from reply,user where reply.Uid = user.Uid and ReTid = "+ReTid+" and ReType = "+ReType + " order by Redate";
		System.out.println(sql);
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}


	@Override
	public int AddThemePraise(int Tid, int Pid) {
		// TODO Auto-generated method stub
		conn = DBUtil.getConnection(); 
		sql = "update theme set Tpraise = Tpraise+1 where Tid = "+Tid;
		System.out.println(sql);
		int Uid = -1;
		try {
			psmt = conn.prepareStatement(sql);
			result = psmt.executeUpdate();
			
			sql = "select Uid from theme where Tid = "+Tid;
			psmt = conn.prepareStatement(sql); 
			rs = psmt.executeQuery();
			if(rs.next())
				Uid = rs.getInt("Uid");
			
			sql = "update user set praise = praise+1 where Uid = "+Uid;
			psmt = conn.prepareStatement(sql); 
			result = psmt.executeUpdate();
			
			sql = "insert into praise(Uid,Tid,Pid,Ptype) values(?,?,?,?)";
			psmt = conn.prepareStatement(sql); 
			psmt.setInt(1, Uid);
			psmt.setInt(2, Tid);
			psmt.setInt(3, Pid);
			psmt.setInt(4, 1);
			result = psmt.executeUpdate();
			
			if(result == 0)
				result = Config.FAILE;
			else
				result = Config.SUCCESS;
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public int AddThemeReplyPra(int Reid, int Uid) {
		// TODO Auto-generated method stub
		System.out.println("In AddThemeReplyPra");
		conn = DBUtil.getConnection(); 
		sql = "update reply set Repraise = Repraise+1 where Reid = "+Reid;
		System.out.println(sql);
		try {
			psmt = conn.prepareStatement(sql);
			result = psmt.executeUpdate();
			
			sql = "update user set praise = praise + 1 where Uid = "+Uid;
			psmt = conn.prepareStatement(sql);
			result = psmt.executeUpdate();
			
			if(result > 0)
				result = Config.SUCCESS;
			else 
				result = Config.FAILE;
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}


	@Override
	public int GetThemeReplyNum(int ReTid, int Retype) {
		// TODO Auto-generated method stub
		conn = DBUtil.getConnection(); 
		sql = "select count(Reid) as ReplyNum from reply where ReTid = ? and Retype = ?";
		System.out.println(sql);
		int ReplyNum = 0;
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, ReTid);
			psmt.setInt(2, Retype);
			rs = psmt.executeQuery();
			if(rs.next())
				ReplyNum = rs.getInt("ReplyNum");
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(ReplyNum);
		return ReplyNum;
	}


	@Override
	public int DelectTheme(int Uid, int Tid) {
		// TODO Auto-generated method stub
		conn = DBUtil.getConnection(); 
		sql = "delete from theme where Tid = ? and Uid = ?";
		System.out.println(sql);
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, Tid);
			psmt.setInt(2, Uid);
			result = psmt.executeUpdate();
			if(result > 0)
				result = Config.SUCCESS;
			else 
				result = Config.FAILE;
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(result);
		return result;
	}


	@Override
	public int GetPraisestatu(int Uid, int Tid) {
		// TODO Auto-generated method stub
		conn = DBUtil.getConnection(); 
		sql = "select * from praise where Uid = ? and Tid = ?";
		System.out.println(sql);
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, Uid);
			psmt.setInt(2, Tid);
			rs = psmt.executeQuery();
			if(rs.next())
				result = Config.SUCCESS;
			else 
				result = Config.FAILE;
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int DeleteThemepraise(int Tid, int Pid) {
		// TODO Auto-generated method stub
		conn = DBUtil.getConnection(); 
		sql = "update theme set Tpraise = Tpraise-1 where Tid = "+Tid;
		System.out.println(sql);
		int Uid = -1;
		try {
			psmt = conn.prepareStatement(sql);
			result = psmt.executeUpdate();
			
			sql = "select Uid from theme where Tid = "+Tid;
			psmt = conn.prepareStatement(sql); 
			rs = psmt.executeQuery();
			if(rs.next())
				Uid = rs.getInt("Uid");
			
			sql = "update user set praise = praise-1 where Uid = "+Uid;
			psmt = conn.prepareStatement(sql); 
			result = psmt.executeUpdate();
			
			sql = "delete from praise where Uid = ? and Tid = ? and Pid = ?";
			psmt = conn.prepareStatement(sql); 
			psmt.setInt(1, Uid);
			psmt.setInt(2, Tid);
			psmt.setInt(3, Pid);
			result = psmt.executeUpdate();
			
			if(result == 0)
				result = Config.FAILE;
			else
				result = Config.SUCCESS;
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public int AddReply(int Tid, int Uid, String content) {
		// TODO Auto-generated method stub
		conn = DBUtil.getConnection(); 
		sql = "insert into reply(ReTid,Retype,Uid,Recontent,Redate) values (?,?,?,?,?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1,Tid);
			psmt.setInt(2,1);
			psmt.setInt(3,Uid);
			psmt.setString(4,content);
			psmt.setTimestamp(5, new Timestamp(new Date().getTime()));
			result = psmt.executeUpdate();
			System.out.println(result);
			if(result > 0)
				result = Config.SUCCESS;
			else
				result = Config.FAILE;
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
