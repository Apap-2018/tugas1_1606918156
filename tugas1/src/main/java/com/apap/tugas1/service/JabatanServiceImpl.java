package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	@Autowired
	private JabatanDb jabatanDb;

	@Override
	public JabatanDb getJabatanDb() {
		return jabatanDb;
	}
	
	@Override
	public JabatanModel getJabatanDetailByNama(String nama) {
		return jabatanDb.findByNama(nama);
	}
	
	@Override
	public JabatanModel getJabatanDetailById(long id) {
		return jabatanDb.findById(id);
	}
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}
}
