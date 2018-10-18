package com.apap.tugas1.service;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.InstansiModel;

import java.util.List;
import java.util.Optional;

public interface PegawaiService {
	void addPegawai(PegawaiModel pegawai);
	void deletePegawai(PegawaiModel pegawai);
	List<PegawaiModel> allPegawai(InstansiModel instansi);
	PegawaiModel getPegawaiDetailByNip(String nip);
	Optional<PegawaiModel> getEmployeeDetailById(Long id);
}
