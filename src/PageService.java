import java.util.List;

public class PageService {
    public Page findPage(Long pageId){
        PageDao pageDao = new PageDao();
        List<PageDto> dtoList = pageDao.findById(pageId);
        return createPage(dtoList);
    }

    private Page createPage(List<PageDto> dtoList) {
        Page page = new Page();

        PageDto pageDto = dtoList.get(0);
        page.setPageId(pageDto.getPageId());
        page.setTitle(pageDto.getTitle());
        page.setContent(pageDto.getContent());


        for (int i = 0; i < dtoList.size(); i++) {
            PageDto dto = dtoList.get(i);
            page.addBreadcrumbs(dto.getTitle());

            if (dto.getParentId() == null) {
                page.reverseBreadcrumbs();
                i++;
                while (i < dtoList.size()) {
                    PageDto subDto = dtoList.get(i);
                    page.addSubPage(subDto.getTitle());
                    i++;
                }
            }
        }

        return page;
    }
}
