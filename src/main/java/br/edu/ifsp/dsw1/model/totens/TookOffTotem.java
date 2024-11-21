package br.edu.ifsp.dsw1.model.totens;

import br.edu.ifsp.dsw1.model.entity.FlightData;
import br.edu.ifsp.dsw1.model.flightstates.TookOff;

public class TookOffTotem extends Totem {

	public TookOffTotem() {
		super();
	}
	
	//Implementação do método update.
	//Nesta classe, nos importamos apenas com voos
	//com estado "TookOff".
	//Exibe um número limitado de voos que já partiram,
	//já que eles são removidos do sistema quando partem.
	//Foi decidido, arbitrariamente, exibir apenas os
	//últimos 3 voos que partiram.
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
