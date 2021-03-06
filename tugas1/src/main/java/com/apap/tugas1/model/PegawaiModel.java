package com.apap.tugas1.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "pegawai")
public class PegawaiModel implements Serializable,Comparable<PegawaiModel> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNip() {
		return nip;
	}


	public void setNip(String nip) {
		this.nip = nip;
	}


	public String getNama() {
		return nama;
	}


	public void setNama(String nama) {
		this.nama = nama;
	}


	public String getTempat_lahir() {
		return tempat_lahir;
	}


	public void setTempat_lahir(String tempat_lahir) {
		this.tempat_lahir = tempat_lahir;
	}


	public Date getTanggal_lahir() {
		return tanggal_lahir;
	}


	public void setTanggal_lahir(Date tanggal_lahir) {
		this.tanggal_lahir = tanggal_lahir;
	}


	public String getTahun_masuk() {
		return tahun_masuk;
	}


	public void setTahun_masuk(String tahun_masuk) {
		this.tahun_masuk = tahun_masuk;
	}


	public InstansiModel getInstansi() {
		return instansi;
	}


	public void setInstansi(InstansiModel instanmdl) {
		this.instansi = instanmdl;
	}


	@NotNull
	@Size(max = 255)
	@Column(name = "nip", nullable = false, unique = true)
	private String nip;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tempat_lahir", nullable = false)
	private String tempat_lahir;
	
	@NotNull
	@Column(name = "tanggal_lahir", nullable = false)
	private Date tanggal_lahir;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tahun_masuk", nullable = false)
	private String tahun_masuk;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "id_instansi", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private InstansiModel instansi;

	@ManyToMany(cascade = CascadeType.ALL)
	 @JoinTable(name = "jabatan_pegawai", joinColumns = { @JoinColumn(name = "id_pegawai") }, inverseJoinColumns = { @JoinColumn(name = "id_jabatan") })
	 private List<JabatanModel> jabatanPegawai = new ArrayList<JabatanModel>();

	public List<JabatanModel> getJabatanPegawai() {
		return jabatanPegawai;
	}


	public void setJabatanPegawai(List<JabatanModel> jabatanPegawai) {
		this.jabatanPegawai = jabatanPegawai;
	}
	
	public double getGaji() {
		List<JabatanModel> listjabatan = this.getJabatanPegawai();
		System.out.println(listjabatan);
		double gajiPokok = listjabatan.stream().mapToDouble(jabatan -> jabatan.getGaji_pokok()).sum();
		double tunjangan = this.getInstansi().getProvinsi().getPresentaseTunjangan();
		return gajiPokok * tunjangan ;
	}
	
	@Override
    public int compareTo(PegawaiModel other) {
		return this.tanggal_lahir.compareTo(other.getTanggal_lahir());
    }
}


