package com.apap.tugas1.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;

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
			
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("instansi", instansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Detail Pegawai");
		
		return "viewPegawai";
		
	}

	@RequestMapping(value= "/pegawai/termuda-tertua", method=RequestMethod.GET)
	private String pegawaiTermudaTertua(@RequestParam("idInstansi") long idInstansi,Model model) {
		List<PegawaiModel> filter = pegawaiService.allPegawai();
		filter = filter.stream()
			.filter(pegawai -> pegawai.getInstansi().getId() == idInstansi)
			.collect(Collectors.toList());
		System.out.println(idInstansi);
		Collections.sort(filter);
		model.addAttribute("termuda",filter.get(0));
		model.addAttribute("instansi",idInstansi);
		model.addAttribute("tertua",filter.get(filter.size()-1));
		return "viewTermudaTertua";
	}
	
	@RequestMapping(value= "/pegawai/carii", method=RequestMethod.GET)
	private String cariPegawai(@RequestParam("idProvinsi") int idProvinsi,@RequestParam("idInstansi") int idInstansi,@RequestParam("idJabatan") int idJabatan, Model model) {
		List<PegawaiModel> filter = pegawaiService.allPegawai();
		filter = filter.stream()
			.filter(pegawai -> pegawai.getInstansi().getProvinsi().getId() == idProvinsi)				
			.filter(pegawai -> pegawai.getInstansi().getId() == idInstansi)
			.filter(pegawai -> pegawai.getJabatanPegawai().stream().anyMatch(jabatans -> jabatans.getId() == idJabatan)).collect(Collectors.toList());
		List<ProvinsiModel> provinsi = provinsiService.allProvinsi();
		List<JabatanModel> jabatan = jabatanService.allJabatan();
		model.addAttribute("provinsi",provinsi);
		model.addAttribute("jabatan",jabatan);
		model.addAttribute("pegawai",filter);
		return "cariPegawai";
	}
	
	@RequestMapping(value= "/pegawai/cari", method=RequestMethod.GET)
	private String filterPegawai(Model model) {
		List<ProvinsiModel> provinsi = provinsiService.allProvinsi();
		List<JabatanModel> jabatan = jabatanService.allJabatan();
		model.addAttribute("provinsi",provinsi);
		model.addAttribute("jabatan",jabatan);
		return "cariPegawai";
	}
}
