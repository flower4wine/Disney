package com.disney.constant;

public class Lo2LoType {

	/**
	 * 停车场内部到景点
	 */
	public static int PARKINNER_2_VIEW = 1;
	public static int VIEW_2_PARKINNER = 2;
	
	/**
	 * 停车场内部到接泊车站
	 */
	public static int PARKINNER_2_BUSSTATION = 3;
	public static int BUSSTATION_2_PARKINNER = 4;
	
	
	/**
	 * 景点到接泊车站
	 */
	public static int VIEW_2_BUSSTATION = 5;
	public static int BUSSTATION_2_VIEW = 6;
	
	/**
	 * 停车场内部到停车场内部
	 */
	public static int PARKINNER_2_PARKINNER = 9;
}
