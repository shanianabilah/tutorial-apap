package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.MenuModel;
import apap.tutorial.gopud.repository.MenuDb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MenuServiceImplTest {
    @InjectMocks
    MenuService menuService = new MenuServiceImpl();

    @Mock
    MenuDb menuDb;

    @Test
    public void whenAddValidMenuItShouldCallMenuRepositorySave(){
        MenuModel newMenu = new MenuModel();
        newMenu.setNama("ayam");;
        newMenu.setDeskripsi("enak");
        newMenu.setHarga(BigInteger.valueOf(10000));
        newMenu.setDurasiMasak(5);
        menuService.addMenu(newMenu);
        verify(menuDb, times(1)).save(newMenu);
    }

    @Test
    public void whenFindAllMenuByIdRestoranCalledItShouldReturnAllMenuInRestoran(){
        List<MenuModel> allMenuInRestoran = new ArrayList<>();
        for (int loopTimes=3; loopTimes>0; loopTimes--){
            allMenuInRestoran.add(new MenuModel());
        }
        when(menuService.findAllMenuByIdRestoran(1L)).thenReturn(allMenuInRestoran);
        List<MenuModel> dataFromServiceCall = menuService.findAllMenuByIdRestoran(1L);
        assertEquals(3,dataFromServiceCall.size());;
        verify(menuDb, times(1)).findByRestoranIdRestoran(1L);
    }

    @Test
    public void whenFindMenuByIdCalledByExistingDataItShouldReturnCorrectData(){
        MenuModel returnedData = new MenuModel();
        returnedData.setNama("mie");
        returnedData.setDeskripsi("indomi");
        returnedData.setHarga(BigInteger.valueOf(7000));
        returnedData.setDurasiMasak(5);
        returnedData.setId((long)1);
        when(menuService.findMenuById(1L)).thenReturn(Optional.of(returnedData));
        Optional<MenuModel> dataFromServiceCall = menuService.findMenuById(1L);
        verify(menuDb, times(1)).findById(1L);
        assertTrue(dataFromServiceCall.isPresent());
        MenuModel dataFromOptional = dataFromServiceCall.get();
        assertEquals("mie",dataFromOptional.getNama());
        assertEquals("indomi", dataFromOptional.getDeskripsi());
        assertEquals(BigInteger.valueOf(7000), dataFromOptional.getHarga());
        assertEquals(Integer.valueOf(5),dataFromOptional.getDurasiMasak());
        assertEquals(1, dataFromOptional.getId());
    }

    @Test
    public void whenChangeMenuCalledItShouldChangeMenuData(){
        MenuModel updatedData = new MenuModel();
        updatedData.setNama("mie");
        updatedData.setDeskripsi("indomi");
        updatedData.setHarga(BigInteger.valueOf(7000));
        updatedData.setDurasiMasak(5);
        updatedData.setId((long)1);
        when(menuDb.findById(1L)).thenReturn(Optional.of(updatedData));
        when(menuService.changeMenu(updatedData)).thenReturn(updatedData);
        MenuModel dataFromServiceCall = menuService.changeMenu(updatedData);
        assertEquals("mie", dataFromServiceCall.getNama());
        assertEquals("indomi", dataFromServiceCall.getDeskripsi());
        assertEquals(1, dataFromServiceCall.getId());
        assertEquals(BigInteger.valueOf(7000), dataFromServiceCall.getHarga());
        assertEquals(Integer.valueOf(5), dataFromServiceCall.getDurasiMasak());
    }

    @Test
    public void whenChangeMenuCalledButNullItShouldReturnNull() throws Exception{
        MenuModel updatedData = new MenuModel();
        updatedData.setNama("mie");
        updatedData.setDeskripsi("indomi");
        updatedData.setHarga(BigInteger.valueOf(7000));
        updatedData.setDurasiMasak(5);
        updatedData.setId((long)1);
        when(menuDb.findById(1L)).thenReturn(Optional.of(updatedData));
        when(menuService.changeMenu(updatedData)).thenThrow(NullPointerException.class);
        MenuModel dataFromServiceCall = menuService.changeMenu(updatedData);
        assertEquals(null, dataFromServiceCall);
    }

    @Test
    public void whenDeleteValidMenuItShouldCallMenuRepositoryDelete(){
        MenuModel newMenu = new MenuModel();
        newMenu.setNama("ayam");;
        newMenu.setDeskripsi("enak");
        newMenu.setHarga(BigInteger.valueOf(10000));
        newMenu.setDurasiMasak(5);
        menuService.deleteMenu(newMenu);
        verify(menuDb, times(1)).delete(newMenu);
    }

    @Test
    public void whenGetListMenuOrderByHargaAscCalledItShouldReturnAllMenuOrderByHarga(){
        List<MenuModel> allMenuInDatabase = new ArrayList<>();
        for (int loopTimes = 3; loopTimes > 0; loopTimes--) {
            allMenuInDatabase.add(new MenuModel());
        }
        when (menuService.getListMenuOrderByHargaAsc(1L)).thenReturn(allMenuInDatabase);
        List<MenuModel> dataFromServiceCall = menuService.getListMenuOrderByHargaAsc(1L);
        assertEquals(3, dataFromServiceCall.size());
        verify(menuDb, times(1)).findByRestoranIdRestoranOrderByHarga(1L);
    }

}

