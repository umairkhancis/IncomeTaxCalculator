package com.netpace.itc.util;

import java.util.List;

public class IncomeTax {

	private Long income;
	private Long payableTax;
	private List<IncomeTaxSlab> slabs;

	public IncomeTax(Long income, List<IncomeTaxSlab> slabs) {
		this.slabs = slabs;
		this.income = income;
	}

	public Long getIncome() {
		return income;
	}

	public void setIncome(Long income) {
		this.income = income;
	}

	public void setPayableTax(Long payableTax) {
		this.payableTax = payableTax;
	}

	public List<IncomeTaxSlab> getIncomeTaxSlabs() {
		return slabs;
	}

	public void setIncomeTaxSlabs(List<IncomeTaxSlab> slabs) {
		this.slabs = slabs;
	}

	public Long getPayableTax() {
		IncomeTaxSlab slab = findSlab();
		float percent = slab.getSlabPercentageValue();
		Long offset = slab.getOffsetValue();
		Long start = slab.getStartValue();
		Long exceeding = income - start;

		payableTax = (long) (exceeding * percent + offset);
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
