package com.cicada.core.model;

public final class NumericIDModel extends Model {

	private int ID;

	public int getID() {
		return ID;
	}

	public void setID(int id) {
		this.ID = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NumericIDModel other = (NumericIDModel) obj;
		if (ID != other.ID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NumericIDModel [ID=");
		builder.append(ID);
		builder.append("]");
		return builder.toString();
	}

}
