package validations;

import exceptions.UserUpdateValidationException;
import model.Administrator;
import model.Guest;
import model.Host;
import repository.AdministratorRepository;
import repository.GuestRepository;
import repository.HostRepository;

public class UserUpdateValidation {

	private GuestRepository guestRepository;
	private HostRepository hostRepository;
	private AdministratorRepository administratorRepository;
	
	public UserUpdateValidation(GuestRepository guestRepository, HostRepository hostRepository,
			AdministratorRepository administratorRepository) {
		super();
		this.guestRepository = guestRepository;
		this.hostRepository = hostRepository;
		this.administratorRepository = administratorRepository;
	}
	
	public void validateGuestUpdatedInformation(Guest guest) throws UserUpdateValidationException {
		if(!guest.getUsername().equals(guestRepository.getById(guest.getId()).getUsername()))
			throw new UserUpdateValidationException("Guest Username was changed!");
	}
	
	public void validateHostUpdatedInformation(Host host) throws UserUpdateValidationException {
		if(!host.getUsername().equals(hostRepository.getById(host.getId()).getUsername()))
			throw new UserUpdateValidationException("Host Username was changed!");
	}
	
	public void validateAdministratorUpdatedInformation(Administrator administrator) throws UserUpdateValidationException {
		if(!administrator.getUsername().equals(administratorRepository.getById(administrator.getId()).getUsername()))
			throw new UserUpdateValidationException("Administrator Username was changed!");
	}
	
}
