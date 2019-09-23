package apap.tutorial.gopud.service;

import java.util.List;
import apap.tutorial.gopud.model.RestoranModel;

public interface RestoranService{
    // method untuk menambah restoran
    void addRestoran(RestoranModel restoran);


    // method untuk mendapatkan semua data restoran yang tersimpan
    List<RestoranModel> getRestoranList();

    // method untuk mendapatkan data sebuah restoran berdasarkan idRestoran
    RestoranModel getRestoranByIdRestoran(String idRestoran);

    // method untuk delete resto
    void deleteResto(RestoranModel resto);
}