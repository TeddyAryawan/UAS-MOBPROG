package com.BC181.Aryawan;

import java.util.Date;

public class Film {
    private int idFilm;
    private String judul;
    private Date tahun;
    private String gambar;
    private String genre;
    private String pemain;
    private String sipnosis;


    public Film(int idFilm, String judul, Date tahun, String gambar, String genre, String pemain, String sipnosis) {
        this.idFilm = idFilm;
        this.judul = judul;
        this.tahun = tahun;
        this.gambar = gambar;
        this.genre = genre;
        this.pemain = pemain;
        this.sipnosis = sipnosis;


    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTahun() {
        return tahun;
    }

    public void setTahun(Date tahun) {
        this.tahun= tahun;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPemain() {
        return pemain;
    }

    public void setPemain(String pemain) {
        this.pemain = pemain;
    }

    public String getSipnosis() {
        return sipnosis;
    }

    public void setSipnosis(String sipnosis) {
        this.sipnosis = sipnosis;
    }


}
