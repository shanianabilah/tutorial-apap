package apap.tutorial.gopud.model;

import apap.tutorial.gopud.service.RestoranService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<RestoranModel> getRestoranByIdRestoran(Long id){
        for (RestoranModel restoran : listRestoran){
            if (restoran.getIdRestoran().equals(id)){
                return Optional.of(restoran);
            }
        }
        return null;
    }

    @Override
    public RestoranModel changeRestoran(RestoranModel restoranModel) {
        return null;
    }

    @Override
    public void deleteResto(RestoranModel resto){
        listRestoran.remove(resto);
    }
}