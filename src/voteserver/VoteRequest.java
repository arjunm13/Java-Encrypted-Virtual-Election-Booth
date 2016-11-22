package voteserver;

import java.io.Serializable;

public class VoteRequest implements Serializable{

	private String clientID = null;
	private String password = null;
	private int validationNumber = 0;
	private voteID myVote = null;
	
	//enum of possible candidates in election
	public static enum voteID
	{
		ARJUN, SAJEEVAN, KRISHNA, ANMOL
	}
	
	//constructor to be used by client during first request to CLA
	public VoteRequest(String clientID, String password, voteID myVote) {
		super();
		this.clientID = clientID;
		this.password = password;
		this.validationNumber = 0;
		
		if (myVote != null)
			this.myVote = myVote;
	}
	
	//constructor to be used by CLA in response to client vote request
	public VoteRequest(String clientID, String password, int validationNumber, voteID myVote) {
		super();
		this.clientID = clientID;
		this.password = password;
		this.validationNumber = validationNumber;
		this.myVote = myVote;
	}
	

	public int getValidationNumber() {
		return validationNumber;
	}

	public void setValidationNumber(int validationNumber) {
		this.validationNumber = validationNumber;
	}

	public String getClientID() {
		return clientID;
	}

	public String getPassword() {
		return password;
	}

	public voteID getMyVote() {
		return myVote;
	}

	
	@Override
	public String toString() {
		return "VoteRequest [clientID=" + clientID + ", password=" + password + ", validationNumber=" + validationNumber
				+ ", myVote=" + myVote + "]";
	}
}
