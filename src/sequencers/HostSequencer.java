package sequencers;

import model.Id;

public class HostSequencer extends Sequencer implements ISequencer{

	private String hostPrefix = "H";
	
	public HostSequencer() {
		super();
	}

	@Override
	public Id initialize() {
		return new Id(hostPrefix, super.defaultSuffix);
	}

	@Override
	public Id next(Id id) {
		return new Id(hostPrefix, incrementSuffix(id));
	}

	
	
}
