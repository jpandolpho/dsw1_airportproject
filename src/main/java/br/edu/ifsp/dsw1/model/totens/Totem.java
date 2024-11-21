//Classe base para os totens.
//Cada um deles possui uma lista de voos, possuindo apenas
//voos que são pertinentes a ele.
//A classe ArrivingTotem, por exemplo, se importa apenas
//com voos com estado Arriving.
package br.edu.ifsp.dsw1.model.totens;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.edu.ifsp.dsw1.model.entity.FlightData;
import br.edu.ifsp.dsw1.model.observer.FlightDataObserver;

public abstract class Totem implements FlightDataObserver {

	protected List<FlightData> flights;
	
	public Totem() {
		flights = new LinkedList<FlightData>();
	}
	
	public void remove(FlightData flight) {
		flights.remove(flight);
	}
	
	//Função para recuperação dos voos associados àquele totem.
	public List<FlightData> getAllFlights(){
		return new ArrayList<FlightData>(flights);
	}
}
