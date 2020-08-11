package sequencers;

import model.Id;

public class ApartmentCommentSequencer extends Sequencer implements ISequencer{

	private String apartmentCommentPrefix = "AC";

	public ApartmentCommentSequencer() {
		super();
	}
	
	@Override
	public Id initialize() {
		return new Id (apartmentCommentPrefix, super.defaultSuffix);
	}
	
	@Override
	public Id next(Id id) {
		return super.next(id);
	}
}
