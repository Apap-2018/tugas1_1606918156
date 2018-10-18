package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;

	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "Home");
		JabatanDb jabatanDb = jabatanService.getJabatanDb();
		List<JabatanModel> listJabatan = jabatanDb.findAll();
		
		InstansiDb instansiDb = instansiService.getInstansiDb();
		List<InstansiModel> listInstansi = instansiDb.findAll();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai")
	public String viewPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		InstansiModel instansi = pegawai.getInstansi();
		List<JabatanModel> listJabatan = pegawai.getJabatanPegawai();
		
		if(pegawai==null) {
			model.addAttribute("error", true);
			model.addAttribute("pegawai", null);
			return "viewPegawai";
		}
			
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("instansi", instansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Detail Pegawai");
		
		return "viewPegawai";
		
	}
	
}
