import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Page {
    private Long pageId;
    private String title;
    private String content;
    private final List<String> subPages = new ArrayList<>();
    private final List<String> breadcrumbs = new ArrayList<>();

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addSubPage(String subPage) {
        subPages.add(subPage);
    }

    public void addBreadcrumbs(String breadcrumb) {
        breadcrumbs.add(breadcrumb);
    }

    public void reverseBreadcrumbs() {
        Collections.reverse(breadcrumbs);
    }
}
