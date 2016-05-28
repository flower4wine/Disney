package com.disney.util;

public class PageUtil {

	public static final int DEFAULT_PAGE_SIZE = 5; // 默认分页大小
	public static final String DEFAULT_PAGE_NAME = "page";

	/**
	 * @Title: newInstands
	 * @param @return 设定文件
	 * @return PageUtil 返回类型
	 * @throws
	 */
	public static PageUtil newInstants() {
		return new PageUtil();
	}

	// 初始化
	public static void init(PageUtil page) {
		page.currPage = 1; // 当前页easyUi中从1开始
		page.pageSize = 10; // 一页显示多少行
		page.sort = null; // 用哪个列排顺
		page.order = null; // 排顺规则
		page.total = 0; // 总数默认为0
	}

	private int currPage; // 当前第几页
	private int pageSize; // 一页显示多少行
	private String sort; // 按哪个列进行排序
	private String order; // 排序规则 asc 升顺 desc 降顺
	private int total; // 数据总数

	// 初使化pageUtil
	public PageUtil() {
		init(this);
	}

	// 全参构造方法
	public PageUtil(int currPage, int pageSize, String sort, String order, int total) {
		super();
		this.currPage = currPage;
		this.pageSize = pageSize;
		this.sort = sort;
		this.order = order;
		this.total = total;
	}

	// 两个参数的构造方法
	public PageUtil(int currPage, int pageSize) {
		super();
		this.currPage = currPage;
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
