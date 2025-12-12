package diaryManagement.repository;

import diaryManagement.domain.DiaryVO;
import jdbc_test.JDBCConnector;

import java.sql.*;
import java.util.ArrayList;

public class DiaryRepository {

    // 현재 로그인한 사용자 (테스트용 6번)
    private final int MY_USER_NO = 6;

    public ArrayList<DiaryVO> select(String searchWord, int selectedIndex) {
        Connection con = JDBCConnector.getConnection();
        ArrayList<DiaryVO> list = new ArrayList<>();
        String[] cols = {"d.title", "d.content"}; // 일기 테이블(d)의 제목, 내용

        String sql = "SELECT d.* " +
                "FROM DIARIES d " +
                "JOIN DIARY_BOX b ON d.diary_no = b.diary_no " +
                "WHERE b.owner_no = " + MY_USER_NO +
                " AND " + cols[selectedIndex] + " LIKE ? " +
                "ORDER BY d.diary_no DESC";

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
            if(rs!=null) rs.close();
            if(pstmt!=null) pstmt.close();
            if(con!=null) con.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public void insert(DiaryVO vo) {
        Connection con = JDBCConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con.setAutoCommit(false);

            String sqlDiary = "INSERT INTO DIARIES (writer_no, category_no, title, day, weather, emotion, content, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, SYSDATE)";
            pstmt = con.prepareStatement(sqlDiary);
            pstmt.setInt(1, MY_USER_NO);
            pstmt.setInt(2, vo.getCategoryNo());
            pstmt.setString(3, vo.getTitle());
            pstmt.setString(4, vo.getDay());
            pstmt.setString(5, vo.getWeather());
            pstmt.setString(6, vo.getEmotion());
            pstmt.setString(7, vo.getContent());
            pstmt.executeUpdate();
            pstmt.close();

            int newDiaryNo = 0;
            String sqlMax = "SELECT MAX(diary_no) FROM DIARIES";
            pstmt = con.prepareStatement(sqlMax);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                newDiaryNo = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            if(newDiaryNo > 0) {
                String sqlBox = "INSERT INTO DIARY_BOX (owner_no, diary_no, type, is_important, add_date, created_at) VALUES (?, ?, 'm', 0, SYSDATE, SYSDATE)";
                pstmt = con.prepareStatement(sqlBox);
                pstmt.setInt(1, MY_USER_NO);
                pstmt.setInt(2, newDiaryNo);
                pstmt.executeUpdate();
            }

            con.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if(con != null) con.rollback(); // 에러나면 취소
            } catch(SQLException ex) {}
        } finally {
            try {
                if(con != null) con.setAutoCommit(true);
                if(pstmt != null) pstmt.close();
                if(rs != null) rs.close();
                if(con != null) con.close();
            } catch(Exception e) {}
        }
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

            if(pstmt!=null) pstmt.close();
            if(con!=null) con.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void delete(int diaryNo) {
        Connection con = JDBCConnector.getConnection();
        PreparedStatement pstmt = null;

        try {
            con.setAutoCommit(false);

            String sqlBox = "DELETE FROM DIARY_BOX WHERE diary_no=? AND owner_no=" + MY_USER_NO;
            pstmt = con.prepareStatement(sqlBox);
            pstmt.setInt(1, diaryNo);
            pstmt.executeUpdate();
            pstmt.close();

            String sqlDiary = "DELETE FROM DIARIES WHERE diary_no=? AND writer_no=" + MY_USER_NO;
            pstmt = con.prepareStatement(sqlDiary);
            pstmt.setInt(1, diaryNo);
            pstmt.executeUpdate();

            con.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try { if(con != null) con.rollback(); } catch(SQLException ex) {}
        } finally {
            try {
                if(con != null) con.setAutoCommit(true);
                if(pstmt != null) pstmt.close();
                if(con != null) con.close();
            } catch(Exception e) {}
        }
    }
}