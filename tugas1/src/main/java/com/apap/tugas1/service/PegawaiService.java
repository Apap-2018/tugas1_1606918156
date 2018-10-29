package com.apap.tugas1.service;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.PegawaiDb;
import com.apap.tugas1.model.InstansiModel;

import java.util.List;
import java.util.Optional;

public interface PegawaiService {
	void addPegawai(PegawaiModel pegawai);
	void deletePegawai(PegawaiModel pegawai);
	List<PegawaiModel> allPegawai();
	PegawaiModel getPegawaiDetailByNip(String nip);
	Optional<PegawaiModel> getEmployeeDetailById(Long id);
	PegawaiDb getPegawaiDb();
	String generateNip(PegawaiModel pegawai);
	double getGaji(PegawaiModel pegawai);
	
}
