package apap.tutorial.gopud.model;

import apap.tutorial.gopud.service.RestoranService;
import apap.tutorial.gopud.model.RestoranModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestoranInMemoryService implements RestoranService {
    private List<RestoranModel> listRestoran;

    //constructor
    public RestoranInMemoryService(){
        listRestoran = new ArrayList<>();
    }

    @Override
    public void addRestoran(RestoranModel restoran){
        listRestoran.add(restoran);
    }

    @Override
    public List<RestoranModel> getRestoranList(){
        return listRestoran;
    }

    public RestoranModel getRestoranByIdRestoran(String id){
        for (RestoranModel i : listRestoran){
            if (i.getIdRestoran().equals(id)){
                return i;
            }
        }
        return null;
    }

    @Override
    public void deleteResto(RestoranModel resto){
        listRestoran.remove(resto);
    }
}