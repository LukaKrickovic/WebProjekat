package sequencers;

import model.Id;

public class UnitSequencer extends Sequencer implements ISequencer{

	private String unitPrefix = "UN";
	
	public UnitSequencer() {
		super();
	}

	@Override
	public Id initialize() {
		return new Id(unitPrefix, super.defaultSuffix);
	}
	
	@Override
	public Id next(Id id) {
		return super.next(id);
	}
}
