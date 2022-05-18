package com.ptc.rain.notice.dto;

import java.io.Serializable;
import java.util.Map;

public class ResultDto<T> implements Serializable {

	  private String result;
	  private String errCode;
	  private String errMsg;

	  private PageList<T> dataList;
	  private Object dataInfo;
	  private Map<String, Object> dataMap;

	  public ResultDto() {}

	  public ResultDto(String result) {
	    this.result = result;
	  }

	  public ResultDto(String result, Object dataInfo) {
	    this.result = result;
	    this.dataInfo = dataInfo;
	  }

	  public ResultDto(String result, PageList<T> dataList) {
	    this.result = result;
	    this.dataList = dataList;
	  }

	  public ResultDto(String result, Map<String, Object> dataMap) {
	    this.result = result;
	    this.dataMap = dataMap;
	  }

	  public ResultDto(String result, String errCode, String errMsg, Object dataInfo) {
	    this.result = result;
	    this.errCode = errCode;
	    this.errMsg = errMsg;
	    this.dataInfo = dataInfo;
	  }

	  public ResultDto(String result, String errCode, String errMsg, PageList<T> dataList) {
	    this.result = result;
	    this.errCode = errCode;
	    this.errMsg = errMsg;
	    this.dataList = dataList;
	  }

	  public ResultDto(String result, String errCode, String errMsg, Map<String, Object> dataMap) {
	    this.result = result;
	    this.errCode = errCode;
	    this.errMsg = errMsg;
	    this.dataMap = dataMap;
	  }

	  public ResultDto(String result, String errCode, String errMsg) {
	    this.result = result;
	    this.errCode = errCode;
	    this.errMsg = errMsg;
	  }



	  public Map<String, Object> getDataMap() {
	    return dataMap;
	  }

	  public void setDataMap(Map<String, Object> dataMap) {
	    this.dataMap = dataMap;
	  }

	  public PageList<T> getDataList() {
	    return dataList;
	  }

	  public void setDataList(PageList<T> dataList) {
	    this.dataList = dataList;
	  }

	  public Object getDataInfo() {
	    return dataInfo;
	  }

	  public void setDataInfo(Object dataInfo) {
	    this.dataInfo = dataInfo;
	  }


	  public String getErrCode() {
	    return errCode;
	  }


	  public void setErrCode(String errCode) {
	    this.errCode = errCode;
	  }


	  public String getErrMsg() {
	    return errMsg;
	  }


	  public void setErrMsg(String errMsg) {
	    this.errMsg = errMsg;
	  }


	  public String getResult() {
	    return result;
	  }

	  public void setResult(String result) {
	    this.result = result;
	  }


	}
