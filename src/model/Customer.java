package model;

public class Customer extends Account {
	private float collectedPoint;
	private Address address;

	public float getCollectedPoint() {
		return collectedPoint;
	}

	public void setCollectedPoint(float collectedPoint) {
		this.collectedPoint = collectedPoint;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
