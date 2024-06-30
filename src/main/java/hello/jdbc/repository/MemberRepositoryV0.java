package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DriverManager 사용
 * SQL injection 방지를 위해 PrdparedStatement 를 통한 파라미터 바인딩이 필수!
 */

@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException { // DB에 회원 저장
        String sql = "insert into member(member_id, money) values(?, ?)";


        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId()); //위의 sql 문의 ?에 대한 매핑
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate(); // 위의 준비된 것들이 DB에 실행된다.

            return member;
        }catch (SQLException e){
            log.info("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();   // 조회 할때는 executeQuery() 사용

            if(rs.next()){
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else
                throw new NoSuchElementException("member not found memberId=" + memberId);

        } catch (SQLException e){
            log.error("db error", e);
            throw e;

        } finally {
            close(con, pstmt, rs);
        }

    }


    private void close(Connection con, Statement stmt, ResultSet rs){
        if(rs!=null){
            try{
                rs.close();
            }catch (SQLException e){
                log.info("error", e);
            }
        }

        if(stmt!=null){
            try{
                stmt.close();
            }catch (SQLException e){
                log.info("error", e);
            }
        }
        if(con!=null){
            try{
                con.close();
            }catch (SQLException e){
                log.info("error", e);
            }
        }


    }

    private  Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}