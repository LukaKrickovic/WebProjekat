package sequencers;

import model.Id;

public class ReservationSequencer extends Sequencer implements ISequencer{

	private String reservationPrefix = "RE";
	
	public ReservationSequencer() {
		super();
	}
	
	@Override
	public Id initialize() {
		return new Id(reservationPrefix, super.defaultSuffix);
	}
	
	@Override
	public Id next(Id id) {
		return super.next(id);
	}

}
