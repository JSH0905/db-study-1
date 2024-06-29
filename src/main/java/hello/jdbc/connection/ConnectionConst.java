package hello.jdbc.connection;

/**
 *  h2 연결을 위한 일종의 규약이므로 글자하나라도 틀리면 안된다.
 *  상수이기 때문에 생성하지 못하도록 abstract로 막음
 */

public abstract class ConnectionConst {
    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}
