package kr.ac.jipark09.domain;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageHandler {
    // 검색
    private SearchCondition sc;
//    private int page; // 현재 페이지
//    private int pageSize; // 한 페이지의 크기
//    private String option;
//    private String keyword;

    private int totalCnt; // 총 게시물 갯수
    private int naviSize = 10; // 페이지 내비게이션의 크기
    private int totalPage; // 전체 페이지의 갯수
    private int beginPage; // 내비게이션의 첫 번째 페이지
    private int endPage; // 내비게이션의 마지막 페이지
    private boolean showPrev; // 이전 페이지로 이동하는 링크를 보여줄 것인지의 여부 (1페이지만 있을 때는 있으면 안되니까)
    private boolean showNext; // 다음 페이지로 이동하는 링크를 보여줄 것인지의 여부 (10의 배수로 채워지지 않았을 때 있으면 안되니까)

    public PageHandler(int totalCnt, SearchCondition sc) {
        setTotalCnt(totalCnt);
        this.sc = sc;

        doPaging(totalCnt, sc);
    }

    // 계산을 해서 페이지에 보여줄 화면을 구성
    public void doPaging(int totalCnt, SearchCondition sc) {
        this.totalCnt = totalCnt;

        // totalPage = 전체게시물 / 10 (올림) => pageSize 정수 주의!!! 정수랑 정수랑 나눠서 소수점 x
        totalPage = (int)Math.ceil(totalCnt / (double)sc.getPageSize());
        // 25 / 10 * 10 + 1 = 11
        // (page - 1) => 10 / 10 * 10 + 1 = 11되버림. 10의 자리는 그 따음페이지로 넘어가면 안됨. 10의 자리때문에 -1 해줘야함
        beginPage = (sc.getPage() - 1) / naviSize * 10 + 1;
        endPage = Math.min(beginPage + naviSize - 1, totalPage);
        showPrev = beginPage != 1;
        showNext = endPage != totalPage;
    }

    public SearchCondition getSc() {
        return sc;
    }

    public void setSc(SearchCondition sc) {
        this.sc = sc;
    }

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

    public int getNaviSize() {
        return naviSize;
    }

    public void setNaviSize(int naviSize) {
        this.naviSize = naviSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isShowPrev() {
        return showPrev;
    }

    public void setShowPrev(boolean showPrev) {
        this.showPrev = showPrev;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    // 페이지 네비게이션을 프린트하는 메서드
    public void print() {
        System.out.println("page = " + sc.getPage());
        System.out.print(showPrev ? "[PREV] " : "");
        for(int i = beginPage; i <= endPage; i++) {
            System.out.print(i + " ");
        }
        System.out.println(showNext ? "[NEXT]" : "");
    }

    @Override
    public String toString() {
        return "PageHandler{" +
                "sc=" + sc +
                ", totalCnt=" + totalCnt +
                ", naviSize=" + naviSize +
                ", totalPage=" + totalPage +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", showPrev=" + showPrev +
                ", showNext=" + showNext +
                '}';
    }
}
