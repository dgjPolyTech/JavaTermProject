package diaryManagement.repository;

import diaryManagement.domain.DiaryVO;
import jdbc_test.JDBCConnector;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DiaryRepository {
    ArrayList<DiaryVO> diaryVoList;

    // 특정 다이어리 검색하는 메소드
    public ArrayList<DiaryVO> select(int DiaryNo){
        Connection con = JDBCConnector.getConnection();

        diaryVoList = new ArrayList<DiaryVO>();
        ResultSet rs = null;

        PreparedStatement pstmt = null;
        String[] coulmnName = {"diaryNo", "writerNo", "title", "status", "createAt", "updatedAt"};

        String sql = "select * from diary where diaryNo = ?";


        return diaryVoList;
    }
}
