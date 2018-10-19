package com.apap.tugas1.service;
import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.repository.InstansiDb;

public interface InstansiService {
	Optional<InstansiModel> getInstansiDetailById(Long id);
	InstansiDb getInstansiDb();
	void addInstansi(InstansiModel instance);
	void deleteInstansi(InstansiModel instance);
	List<InstansiModel> allInstansi();
}

