package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		System.out.println(listJabatan);
		
		int gaji = (int) pegawai.getGaji();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("instansi", instansi);
		model.addAttribute("gaji", gaji);
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
	private String cariPegawai(@RequestParam("idProvinsi") int idProvinsi, @RequestParam("idInstansi") int idInstansi, @RequestParam("idJabatan") int idJabatan, Model model) {
        List<PegawaiModel> filter = pegawaiService.allPegawai();
        if (idProvinsi != 0) {
            filter = filter.stream()
                    .filter(pegawai -> pegawai.getInstansi().getProvinsi().getId() == idProvinsi).collect(Collectors.toList());
        }
        if (idInstansi != 0) {
            filter = filter.stream()
                    .filter(pegawai -> pegawai.getInstansi().getId() == idInstansi).collect(Collectors.toList());
        }

        if (idJabatan != 0) {
            filter = filter.stream()
                    .filter(pegawai -> pegawai.getJabatanPegawai().stream()
                            .anyMatch(jabatans -> jabatans.getId() == idJabatan)).collect(Collectors.toList());
        }

        List<ProvinsiModel> provinsi = provinsiService.allProvinsi();
        List<JabatanModel> jabatan = jabatanService.allJabatan();
        model.addAttribute("provinsi", provinsi);
        model.addAttribute("jabatan", jabatan);
        model.addAttribute("pegawai", filter);
        return "cariPegawai";
	}
	
	@RequestMapping(value= "/get/instansi/{provinsi}", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> bar(@PathVariable(value = "provinsi") String provinsi) {
	    final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    ProvinsiModel province = provinsiService.getProvinceDetailById(Long.parseLong(provinsi));
	    List<InstansiModel> instances = province.getInstansiList();
	    StringBuilder sb = new StringBuilder();
	    
	    for (InstansiModel s : instances) {
	    	sb.append(s.getId());
	    	sb.append(" // ");
	        sb.append(s.getNama());
	        sb.append(" // ");
	    }

	    return new ResponseEntity<String>("{\"instansi\": \""+ sb.toString() + "\"}", httpHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value= "/pegawai/cari", method=RequestMethod.GET)
	private String filterPegawai(Model model) {
		List<ProvinsiModel> provinsi = provinsiService.allProvinsi();
		List<JabatanModel> jabatan = jabatanService.allJabatan();
		model.addAttribute("provinsi", provinsi);
		model.addAttribute("jabatan", jabatan);
		return "cariPegawai";
	}
	
	@RequestMapping(value= "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		if (pegawai.getJabatanPegawai() == null) {
			pegawai.setJabatanPegawai(new ArrayList());
		}
		
		pegawai.getJabatanPegawai().add(new JabatanModel());
		
		List<ProvinsiModel> listProvinsi = provinsiService.allProvinsi();
		List<JabatanModel> listJabatan = jabatanService.allJabatan();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Tambah Pegawai");

		return "addPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah/instansi",method = RequestMethod.GET)
	private @ResponseBody List<InstansiModel> checkInstansiTambah(@RequestParam(value="provinsiId") int provinsiId) {
		ProvinsiModel prov1 = provinsiService.getProvinceDetailById(provinsiId);
		
		return prov1.getListInstansi();
	}
	

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"submit"})
	private String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {		
		String nip = pegawaiService.generateNip(pegawai);
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		
		model.addAttribute("nip", nip);
		model.addAttribute("title", "Tambah Pegawai");
		
		return "add";
	}
}

class Pegawai {   
	PegawaiModel pegawai;
	   

	public PegawaiModel getPegawai() {
		return pegawai;
	}   
}
