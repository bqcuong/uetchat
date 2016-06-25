package net.bqc.uetchat.is;

public class Chat {
	
	private String lhs;
	private String rhs;
	
	public Chat(String lhs, String rhs) {
		super();
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public String getLhs() {
		return lhs;
	}

	public void setLhs(String lhs) {
		this.lhs = lhs;
	}

	public String getRhs() {
		return rhs;
	}

	public void setRhs(String rhs) {
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return "Chat [lhs=" + lhs + ", rhs=" + rhs + "]";
	}
	
}
