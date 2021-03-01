package pl.javastart.restoffers.offer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/offers")
@RestController
public class OfferRestController {

    private final OfferService offerService;

    public OfferRestController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("")
    public List<OfferDto> getAllOffers(@RequestParam(required = false) String title) {
        return offerService.findByTitle(title);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDto> getOfferById(@PathVariable Long id) {
        return ResponseEntity.of(offerService.findById(id));
    }

    @GetMapping("/count")
    public long getOffersCount() {
        return offerService.countAll();
    }

    @PostMapping("")
    public ResponseEntity<?> addOffer(@RequestBody OfferDto offerDto) {
        try {
            return ResponseEntity.ok(offerService.insert(offerDto));
        } catch (NoCategoryException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        offerService.deleteOffer(id);
    }

}
