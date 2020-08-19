package services;

import java.util.ArrayList;

import enums.Gender;
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
	
	public Iterable<User> searchByCriteria(String input, Roles role, Gender gender) {
		ArrayList<User> retVal = (ArrayList)getSearchResults(input);

		retVal = filterByGender(gender, retVal);
		retVal = filterByRole(role, retVal);
		
		return retVal;
	}

	private ArrayList<User> filterByGender(Gender gender, ArrayList<User> retVal) {
		if(gender != null) {
			for(User temp : retVal) {
				if(!temp.getGender().equals(gender))
					retVal.remove(temp);
			}
		}
		return retVal;
	}

	private ArrayList<User> filterByRole(Roles role, ArrayList<User> retVal) {
		if(role != null) {
			for(User temp : retVal) {
				if(!temp.getRole().equals(role)) {
					retVal.remove(role);
				}
			}
		}
		
		return retVal;
	}
	
	public Iterable<User> getSearchResults(String input){
		String[] inputBits = parseInput(input.trim().toLowerCase());
		ArrayList<User> results = new ArrayList<User>();
		
		if(inputBits.length == 2) {
			results.addAll((ArrayList<User>)getUsersByNameAndSurname(inputBits[0], inputBits[1]));
		} else {
			for(String temp : inputBits) {
				results.add(getUserByUsername(temp));
				results.addAll(getUserByName(temp));
				results.addAll(getUsersBySurname(temp));
			}
		}
		return unique(results);
	}
	

	private User getUserByUsername(String temp) {
		ArrayList<User> retVal = new ArrayList<User>(); 
		if(hostRepository.getUserByUsername(temp) != null)
			return hostRepository.getUserByUsername(temp);
		else if (guestRepository.getUserByUsername(temp) != null)
			return guestRepository.getUserByUsername(temp);
		else 
			return administratorRepository.getUserByUsername(temp);
	}

	private Iterable<User> unique(ArrayList<User> results) {
		ArrayList<User> retVal = new ArrayList<User>();
		for(User temp : results) {
			if(!retVal.contains(temp))
				retVal.add(temp);
		}
		return retVal;
	}

	private ArrayList<User> getUsersByNameAndSurname(String s1, String s2) {
		ArrayList<User> results = new ArrayList<User>();
		results.addAll((ArrayList)hostRepository.getUsersByNameAndSurname(s1, s2));
		results.addAll((ArrayList)guestRepository.getUsersByNameAndSurname(s1, s2));
		results.addAll((ArrayList)administratorRepository.getUsersByNameAndSurname(s1, s2));
		results.addAll((ArrayList)hostRepository.getUsersByNameAndSurname(s2, s1));
		results.addAll((ArrayList)guestRepository.getUsersByNameAndSurname(s2, s1));
		results.addAll((ArrayList)administratorRepository.getUsersByNameAndSurname(s2, s1));
		return results;
	}
	
	private ArrayList<User> getUsersBySurname(String temp) {
		ArrayList<User> results = new ArrayList<User>();
		results.addAll((ArrayList)hostRepository.getUsersBySurname(temp));
		results.addAll((ArrayList)guestRepository.getUsersBySurname(temp));
		results.addAll((ArrayList)administratorRepository.getUsersBySurname(temp));
		return results;
	}

	private ArrayList<User> getUserByName(String temp) {
		ArrayList<User> results = new ArrayList<User>();
		results.addAll((ArrayList)hostRepository.getUsersByName(temp));
		results.addAll((ArrayList)guestRepository.getUsersByName(temp));
		results.addAll((ArrayList)administratorRepository.getUsersByName(temp));
		return results;
	}

	private String[] parseInput(String input) {
		return input.split(" ");
	}

	public Iterable<User> hostSearch(String input) {
		Iterable<User> allResults = getSearchResults(input);
		ArrayList<User> retVal = new ArrayList<User>();
		for(User temp : allResults) {
			retVal.add(temp);
		}
		return retVal;
	}
	
}
