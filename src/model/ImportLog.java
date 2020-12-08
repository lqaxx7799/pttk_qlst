package model;

import java.util.Date;

public class ImportLog {
	private int id;
	private Date createdTime;
	private Provider provider;
	private Item item;
	private int quantity;
	private float totalCost;
	private Employee importEmployee;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public Employee getImportEmployee() {
		return importEmployee;
	}

	public void setImportEmployee(Employee importEmployee) {
		this.importEmployee = importEmployee;
	}

}
