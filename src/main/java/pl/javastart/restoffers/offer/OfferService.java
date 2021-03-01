package pl.javastart.restoffers.offer;

import org.springframework.stereotype.Service;
import pl.javastart.restoffers.category.Category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<OfferDto> findByTitle(String title) {
        List<Offer> offersToShow;

        if (title == null) {
            offersToShow = offerRepository.findAll();
        } else {
            offersToShow = offerRepository.findByTitleContainsIgnoreCase(title);
        }

        return offersToShow.stream()
                .map(this::toOfferDto)
                .collect(Collectors.toList());
    }

    private OfferDto toOfferDto(Offer offer) {
        OfferDto dto = new OfferDto();
        dto.setId(offer.getId());
        dto.setTitle(offer.getTitle());
        dto.setDescription(offer.getDescription());
        dto.setPrice(offer.getPrice());
        dto.setImgUrl(offer.getImgUrl());
        dto.setCategory(offer.getCategory().getDisplayName());
        return dto;
    }

    public long countAll() {
        return offerRepository.count();
    }


    public OfferDto insert(OfferDto offerDto) {
        Offer offer = new Offer();
        offer.setTitle(offerDto.getTitle());
        offer.setDescription(offerDto.getDescription());
        offer.setPrice(offerDto.getPrice());
        offer.setImgUrl(offerDto.getImgUrl());
        offer.setCategory(Category.findByDisplayName(offerDto.getCategory()));

        offerRepository.save(offer);

        return toOfferDto(offer);
    }

    public Optional<OfferDto> findById(Long id) {
        return offerRepository.findById(id)
                .map(this::toOfferDto);
    }
}
