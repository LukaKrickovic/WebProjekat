package sequencers;
import model.Id;


public interface ISequencer {

	public Id initialize();
	public Id next(Id id);
}
