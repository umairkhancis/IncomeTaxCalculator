package com.netpace.itc.util;

public class IncomeTaxSlab {
	private Long startValue;
	private Long endValue;
	private Long offsetValue; // r% of the amount exceeding startValue
	private float slabPercentageValue;

	
	public IncomeTaxSlab(Long startValue, Long endValue, Long offsetValue,
			float slabPercentageValue) {
		this.startValue = startValue;
		this.endValue = endValue;
		this.offsetValue = offsetValue;
		this.slabPercentageValue = slabPercentageValue;
	}

	public Long getStartValue() {
		return startValue;
	}

	public Long getEndValue() {
		return endValue;
	}

	public Long getOffsetValue() {
		return offsetValue;
	}

	public float getSlabPercentageValue() {
		return slabPercentageValue/100F;
	}
	
	public void setStartValue(Long startValue) {
		this.startValue = startValue;
	}

	public void setEndValue(Long endValue) {
		this.endValue = endValue;
	}

	public void setOffsetValue(Long offsetValue) {
		this.offsetValue = offsetValue;
	}

	public void setSlabPercentageValue(float slabPercentageValue) {
		this.slabPercentageValue = slabPercentageValue;
	}
}
