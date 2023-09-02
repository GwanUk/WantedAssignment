import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PageDao {

    public List<PageDto> findById(Long pageId) {
        String sql = """
                WITH RECURSIVE hierarchy_pages (page_id, title, content, parent_id)
                AS
                (
                    SELECT page_id, title, content, parent_id
                    FROM pages.pages
                    WHERE page_id = ?
                    UNION ALL
                    SELECT c.page_id, c.title, c.content, c.parent_id
                    FROM pages.pages c
                    JOIN hierarchy_pages p
                    ON c.page_id = p.parent_id
                )
                
                SELECT page_id, title, content, parent_id
                FROM hierarchy_pages
                
                UNION ALL
                
                (SELECT page_id, title, content, parent_id
                FROM pages.pages
                WHERE parent_id = ?
                ORDER BY page_id);
                """;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, pageId);
            stmt.setLong(2, pageId);
            rs = stmt.executeQuery();

            List<PageDto> result = new ArrayList<>();
            while (rs.next()) {
                PageDto dto = new PageDto();
                dto.setPageId(rs.getLong("page_id"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setParentId(rs.getObject("parent_id", Long.class));
                result.add(dto);
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    private void close(Connection conn, PreparedStatement stmt, ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
