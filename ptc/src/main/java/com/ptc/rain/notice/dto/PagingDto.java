package com.ptc.rain.notice.dto;

public class PagingDto {
	
  private int perPage; // 게시 글 수
  private int firstPageNo; // 첫 번째 페이지 번호
  private int prevPageNo; // 이전 페이지 번호
  private int currentPage; // 페이지 번호
  private int nextPageNo; // 다음 페이지 번호
  private int finalPageNo; // 마지막 페이지 번호
  private int totalCount; // 게시 글 전체 수

  public int getPageOffsetNo() {
    return (currentPage - 1) * perPage;
  }

  /**
   * @return the perPage
   */
  public int getPerPage() {
    return perPage;
  }

  /**
   * @param perPage the perPage to set
   */
  public void setPerPage(int perPage) {
    this.perPage = perPage;
  }

  /**
   * @return the firstPageNo
   */
  public int getFirstPageNo() {
    return firstPageNo;
  }

  /**
   * @param firstPageNo the firstPageNo to set
   */
  public void setFirstPageNo(int firstPageNo) {
    this.firstPageNo = firstPageNo;
  }

  /**
   * @return the prevPageNo
   */
  public int getPrevPageNo() {
    return prevPageNo;
  }

  /**
   * @param prevPageNo the prevPageNo to set
   */
  public void setPrevPageNo(int prevPageNo) {
    this.prevPageNo = prevPageNo;
  }

  /**
   * @return the startPageNo
   */
  public int getStartPageNo() {
    return (this.currentPage - 1) * this.perPage + 1; // 시작 페이지 (페이징 네비 기준)
  }

  /**
   * @return the pageNo
   */
  public int getCurrentPage() {
    return currentPage;
  }

  /**
   * @param pageNo the pageNo to set
   */
  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  /**
   * @return the endPageNo
   */
  public int getEndPageNo() {
    return this.currentPage * this.perPage; // 끝 페이지 (페이징 네비 기준)
  }


  /**
   * @return the nextPageNo
   */
  public int getNextPageNo() {
    return nextPageNo;
  }

  /**
   * @param nextPageNo the nextPageNo to set
   */
  public void setNextPageNo(int nextPageNo) {
    this.nextPageNo = nextPageNo;
  }

  /**
   * @return the finalPageNo
   */
  public int getFinalPageNo() {
    return finalPageNo;
  }

  /**
   * @param finalPageNo the finalPageNo to set
   */
  public void setFinalPageNo(int finalPageNo) {
    this.finalPageNo = finalPageNo;
  }

  /**
   * @return the totalCount
   */
  public int getTotalCount() {
    return totalCount;
  }

  /**
   * @param totalCount the totalCount to set
   */
  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
    this.makePaging();
  }

  /**
   * 페이징 생성
   */
  private void makePaging() {

    if (this.totalCount == 0)
      return; // 게시 글 전체 수가 없는 경우
    if (this.currentPage == 0)
      this.setCurrentPage(1); // 기본 값 설정
    if (this.perPage == 0)
      this.setPerPage(10); // 기본 값 설정

    int finalPage = (totalCount + (perPage - 1)) / perPage; // 마지막 페이지
    if (this.currentPage > finalPage)
      this.setCurrentPage(finalPage); // 기본 값 설정

    if (this.currentPage < 0 || this.currentPage > finalPage)
      this.currentPage = 1; // 현재 페이지 유효성 체크

    boolean isNowFirst = currentPage == 1 ? true : false; // 시작 페이지 (전체)
    boolean isNowFinal = currentPage == finalPage ? true : false; // 마지막 페이지 (전체)


    this.setFirstPageNo(1); // 첫 번째 페이지 번호

    if (isNowFirst) {
      this.setPrevPageNo(1); // 이전 페이지 번호
    } else {
      this.setPrevPageNo(((currentPage - 1) < 1 ? 1 : (currentPage - 1))); // 이전 페이지 번호
    }


    if (isNowFinal) {
      this.setNextPageNo(finalPage); // 다음 페이지 번호
    } else {
      this.setNextPageNo(((currentPage + 1) > finalPage ? finalPage : (currentPage + 1))); // 다음 페이지 번호
    }

    this.setFinalPageNo(finalPage); // 마지막 페이지 번호

  }
	
}
