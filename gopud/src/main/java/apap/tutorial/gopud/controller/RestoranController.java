package apap.tutorial.gopud.controller;

import java.util.Collections;
import java.util.List;

import apap.tutorial.gopud.model.MenuModel;
import apap.tutorial.gopud.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import apap.tutorial.gopud.model.RestoranModel;
import apap.tutorial.gopud.service.RestoranService;

@Controller
public class RestoranController{
    @Qualifier("restoranServiceImpl")
    @Autowired
    private RestoranService restoranService;

    @Autowired
    private MenuService menuService;

    /*@RequestMapping("/")
    public String home() {return "home";}*/

    // URL mapping yang digunakan untuk mengakses halaman add restoran
    @RequestMapping(value = "/restoran/add", method = RequestMethod.GET)
    public String addRestoranFormPage(Model model){
        RestoranModel newRestoran = new RestoranModel();
        model.addAttribute("restoran", newRestoran);
        return "form-add-restoran";
    }

    // URL mapping yang digunakan untuk submit form yang telah anda masukkan pada halaman add restoran
    @RequestMapping(value = "/restoran/add", method = RequestMethod.POST)
    public String addRestoranSubmit(@ModelAttribute RestoranModel restoran, Model model) {
        restoranService.addRestoran(restoran);
        model.addAttribute("namaResto", restoran.getNama());
        return "add-restoran";
    }

    // URL mapping view
    @RequestMapping(path = "/restoran/view", method = RequestMethod.GET)
    public String view(
            // Request Parameter untuk dipass
            @RequestParam(value = "idRestoran") Long idRestoran, Model model
    ){
        // Mengambil objek RestoranModel yang dituju
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran).get();

        List<MenuModel> menuList = menuService.getListMenuOrderByHargaAsc(restoran.getIdRestoran());
        restoran.setListMenu(menuList);
        // Add model restoran ke "resto" untuk dirender
        model.addAttribute("resto", restoran);

        /*List<MenuModel> menuList = menuService.findAllMenuByIdRestoran(restoran.getIdRestoran());
        model.addAttribute("menuList", menuList);*/

        // Return view template
        return "view-restoran";
    }

    // API yang digunakan untuk menuju halaman form change restoran
    @RequestMapping(value = "restoran/change/{idRestoran}", method = RequestMethod.GET)
    public String changeRestoranFormPage(@PathVariable Long idRestoran, Model model){
        // Mengambil existing data restoran
        RestoranModel existingRestoran = restoranService.getRestoranByIdRestoran(idRestoran).get();
        model.addAttribute("restoran", existingRestoran);
        return "form-change-restoran";
    }

    // API yang digunakan untuk submit form change restoran
    @RequestMapping(value = "restoran/change/{idRestoran}", method = RequestMethod.POST)
    public String changeRestoranFormSubmit(@PathVariable Long idRestoran, @ModelAttribute RestoranModel restoran, Model model){
        RestoranModel newRestoranData = restoranService.changeRestoran(restoran);
        model.addAttribute("restoran", newRestoranData);
        return "change-restoran";
    }

    // URL mapping viewAll
    @RequestMapping("restoran/view-all")
    public String viewall(Model model){

        //Mengambil semua objek RestoranModel yang ada
        List<RestoranModel> listRestoran = restoranService.getRestoranList();
        Collections.sort(listRestoran);

        // Add model restoran ke "resto" untuk di render
        model.addAttribute("restoList", listRestoran);

        // Return view template
        return "viewall-restoran";
    }

    //URL mapping view restoran menggunakan path variable
    @RequestMapping("restoran/view/id-restoran/{idRestoran}")
    public String viewPath(@PathVariable("idRestoran") Long idRestoran, Model model){
        // Mengambil objek RestoranModel yang dituju
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran).get();

        // Add model restoran ke "resto" untuk dirender
        model.addAttribute("resto", restoran);
        List<MenuModel> menuList = menuService.findAllMenuByIdRestoran(restoran.getIdRestoran());
        model.addAttribute("menuList", menuList);

        // Return view template
        return "view-restoran";
    }

    //URL mapping ganti nomor telepon
    @RequestMapping("restoran/update/id-restoran/{idRestoran}/nomor-telepon/{nomorTelepon}")
    public String updateTelepon(@PathVariable("nomorTelepon") Integer nomorTelepon, @PathVariable("idRestoran") Long idRestoran, Model model){
        // Mengambil objek RestoranModel yang dituju
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran).get();
        restoran.setNomorTelepon(nomorTelepon);

        // Add model restoran ke "resto" untuk dirender
        model.addAttribute("resto", restoran);

        // Return view template
        return "update-telepon";
    }

    // URL mapping delete resto
    @RequestMapping("restoran/delete/id/{idRestoran}")
    public String deleteRestoran(@PathVariable("idRestoran") Long idRestoran, Model model){
        // Mengambil objek RestoranModel yang dituju
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran).get();
        if(restoran == null || idRestoran.equals("")){
            return "null-error";
        }
        else{
            // Add model restoran ke "resto" untuk dirender
            if (restoran.getListMenu().isEmpty()) {
                model.addAttribute("resto", restoran);

                restoranService.deleteResto(restoran);

                // Return view template
                return "delete-resto";
            }
            else{
                return "delete-error";
            }
        }
    }
}