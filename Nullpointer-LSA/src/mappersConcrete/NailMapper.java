package mappersConcrete;

import java.util.ArrayList;
import datasource.NailDTO;
import datasourceConcrete.NailsGateway;
import exception.DatabaseException;
import sharedDomain.Nail;
import sharedDomain.NailAbstract;

/**
 * Nail Mapper
 * 
 * @author clabaugh10
 *
 */
public class NailMapper extends NailAbstract {
	
	private NailsGateway nailGateway;
	private Nail nail;

	/**
	 * Finder Constructor
	 * 
	 * @throws DatabaseException
	 */
	public NailMapper(int nailID) throws DatabaseException 
	{
		this.nailGateway = new NailsGateway(nailID);	
		buildNail();
	}

	/**
	 * Creation Constructor
	 * 
	 * @throws DatabaseException
	 */
	public NailMapper(String upc, int manufacturerID, int price, double length, int numberInBox) throws DatabaseException 
	{
		this.nailGateway = new NailsGateway(upc, manufacturerID, price, length, numberInBox);
		buildNail();
	}

	/**
	 * 
	 * @return ArrayList of Tool objects
	 * @throws DatabaseException
	 */
	public static ArrayList<Nail> findAll() throws DatabaseException {
		ArrayList<Nail> nails = new ArrayList<Nail>();
		ArrayList<NailDTO> dtos = NailsGateway.findAll();

		for (int i = 0; i < dtos.size(); i++) {
			nails.add(new Nail(dtos.get(i).getId(),dtos.get(i).getUpc(), dtos.get(i).getManufacturerID(), dtos.get(i).getPrice(),
					dtos.get(i).getLength(), dtos.get(i).getNumberInBox()));
		}

		return nails;
	}

	/**
	 * 
	 * @throws DatabaseException
	 */
	@Override
	public void persist() throws DatabaseException {
		nailGateway.setUpc(nail.getUpc());
		nailGateway.setManufacturerID(nail.getManufacturerID());
		nailGateway.setPrice(nail.getPrice());
		nailGateway.setLength(nail.getLength());
		nailGateway.setNumberInBox(nail.getNumberInBox());
		nailGateway.persist();
	}

	/**
	 * 
	 * @throws DatabaseException
	 */
	@Override
	public void delete() throws DatabaseException {
		nailGateway.delete();
	}

	@Override
	public Nail getNail() {
        return nail;
	}

	@Override
	protected void buildNail() {
		nail = new Nail(nailGateway.getID(), nailGateway.getUpc(), nailGateway.getManufacturerID(), nailGateway.getPrice(), nailGateway.getLength(), nailGateway.getNumberInBox());
	}

	@Override
	public int getNailID() {
		return nailGateway.getID();
	}


}
