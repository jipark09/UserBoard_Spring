package kr.ac.jipark09.domain;

import org.springframework.web.util.UriComponentsBuilder;

// 검색 조건
public class SearchCondition {
    // boardMapper.xml 검색 sql문 보고 들어갈 값 변수에 놓기
    private Integer page = 1; // Controller에서도 쓸 것이기 때문에 넣어준다.
    private Integer pageSize = 10;
//    private Integer offset = 0;
    private String keyword = "";
    private String option = "";

    public SearchCondition() {};

    public SearchCondition(Integer page, Integer pageSize, String keyword, String option) {
        this.page = page;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.option = option;
    }

    // 페이지를 줘서 해당 페이지에 대한 네비게이션도 해줘야 함
    public String getQueryString(Integer page) {
        // ?page=1&pageSize=10&option=T&keyword="title" -> 일일이 치기 귀찮음 이렇게 만들어주는 메서드를 만들어줌
        return UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("pageSize", pageSize)
                .queryParam("option", option)
                .queryParam("keyword", keyword)
                .build().toString();

    }

    // 쿼리스트링 처리 부분
    public String getQueryString() {
        return getQueryString(page);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return (page - 1) * pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "SearchCondition{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", offset=" + getOffset() +
                ", keyword='" + keyword + '\'' +
                ", option='" + option + '\'' +
                '}';
    }
}
