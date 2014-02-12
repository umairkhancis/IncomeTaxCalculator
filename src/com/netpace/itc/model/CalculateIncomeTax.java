package com.netpace.itc.model;

import java.util.List;

public class CalculateIncomeTax {

	private Double income;
	private Double payableTax;
	private List<Slab> slabs;

	public CalculateIncomeTax(Double income, List<Slab> slabs) {
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

	public List<Slab> getIncomeTaxSlabs() {
		return slabs;
	}

	public void setIncomeTaxSlabs(List<Slab> slabs) {
		this.slabs = slabs;
	}

	public Double getPayableTax() {
		Slab slab = findSlab();
		float percent = slab.getSlabPercentageValue();
		Double offset = slab.getOffsetValue();
		Double start = slab.getStartValue();
		Double exceeding = income - start;

		payableTax = (double) (exceeding * percent + offset);
		return payableTax;
	}

	private Slab findSlab() {
		Slab slab = null;
		for (int i = 0; i < slabs.size(); i++) {
			slab = slabs.get(i);
			if (income >= slab.getStartValue() && income <= slab.getEndValue()) {
				break;
			}
		}
		return slab;
	}

}
