package com.example.demo.serviceAdmin;

import java.util.List;

import com.example.demo.entity.Asientos;

public interface IAsientosService {
	
	List<Asientos> getAllAsientos();
	Asientos getAsientosById(Long id);
	Asientos saveAsiento(Asientos asiento);
	void deleteAsiento(Long id);
}
