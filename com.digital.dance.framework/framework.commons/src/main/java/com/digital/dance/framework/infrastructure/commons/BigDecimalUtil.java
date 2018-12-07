package com.digital.dance.framework.infrastructure.commons;

import java.math.BigDecimal;

/**
 * 
 * @author liuxiny
 *
 */
public class BigDecimalUtil {
	//+
	/**
	 * +
	 * @param left
	 * @param right
	 * @param scale
	 * @param roundMode
	 * @return
	 */
	public static double sum(double left,double right,int scale,int roundMode){
		double sumV=0;
		BigDecimal bDecimal1=new BigDecimal(Double.toString(left));
		BigDecimal bDecimal2=new BigDecimal(Double.toString(right));
		sumV=bDecimal1.add(bDecimal2).setScale(scale, roundMode).doubleValue();
		return sumV;
	}
	
    //-
	/**
	 * -
	 * @param left
	 * @param right
	 * @param scale
	 * @param roundMode
	 * @return
	 */
	public static double subtract(double left,double right,int scale,int roundMode){
		double value=0;
		BigDecimal bDecimal1=new BigDecimal(Double.toString(left));
		BigDecimal bDecimal2=new BigDecimal(Double.toString(right));
		value=bDecimal1.subtract(bDecimal2).setScale(scale, roundMode).doubleValue();
		return value;
	} 
	
    //*
	/**
	 * *
	 * @param left
	 * @param right
	 * @param scale
	 * @param roundMode
	 * @return
	 */
	public static double multiply(double left,double right,int scale,int roundMode){
		double value=0;
		BigDecimal bDecimal1=new BigDecimal(Double.toString(left));
		BigDecimal bDecimal2=new BigDecimal(Double.toString(right));
		value=bDecimal1.multiply(bDecimal2).setScale(scale, roundMode).doubleValue();
		return value;
	}
	
	 /* ��/ */
	/**
	 * divide
	 * @param left
	 * @param right
	 * @param scale
	 * @param roundMode
	 * @return
	 * @throws Exception
	 */
	public static double divide(double left,double right,int scale,int roundMode) throws Exception{
		double value=0;
		BigDecimal bDecimal1=new BigDecimal(Double.toString(left));
		BigDecimal bDecimal2=new BigDecimal(Double.toString(right));
		value=bDecimal1.divide(bDecimal2, scale, roundMode).doubleValue();
		return value;
	}
}
