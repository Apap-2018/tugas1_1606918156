package com.apap.tugas1.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;


@Controller
public class InstansiController {
	@Autowired
	private JabatanService jabatanService;

	@Autowired
	PegawaiService pegawaiService;
	
	@Autowired
	InstansiService instansiService;
	
	@RequestMapping(value = "/instansi/view", method = RequestMethod.GET)
	public String viewJabatan(@RequestParam("idInstansi") Long idInstansi, Model model) {
		Optional<InstansiModel> instansi = instansiService.getInstansiDetailById(idInstansi);
		
		model.addAttribute("instansi", instansi);
		model.addAttribute("title", "Detail Pegawai");
		return "viewTermudaTertua";
		
	}
	
}
