package apap.tutorial.gopud.service;

import apap.tutorial.gopud.rest.ChefDetail;
import reactor.core.publisher.Mono;

public interface ChefRestService {
    Mono<ChefDetail> getChef(String nama);
}
