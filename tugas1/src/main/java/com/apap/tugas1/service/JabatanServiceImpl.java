package com.apap.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Override
	public void deleteJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.delete(jabatan);
		
	}

	@Override
	public void updateJabatan(JabatanModel jabatan) {
		for (int i = 0; i < jabatanDb.findAll().size(); i++) {
			if (jabatanDb.findAll().get(i).getId() == (jabatan.getId())) {
				
				JabatanModel archive = jabatanDb.findAll().get(i);
				int idx = jabatanDb.findAll().indexOf(archive);
				
				jabatanDb.findAll().get(idx).setNama(jabatan.getNama());
				jabatanDb.findAll().get(idx).setDeskripsi(jabatan.getDeskripsi());
				jabatanDb.findAll().get(idx).setGaji_pokok(jabatan.getGaji_pokok());				
			}
		}
		
	}
}
