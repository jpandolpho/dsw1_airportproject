package br.edu.ifsp.dsw1.model.totens;

import br.edu.ifsp.dsw1.model.entity.FlightData;
import br.edu.ifsp.dsw1.model.flightstates.TakingOff;

public class TakingOffTotem extends Totem {

	public TakingOffTotem() {
		super();
	}
	
	@Override
	public void update(FlightData flight) {
		if(flight.getState() instanceof TakingOff) {
			flights.add(flight);
		}else{
			flights.remove(flight);
		}
	}

}
