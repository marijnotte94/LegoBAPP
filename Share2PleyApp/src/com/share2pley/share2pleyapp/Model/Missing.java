package com.share2pley.share2pleyapp.Model;

public class Missing {
	private int mPersonId;
	private int mSetNumber;
	private String mDescription;
	private int mAmount;

	public Missing(int personId, int setNumber, String description, int amount) {
		mPersonId = personId;
		mSetNumber = setNumber;
		mDescription = description;
		mAmount = amount;
	}

	public int getPersonId() {
		return mPersonId;
	}

	public int getSetNumber() {
		return mSetNumber;
	}

	public String getDescription() {
		return mDescription;
	}

	public int getAmount() {
		return mAmount;
	}

	public void setPersonId(int personId) {
		mPersonId = personId;
	}

	public void setSetNumber(int setNumber) {
		mSetNumber = setNumber;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public void setAmount(int amount) {
		mAmount = amount;
	}

	@Override
	public String toString() {
		return mDescription + ", " + mAmount;
	}

}
