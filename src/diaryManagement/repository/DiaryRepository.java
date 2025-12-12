package diaryManagement.repository;

import diaryManagement.domain.DiaryVO;
import jdbc_test.JDBCConnector;

import java.sql.*;
import java.util.ArrayList;

public class DiaryRepository {
    private final int MY_USER_NO = 6;

    public ArrayList<DiaryVO> select(String searchWord, int selectedIndex) {
        Connection con = JDBCConnector.getConnection();
        ArrayList<DiaryVO> list = new ArrayList<>();
        String[] cols = {"title", "content"};
        String sql = "SELECT * FROM DIARIES WHERE writer_no = " + MY_USER_NO +
                " AND " + cols[selectedIndex] + " LIKE ? ORDER BY diary_no DESC";

        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + searchWord + "%");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                DiaryVO vo = new DiaryVO();
                vo.setDiaryNo(rs.getInt("diary_no"));
                vo.setCategoryNo(rs.getInt("category_no"));
                vo.setTitle(rs.getString("title"));
                vo.setDay(rs.getString("day"));
                vo.setWeather(rs.getString("weather"));
                vo.setEmotion(rs.getString("emotion"));
                vo.setContent(rs.getString("content"));
                list.add(vo);
            }
            if(rs!=null) rs.close(); if(pstmt!=null) pstmt.close(); if(con!=null) con.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public void insert(DiaryVO vo) {
        Connection con = JDBCConnector.getConnection();
        String sql = "INSERT INTO DIARIES (writer_no, category_no, title, day, weather, emotion, content, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, SYSDATE)";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, MY_USER_NO);
            pstmt.setInt(2, vo.getCategoryNo()); // 주제 번호 저장
            pstmt.setString(3, vo.getTitle());
            pstmt.setString(4, vo.getDay());
            pstmt.setString(5, vo.getWeather());
            pstmt.setString(6, vo.getEmotion());
            pstmt.setString(7, vo.getContent());
            pstmt.executeUpdate();

            if(pstmt!=null) pstmt.close(); if(con!=null) con.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void update(DiaryVO vo) {
        Connection con = JDBCConnector.getConnection();
        String sql = "UPDATE DIARIES SET title=?, content=?, weather=?, emotion=?, updated_at=SYSDATE WHERE diary_no=? AND writer_no=" + MY_USER_NO;
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, vo.getTitle());
            pstmt.setString(2, vo.getContent());
            pstmt.setString(3, vo.getWeather());
            pstmt.setString(4, vo.getEmotion());
            pstmt.setInt(5, vo.getDiaryNo());
            pstmt.executeUpdate();

            if(pstmt!=null) pstmt.close(); if(con!=null) con.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void delete(int diaryNo) {
        Connection con = JDBCConnector.getConnection();
        String sql = "DELETE FROM DIARIES WHERE diary_no=? AND writer_no=" + MY_USER_NO;
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, diaryNo);
            pstmt.executeUpdate();
            if(pstmt!=null) pstmt.close(); if(con!=null) con.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}