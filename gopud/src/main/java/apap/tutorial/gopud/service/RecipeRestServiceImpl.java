package apap.tutorial.gopud.service;

import apap.tutorial.gopud.rest.ResultsDetail;
import apap.tutorial.gopud.rest.Setting;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

@Service
@Transactional
public class RecipeRestServiceImpl implements RecipeRestService{

    private final WebClient webClient;

    public RecipeRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.recipeUrl).build();
    }

    @Override
    public Mono<ResultsDetail> getRecipe(String excludeIngredient) {
        return this.webClient.get().uri(uriBuilder -> uriBuilder
                .queryParam("excludeIngredient", excludeIngredient)
                .queryParam("cuisine", "German")
                .queryParam("apiKey", "47a85196a0964e27bb5d5f7cd1ad8682")
                .build()).retrieve()
                .bodyToMono(ResultsDetail.class);
    }

}
