package com.apap.tugas1.service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDb employeeDb;
	
	@Override
	public PegawaiDb getPegawaiDb() {
		return employeeDb;
	}
	
	@Override
	public void addPegawai(PegawaiModel employee) {
		employeeDb.save(employee);
	}
	
	@Override
	public Optional<PegawaiModel> getEmployeeDetailById(Long id) {
		// TODO Auto-generated method stub
		return employeeDb.findById(id);
	}
	
	@Override
	public void deletePegawai(PegawaiModel employee){
		// TODO Auto-generated method stub
		employeeDb.delete(employee);
		
	}
	
	@Override
	public List<PegawaiModel> allPegawai() {
		// TODO Auto-generated method stub
		return employeeDb.findAll();
	}

	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		// TODO Auto-generated method stub
		return employeeDb.findByNip(nip);
	}

	@Override
	public String generateNip(PegawaiModel pegawai) {		
		String nipNew = "";
		
		String lastCode = "0";
		String firstCode = "";
		int urutanPegawai = 0;
		
		DateFormat df = new SimpleDateFormat("ddMMYY");
		Date tglLahir = pegawai.getTanggal_lahir();
		String formatted = df.format(tglLahir);
		System.out.println("date :" + formatted);
		
		Long idInstansi = pegawai.getInstansi().getId();
		System.out.println("kode instansi :"+ idInstansi);
		
		List<PegawaiModel> filter = this.allPegawai();

		  filter = filter.stream()
			.filter(peg -> peg.getInstansi().getId() == pegawai.getInstansi().getId())
			.filter(peg -> peg.getTanggal_lahir().equals(pegawai.getTanggal_lahir()))
			.filter(peg -> peg.getTahun_masuk().equalsIgnoreCase(pegawai.getTahun_masuk()))
			.collect(Collectors.toList());
	
		  System.out.println(filter.size());
		  
		if (filter.isEmpty()) {
			  urutanPegawai += 1;
		}
		  
		else {
			urutanPegawai = filter.size() + 1;
		}
		
		if (Integer.toString(urutanPegawai).length() == 1) {
			lastCode += Integer.toString(urutanPegawai);
			nipNew = idInstansi + formatted + pegawai.getTahun_masuk() + lastCode;
		}
		
		else {
			firstCode += Integer.toString(urutanPegawai);
			nipNew = idInstansi + formatted + pegawai.getTahun_masuk() + firstCode;
		}
		
		return nipNew;
	}
	
	@Override
	public double getGaji(PegawaiModel pegawai) {
		double gajiPokok = 0;
		double totalGaji = 0;
		
		for (JabatanModel jabatan : pegawai.getJabatanPegawai()) {
			double temp = jabatan.getGaji_pokok();
			
			if (temp > gajiPokok) {
				gajiPokok = temp;
			}
		}
		
		double tunjangan = ((pegawai.getInstansi().getProvinsi().getPresentaseTunjangan() / 100) * gajiPokok);
		totalGaji = gajiPokok + tunjangan;
		
		return totalGaji;
	}
}
