package model;

public class Id {

	private String prefix;
	private long suffix;
	
	//	Constructors
	
	public Id() {}

	public Id(String prefix, long suffix) {
		super();
		this.prefix = prefix;
		this.suffix = suffix;
	}
	
	//	Getters and Setters

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public long getSuffix() {
		return suffix;
	}

	public void setSuffix(long suffix) {
		this.suffix = suffix;
	}

	@Override
	public String toString() {
		return prefix + suffix;
	}
	
	
}
