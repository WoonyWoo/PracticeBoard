package com.ptc.rain.notice.dto;

import java.io.Serializable;
import java.util.List;

public class PageList<T> extends NoticeDto implements Serializable {

	  private List<T> itemList;
	  private int itemTotalCount;

	  public List<T> getItemList() {
	    return itemList;
	  }

	  public void setItemList(List<T> itemList) {
	    this.itemList = itemList;
	  }

	  public int getItemTotalCount() {
	    return itemTotalCount;
	  }

	  public void setItemTotalCount(int itemTotalCount) {
	    this.itemTotalCount = itemTotalCount;
	    if (this.getPaging() != null) {
	      this.getPaging().setTotalCount(itemTotalCount);
	    }
	  }

	}