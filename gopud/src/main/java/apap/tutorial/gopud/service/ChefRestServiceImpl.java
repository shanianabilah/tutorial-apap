package apap.tutorial.gopud.service;

import apap.tutorial.gopud.rest.ChefDetail;
import apap.tutorial.gopud.rest.Setting;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

@Service
@Transactional
public class ChefRestServiceImpl implements ChefRestService {

    private final WebClient webClient;

    public ChefRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.chefUrl).build();
    }

    @Override
    public Mono<ChefDetail> getChef(String nama) {
        return this.webClient.get().uri(uriBuilder -> uriBuilder
                .queryParam("nama", nama)
                .build()).retrieve()
                .bodyToMono(ChefDetail.class);
    }
}
