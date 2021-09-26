package com.metanet.intern.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pager {
	private int totalRows;		//전체 행수
	private int totalPageNo;	//전체 페이지 수
	private int totalGroupNo;	//전체 그룹 수
	private int startPageNo;	//그룹의 시작 페이스 번호
	private int endPageNo;		//그룹의 끝 페이지 번호
	private int pageNo;			//현재 페이지 번호
	private int pagesPerGroup;	//그룹당 페이지 수
	private int groupNo;		//현재 그룹 번호
	private int rowsPerPage;	//페이지당 행 수 
	private int startRowNo; 	//페이지의 시작 행 번호
	private int endRowNo; 		//페이지의 시작 행 번호
	private int numberOfElements; //현재 페이지에 나와야 할 갯수
	public Pager(int rowsPerPage, int pagesPerGroup, int totalRows, int pageNo) {
		this.rowsPerPage = rowsPerPage;
		this.pagesPerGroup = pagesPerGroup;
		this.totalRows = totalRows;
		this.pageNo = pageNo;

		//전체 행수 / 페이지 당 행수
		totalPageNo = totalRows / rowsPerPage;
		if(totalRows % rowsPerPage != 0) totalPageNo++;
		
		//전체 페이지 수 / 그룹당 페이지 수
		totalGroupNo = totalPageNo / pagesPerGroup;
		if(totalPageNo % pagesPerGroup != 0) totalGroupNo++;
		
		//현제 페이지 no / 그룹당 페이지 수
		groupNo = (pageNo - 1) / pagesPerGroup + 1;
		
		//현제 페이지가 속해 있는 그룹에 시작 페이지 no
		startPageNo = (groupNo-1) * pagesPerGroup + 1;
		
		//현제 페이지가 속해 있는 그룹에 마지막 페이지 no
		endPageNo = startPageNo + pagesPerGroup - 1;
		if(groupNo == totalGroupNo) endPageNo = totalPageNo;
		
		startRowNo = (pageNo - 1) * rowsPerPage +1;
		endRowNo = pageNo * rowsPerPage;
		if(totalRows == 0) {
			startPageNo = 0;
			endPageNo = 0;
		}
	}
}
