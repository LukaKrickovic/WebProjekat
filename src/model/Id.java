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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + (int) (suffix ^ (suffix >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Id other = (Id) obj;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (suffix != other.suffix)
			return false;
		return true;
	}
	
	
}
