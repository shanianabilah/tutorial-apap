package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.MenuModel;

import javax.swing.text.html.Option;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface MenuService {
    void addMenu(MenuModel menu);
    List<MenuModel> findAllMenuByIdRestoran(Long idRestoran);
    // method untuk mendapatkan data sebuah restoran berdasarkan idMenu
    Optional<MenuModel> findMenuById(Long idMenu);
    MenuModel changeMenu(MenuModel menuModel);
    void deleteMenu(MenuModel menu);
    List<MenuModel> getListMenuOrderByHargaAsc(Long idRestoran);
}
