package com.netpace.itc.util;

import java.util.List;

public class IncomeTax {

	private Double income;
	private Double payableTax;
	private List<IncomeTaxSlab> slabs;

	public IncomeTax(Double income, List<IncomeTaxSlab> slabs) {
		this.slabs = slabs;
		this.income = income;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public void setPayableTax(Double payableTax) {
		this.payableTax = payableTax;
	}

	public List<IncomeTaxSlab> getIncomeTaxSlabs() {
		return slabs;
	}

	public void setIncomeTaxSlabs(List<IncomeTaxSlab> slabs) {
		this.slabs = slabs;
	}

	public Double getPayableTax() {
		IncomeTaxSlab slab = findSlab();
		float percent = slab.getSlabPercentageValue();
		Double offset = slab.getOffsetValue();
		Double start = slab.getStartValue();
		Double exceeding = income - start;

		payableTax = (double) (exceeding * percent + offset);
		return payableTax;
	}

	private IncomeTaxSlab findSlab() {
		IncomeTaxSlab slab = null;
		for (int i = 0; i < slabs.size(); i++) {
			slab = slabs.get(i);
			if (income >= slab.getStartValue() && income <= slab.getEndValue()) {
				break;
			}
		}
		return slab;
	}

}
