package br.edu.ifsp.dsw1.model.totens;

import br.edu.ifsp.dsw1.model.entity.FlightData;
import br.edu.ifsp.dsw1.model.flightstates.Arriving;

public class ArrivingTotem extends Totem {

	public ArrivingTotem() {
		super();
	}
	
	@Override
	public void update(FlightData flight) {
		if(flight.getState() instanceof Arriving) {
			flights.add(flight);
		}else{
			flights.remove(flight);
		}
	}

}
