package services;

import java.util.ArrayList;
import java.util.Collection;

import enums.Roles;
import exceptions.UserUpdateValidationException;
import model.Administrator;
import model.Guest;
import model.Host;
import model.Id;
import model.User;
import repository.AdministratorRepository;
import repository.GuestRepository;
import repository.HostRepository;
import sequencers.GuestSequencer;
import sequencers.HostSequencer;
import validations.UserUpdateValidation;
import validations.UserValidation;

public class UserService {

	private HostRepository hostRepository;
	private GuestRepository guestRepository;
	private AdministratorRepository administratorRepository;
	private UserValidation userValidation;
	private UserUpdateValidation userUpdateValidation;
	
	public UserService(HostRepository hostRepository, GuestRepository guestRepository,
			AdministratorRepository administratorRepository) {
		super();
		this.hostRepository = hostRepository;
		this.guestRepository = guestRepository;
		this.administratorRepository = administratorRepository;
		this.userValidation = new UserValidation();
		this.userUpdateValidation = new UserUpdateValidation(guestRepository, hostRepository, administratorRepository);
	}
	
	public Host registerHost(Host host) {
		userValidation.validate(host);
		hostRepository.create(host);
		return host;
	}
	
	public Guest registerGuest(Guest guest) {
		userValidation.validate(guest);
		guestRepository.create(guest);
		return guest;
	}
	
	public User login(String username, String password, Roles role) {
		User retVal;
		if(role.equals(Roles.ADMINISTRATOR)) {
			retVal = administratorRepository.getUserByUsername(username);
		} else if(role.equals(Roles.GUEST)) {
			retVal = guestRepository.getUserByUsername(username);
		} else {
			retVal = hostRepository.getUserByUsername(username);
		}
		
		if(retVal.getPassword().equals(password))
			return retVal;
		else
			return null;
	}
	
	public void changeData (User user) {
		try {
		if(user.getRole().equals(Roles.ADMINISTRATOR)) {
			userUpdateValidation.validateAdministratorUpdatedInformation((Administrator)user);
			administratorRepository.update((Administrator)user);
		} else if(user.getRole().equals(Roles.GUEST)) {
			userUpdateValidation.validateGuestUpdatedInformation((Guest)user);
			guestRepository.update((Guest)user);
		} else {
			userUpdateValidation.validateHostUpdatedInformation((Host)user);
			hostRepository.update((Host)user);
		}
		} catch (UserUpdateValidationException e) {
			e.printStackTrace();
		}
		
	}
	
	public Iterable<Administrator> getAllAdministrators(){
		return administratorRepository.getAll();
	}
	
	public Iterable<Host> getAllHosts(){
		return hostRepository.getAll();
	}
	
	public Iterable<Guest> getAllGuests(){
		return guestRepository.getAll();
	}
	
	public User getUserById(Id id) {
		if(id.getPrefix().equals(GuestSequencer.guestPrefix))
			return guestRepository.getById(id);
		else if(id.getPrefix().equals(HostSequencer.hostPrefix))
			return hostRepository.getById(id);
		else 
			return administratorRepository.getById(id);
	}
	
	public Iterable<User> getSearchResults(String input){
		String[] inputBits = parseInput(input.trim().toLowerCase());
		ArrayList<User> results = new ArrayList<User>();
		
		if(inputBits.length == 2) {
			results.addAll((ArrayList<User>)getUsersByNameAndSurname(inputBits[0], inputBits[1]));
		} else {
			for(String temp : inputBits) {
				results.addAll((ArrayList)getUserByName(temp));
				results.addAll((ArrayList) getUsersBySurname(temp));
			}
		}
		return results;
	}

	private Iterable<User> getUsersByNameAndSurname(String s1, String s2) {
		ArrayList<User> results = new ArrayList<User>();
		results.addAll((ArrayList)hostRepository.getUsersByNameAndSurname(s1, s2));
		results.addAll((ArrayList)guestRepository.getUsersByNameAndSurname(s1, s2));
		results.addAll((ArrayList)administratorRepository.getUsersByNameAndSurname(s1, s2));
		results.addAll((ArrayList)hostRepository.getUsersByNameAndSurname(s2, s1));
		results.addAll((ArrayList)guestRepository.getUsersByNameAndSurname(s2, s1));
		results.addAll((ArrayList)administratorRepository.getUsersByNameAndSurname(s2, s1));
		return results;
	}
	
	private Iterable<User> getUsersBySurname(String temp) {
		ArrayList<User> results = new ArrayList<User>();
		results.addAll((ArrayList)hostRepository.getUsersBySurname(temp));
		results.addAll((ArrayList)guestRepository.getUsersBySurname(temp));
		results.addAll((ArrayList)administratorRepository.getUsersBySurname(temp));
		return results;
	}

	private Iterable<User> getUserByName(String temp) {
		ArrayList<User> results = new ArrayList<User>();
		results.addAll((ArrayList)hostRepository.getUsersByName(temp));
		results.addAll((ArrayList)guestRepository.getUsersByName(temp));
		results.addAll((ArrayList)administratorRepository.getUsersByName(temp));
		return results;
	}

	private String[] parseInput(String input) {
		return input.split(" ");
	}
	
	private void update(User user) {
		if(user.getRole().equals(Roles.ADMINISTRATOR))
			administratorRepository.update((Administrator) user);
		else if(user.getRole().equals(Roles.GUEST))
			guestRepository.update((Guest) user);
		else
			hostRepository.update((Host) user);
	}
}
