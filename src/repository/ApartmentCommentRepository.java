package repository;

import java.io.File;
import java.util.ArrayList;

import converters.ApartmentCommentConverter;
import exceptions.IdWriteException;
import model.ApartmentComment;
import model.Guest;
import model.Id;
import stream.Stream;

public class ApartmentCommentRepository implements IRepository<ApartmentComment, Id>{

	private Stream stream;
	private ApartmentCommentConverter apartmentCommentConverter;
	private String apartmentCommentFilePath = "data/ApartmentComments.dat";
	private File apartmentCommentFile;
	private GuestRepository guestRepository;
	private UnitRepository unitRepository;
	
	public ApartmentCommentRepository(Stream stream, GuestRepository guestRepository) {
		this.stream = stream;
		apartmentCommentConverter = new ApartmentCommentConverter();
		apartmentCommentFile = new File(apartmentCommentFilePath);
		this.guestRepository = guestRepository;
	}
	
	
	
	private Iterable<ApartmentComment> bindWithGuest(Iterable<ApartmentComment> allComments){
		ArrayList<ApartmentComment> retVal = new ArrayList<ApartmentComment>();
		for(ApartmentComment temp : allComments) {
			temp.setGuest(guestRepository.getById(temp.getGuest().getId()));
			retVal.add(temp);
		}
		return retVal;
	}
	
	private Iterable<ApartmentComment> bindWithUnit(Iterable<ApartmentComment> allComments){
		ArrayList<ApartmentComment> retVal = new ArrayList<ApartmentComment>();
		for(ApartmentComment temp : allComments) {
			temp.setUnit(unitRepository.getById(temp.getUnit().getId()));
			retVal.add(temp);
		}
		return retVal;
	}



	@Override
	public ApartmentComment create(ApartmentComment entity) {
		try {
			checkId(entity.getId());
			stream.writeToFile(apartmentCommentConverter.ConvertToJSON(entity), apartmentCommentFile);
			return entity;
		} catch (IdWriteException e) {
			e.printStackTrace();
		}
		return null;
	}



	private void checkId(Id id) throws IdWriteException{
		if(getById(id) != null)
			throw new IdWriteException("ApartmentComment id already in use: " + id.toString());
	}



	@Override
	public ApartmentComment getById(Id id) {
		for(ApartmentComment temp : getAll()) {
			if(temp.getId().equals(id))
				return temp;
		}
		return null;
	}



	@Override
	public Iterable<ApartmentComment> getAll() {
		ArrayList<ApartmentComment> allComments = (ArrayList)getAllUnbound();
		allComments = (ArrayList) bindWithGuest(allComments);
		allComments = (ArrayList) bindWithUnit(allComments);
		return allComments;
	}

	public Iterable<ApartmentComment> getAllUnbound() {
		ArrayList<String> allCommentsString = (ArrayList)stream.readFromFile(apartmentCommentFile);
		ArrayList<ApartmentComment> allComments= new ArrayList<ApartmentComment>();
		for(String temp : allCommentsString) {
			if(!apartmentCommentConverter.ConvertFromJSON(temp).isDeleted())
				allComments.add(apartmentCommentConverter.ConvertFromJSON(temp));
		}
		return allComments;
	}

	@Override
	public void update(ApartmentComment entity) {
		ArrayList<ApartmentComment> allComments= (ArrayList)getAll();
		StringBuilder backup = new StringBuilder();
		for(ApartmentComment temp : allComments) {
			if(!temp.getId().equals(entity.getId())) {
				backup.append(apartmentCommentConverter.ConvertToJSON(temp));
				backup.append("\n");
			} else {
				backup.append(apartmentCommentConverter.ConvertToJSON(entity));
				backup.append("\n");
			}
		}
		
		stream.blankOutFile(apartmentCommentFile);
		stream.writeToFile(backup.toString(), apartmentCommentFile);
	}



	@Override
	public void delete(ApartmentComment entity) {
		entity.Delete();
		update(entity);
		
	}
	

}
