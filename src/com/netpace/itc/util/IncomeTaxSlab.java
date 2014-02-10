package com.netpace.itc.util;

public class IncomeTaxSlab {
	private Double startValue;
	private Double endValue;
	private Double offsetValue; // r% of the amount exceeding startValue
	private float slabPercentageValue;

	
	public IncomeTaxSlab(Double startValue, Double endValue, Double offsetValue,
			float slabPercentageValue) {
		this.startValue = startValue;
		this.endValue = endValue;
		this.offsetValue = offsetValue;
		this.slabPercentageValue = slabPercentageValue;
	}

	public Double getStartValue() {
		return startValue;
	}

	public Double getEndValue() {
		return endValue;
	}

	public Double getOffsetValue() {
		return offsetValue;
	}

	public float getSlabPercentageValue() {
		return slabPercentageValue/100F;
	}
	
	public void setStartValue(Double startValue) {
		this.startValue = startValue;
	}

	public void setEndValue(Double endValue) {
		this.endValue = endValue;
	}

	public void setOffsetValue(Double offsetValue) {
		this.offsetValue = offsetValue;
	}

	public void setSlabPercentageValue(float slabPercentageValue) {
		this.slabPercentageValue = slabPercentageValue;
	}
}
