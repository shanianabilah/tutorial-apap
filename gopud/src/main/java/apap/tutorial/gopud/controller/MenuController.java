package apap.tutorial.gopud.controller;

import apap.tutorial.gopud.model.MenuModel;
import apap.tutorial.gopud.model.RestoranModel;
import apap.tutorial.gopud.service.MenuService;
import apap.tutorial.gopud.service.RestoranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class MenuController {
    @Autowired
    MenuService menuService;

    @Qualifier("restoranServiceImpl")
    @Autowired
    RestoranService restoranService;

    @RequestMapping(value = "/menu/add/{idRestoran}", method = RequestMethod.GET)
    private String addProductFormPage(@PathVariable(value = "idRestoran") Long idRestoran, Model model){
        MenuModel menu = new MenuModel();
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran).get();
        ArrayList<MenuModel> list = new ArrayList<MenuModel>();
        list.add(menu);
        restoran.setListMenu(list);

        model.addAttribute("menu", menu);
        model.addAttribute("restoran",restoran);

        return "form-add-menu";
    }

    @RequestMapping(value = "menu/add/{idRestoran}", method = RequestMethod.POST, params={"save"})
    private String addProductSubmit(@ModelAttribute RestoranModel restoran, Model model){
        RestoranModel resto = restoranService.getRestoranByIdRestoran(restoran.getIdRestoran()).get();
        for (MenuModel menu : restoran.getListMenu()){
            menu.setRestoran(resto);
            menuService.addMenu(menu);
        }
//        model.addAttribute("nama", menu.getNama());
        return "add-menu";
    }

    @RequestMapping(value ="/menu/add/{idRestoran}", method=RequestMethod.POST, params= {"addRow"})
    private String addRow(@ModelAttribute RestoranModel restoran, Model model) {
        if(restoran.getListMenu() == null) {
            restoran.setListMenu(new ArrayList<MenuModel>());
        }
        restoran.getListMenu().add(new MenuModel());

        model.addAttribute("restoran", restoran);
        return "form-add-menu";
    }

    @RequestMapping(value ="/menu/add/{idRestoran}", method=RequestMethod.POST, params= {"removeRow"})
    private String removeRow(@ModelAttribute RestoranModel restoran , final HttpServletRequest req, Model model) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        restoran.getListMenu().remove(rowId.intValue());

        model.addAttribute("restoran" , restoran);
        return "form-add-menu";
    }

    // API yang digunakan untuk menuju halaman form change menu
    @RequestMapping(value = "menu/change/{idMenu}", method = RequestMethod.GET)
    public String changeMenuFormPage(@PathVariable Long idMenu, Model model){
        // Mengambil existing data menu
        MenuModel existingMenu = menuService.findMenuById(idMenu).get();
        model.addAttribute("menu", existingMenu);
        return "form-change-menu";
    }

    // API yang digunakan untuk submit form change menu
    @RequestMapping(value = "menu/change/{idMenu}", method = RequestMethod.POST)
    public String changeRestoranFormSubmit(@PathVariable Long idMenu, @ModelAttribute MenuModel menu, Model model){
        MenuModel newMenuData = menuService.changeMenu(menu);
        model.addAttribute("menu", newMenuData);
        return "change-menu";
    }

    /*@RequestMapping(value = "menu/delete/{idMenu}", method = RequestMethod.GET)
    public String deleteMenu(@PathVariable Long idMenu, Model model){
        MenuModel menu = menuService.findMenuById(idMenu).get();
        model.addAttribute("nama", menu.getNama());
        menuService.deleteMenu(menu);
        return "delete-menu";
    }*/

    @RequestMapping(value = "/menu/delete", method = RequestMethod.POST)
    private String delete(@ModelAttribute RestoranModel restoran, Model model){
        for (MenuModel menu : restoran.getListMenu()){
            menuService.deleteMenu(menu);
        }
        return "delete-menu";
    }


}
