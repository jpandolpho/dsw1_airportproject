package br.edu.ifsp.dsw1.model.totens;

import br.edu.ifsp.dsw1.model.entity.FlightData;
import br.edu.ifsp.dsw1.model.flightstates.TookOff;

public class TookOffTotem extends Totem {

	public TookOffTotem() {
		super();
	}
	
	@Override
	public void update(FlightData flight) {
		if(flight.getState() instanceof TookOff) {
			flights.add(flight);
		}
		if(flights.size()>3) {
			flights.remove(0);
		}
	}

}
