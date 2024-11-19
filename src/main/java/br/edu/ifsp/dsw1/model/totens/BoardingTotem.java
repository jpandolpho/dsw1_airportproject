package br.edu.ifsp.dsw1.model.totens;

import br.edu.ifsp.dsw1.model.entity.FlightData;
import br.edu.ifsp.dsw1.model.flightstates.Boarding;

public class BoardingTotem extends Totem {

	public BoardingTotem() {
		super();
	}
	
	@Override
	public void update(FlightData flight) {
		if(flight.getState() instanceof Boarding) {
			flights.add(flight);
		}else{
			flights.remove(flight);
		}
	}

}
