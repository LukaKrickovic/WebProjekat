package sequencers;

import model.Id;

public class AmenitySequencer extends Sequencer implements ISequencer{

	private String amenityPrefix = "AM";

	public AmenitySequencer() {
		super();
	}
	
	@Override
	public Id initialize() {
		return new Id(amenityPrefix, super.defaultSuffix);
	}

	@Override
	public Id next(Id id) {
		return super.next(id);
	}

}
