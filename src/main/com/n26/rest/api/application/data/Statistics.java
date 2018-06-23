package com.n26.rest.api.application.data;

public class Statistics {
	private double sum;
	private double avg;
	private double min;
	private double max;
	private long count;
	
	public Statistics(double sum, double avg, double min, double max, long count) {
		this.sum = sum;
		this.avg = avg;
		this.min = min;
		this.max = max;
		this.count = count;
	}
	
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
}
