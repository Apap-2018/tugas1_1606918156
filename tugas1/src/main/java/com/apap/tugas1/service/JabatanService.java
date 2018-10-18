package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

public interface JabatanService {
	JabatanModel getJabatanDetailByNama(String nama);
	JabatanModel getJabatanDetailById(long id);
	JabatanDb getJabatanDb();
	void addJabatan(JabatanModel jabatan);
}
