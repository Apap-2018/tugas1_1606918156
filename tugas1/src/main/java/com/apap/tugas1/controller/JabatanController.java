package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.PegawaiDb;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;


@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;

	@Autowired
	PegawaiService pegawaiService;
	
	@Autowired
	InstansiService instansiService;
	
	private long id1;

	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "addJabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan) {
		jabatanService.addJabatan(jabatan);
		return "add";
	}
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	public String viewJabatan(@RequestParam("idJabatan") Long idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan);
		
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("title", "Detail Jabatan");
		System.out.println(jabatan);
		return "viewJabatan";
		
	}
	@RequestMapping(value= "/jabatan/ubah", method = RequestMethod.GET)
	private String update(@RequestParam (value = "idJabatan") Long idJabatan, Model model) {

		JabatanModel jabatan1 = jabatanService.getJabatanDetailById(idJabatan);

		this.id1 = idJabatan;
		jabatan1.setId(id1);
		model.addAttribute("jabatan1", jabatan1);
		model.addAttribute("jabatan", new JabatanModel());
		model.addAttribute("title", "Ubah Data Jabatan");
		
		return "updateJabatan";			
	}
	
	@RequestMapping(value= "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {

		jabatan.setId(id1);
		jabatanService.updateJabatan(jabatan);
		model.addAttribute("title", "Ubah Data Jabatan");
		
		return "update";
	}
	@RequestMapping("/jabatan/viewall")
	public String viewAll (Model model) {
		
		JabatanDb jabatanDb = jabatanService.getJabatanDb();
		List<JabatanModel> listJabatan = jabatanDb.findAll();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Lihat Semua Jabatan");
		return "viewallJabatan";	
	}
	
	@RequestMapping(value= "/jabatan/hapus/{idJabatan}", method = RequestMethod.POST)
	private String deleteJabatan(@PathVariable(value = "idJabatan") Long idJabatan, Model model) {
		
		PegawaiDb pegawaiDb = pegawaiService.getPegawaiDb();
		JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan);
		jabatan.setId(idJabatan);
		jabatanService.deleteJabatan(jabatan);
		
		model.addAttribute("title", "Hapus Jabatan");
		return "deleteJabatan";
				
	}
	
	
	
	
}
