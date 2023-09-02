DROP TABLE IF EXISTS pages.pages;

CREATE TABLE pages.pages
(
    page_id   BIGINT AUTO_INCREMENT,
    title     VARCHAR(255),
    content   text,
    parent_id BIGINT,
    PRIMARY KEY (page_id)
);

insert into pages.pages(page_id, title, content, parent_id)
values (1, 'a', 'a', null),
       (2, 'b', 'b', 1),
       (3, 'c', 'c', 2),
       (4, 'd', 'd', 3),
       (5, 'e', 'e', 4),
       (6, 'f', 'f', 4);

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