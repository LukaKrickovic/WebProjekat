package sequencers;

import model.Id;

public class AdministratorSequencer extends Sequencer implements ISequencer{

	private String administratorPrefix = "A";
	
	public AdministratorSequencer() {
		super();
	}

	@Override
	public Id initialize() {
		return new Id(administratorPrefix, super.defaultSuffix);
	}

	@Override
	public Id next(Id id) {
		return new Id(administratorPrefix, incrementSuffix(id));
	}	
	
}
