package com.apap.tugas1.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "instansi")
public class InstansiModel implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "deskripsi", nullable = false)
	private String deskripsi;
	
	@OneToMany(mappedBy = "instansi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PegawaiModel> listPegawai;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return nama;
	}

	public void setName(String nama) {
		this.nama = nama;
	}
	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public List<PegawaiModel> getListPegawai() {
		return listPegawai;
	}

	public void setListPegawai(List<PegawaiModel> listPegawai) {
		this.listPegawai = listPegawai;
	}

	public Object getProvinsi() {
		// TODO Auto-generated method stub
		return null;
	}
}

