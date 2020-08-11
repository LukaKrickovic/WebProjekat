package test;

import model.Id;
import sequencers.AmenitySequencer;
import sequencers.GuestSequencer;
import sequencers.HostSequencer;

public class SequencerTest {

	public static void main(String[] args) {
		Id retVal = new Id();
		HostSequencer hs = new HostSequencer();
		retVal = hs.initialize();
		System.out.println(retVal);
		retVal = hs.next(retVal);
		System.out.println(retVal);
		GuestSequencer gs = new GuestSequencer();
		retVal = gs.next(retVal);
		System.out.println(retVal);
		
		AmenitySequencer as = new AmenitySequencer();
		retVal = as.initialize();
		retVal = as.next(retVal);
		System.out.println(retVal);
	}

}
