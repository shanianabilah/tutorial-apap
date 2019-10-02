package apap.tutorial.gopud.service;

import java.util.List;
import java.util.Optional;

import apap.tutorial.gopud.model.RestoranModel;

public interface RestoranService{
    // method untuk menambah restoran
    void addRestoran(RestoranModel restoran);


    // method untuk mendapatkan semua data restoran yang tersimpan
    List<RestoranModel> getRestoranList();

    // method untuk mendapatkan data sebuah restoran berdasarkan idRestoran
    Optional<RestoranModel> getRestoranByIdRestoran(Long idRestoran);

    RestoranModel changeRestoran(RestoranModel restoranModel);

    // method untuk delete resto
    void deleteResto(RestoranModel resto);
}