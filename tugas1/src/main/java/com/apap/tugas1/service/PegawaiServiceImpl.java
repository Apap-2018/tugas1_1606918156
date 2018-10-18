package com.apap.tugas1.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.repository.PegawaiDb;

import java.util.List;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDb employeeDb;
	
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
	public List<PegawaiModel> allPegawai(InstansiModel instance) {
		// TODO Auto-generated method stub
		List<PegawaiModel> employees = employeeDb.findAll();
		return employees.stream().filter(employee -> employee.getInstansi().getId() == instance.getId()).collect(Collectors.toList());
	}

	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		// TODO Auto-generated method stub
		return employeeDb.findByNip(nip);
	}
}
