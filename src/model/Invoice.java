package model;

import java.util.Date;

public class Invoice {
	private int id;
	private Customer customer;
	private Date createdTime;
	private String status;
	private boolean isDeleted;
	private Employee exportEmployee;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Employee getExportEmployee() {
		return exportEmployee;
	}

	public void setExportEmployee(Employee exportEmployee) {
		this.exportEmployee = exportEmployee;
	}

}
