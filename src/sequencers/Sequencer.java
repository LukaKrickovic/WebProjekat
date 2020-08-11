package sequencers;

import model.Id;


public class Sequencer {

	//	Simbolicki prefiks i numericki sufiks (sacuvan kao string jer ce i ID biti string)
	
	protected long defaultSuffix;
	
	public Sequencer() {
		defaultSuffix = 0;
	}
	
	public long incrementSuffix(Id id) {
		return id.getSuffix() + 1;
	}
	
	public Id next(Id id) {
		return new Id(id.getPrefix(), incrementSuffix(id));
	}
	
}
