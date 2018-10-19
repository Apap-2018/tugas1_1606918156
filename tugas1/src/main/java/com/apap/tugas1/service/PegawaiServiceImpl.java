package com.apap.tugas1.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.PegawaiDb;

import java.util.List;

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
}
