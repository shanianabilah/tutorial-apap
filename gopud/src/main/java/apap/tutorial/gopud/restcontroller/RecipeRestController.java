package apap.tutorial.gopud.restcontroller;

import apap.tutorial.gopud.rest.ResultsDetail;
import apap.tutorial.gopud.service.RecipeRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class RecipeRestController {

    @Autowired
    private RecipeRestService recipeRestService;

    @GetMapping(value = "/recipe")
    private Mono<ResultsDetail> getRecipe(@RequestParam(value = "excludeIngredient") String excludeIngredient) {
        return recipeRestService.getRecipe(excludeIngredient);
    }
}
