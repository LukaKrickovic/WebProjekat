package sequencers;
import model.Id;

public class GuestSequencer extends Sequencer implements ISequencer {

	private String guestPrefix = "G";
	
	public GuestSequencer() {
		super();
	}
	
	@Override
	public Id initialize() {
		return new Id(guestPrefix, super.defaultSuffix);
	}

	@Override
	public Id next(Id id) {
		return new Id(guestPrefix, incrementSuffix(id));
	}

	
}
