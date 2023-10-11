package pl.javastart.restoffers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestOffersApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @DisplayName("GET: /api/offers")
    void shouldGetOffers() {
        final String url = "/api/offers";
        final List<String> expectedObjectProperties = Arrays.asList("id",
                "id",
                "title",
                "description",
                "imgUrl",
                "price",
                "category");
        List<Map<String, Object>> list = restTemplate.getForObject(url, List.class);
        assertFalse(list.isEmpty());
        Map<String, Object> firstObjectProperties = list.get(0);
        assertTrue(firstObjectProperties.keySet().containsAll(expectedObjectProperties));
    }

    @Test
    @DisplayName("GET: /api/offers/count")
    void shouldGetOffersCount() {
        final String url = "/api/offers/count";
        Integer count = restTemplate.getForObject(url, Integer.class);
        assertTrue(count > 0);
    }

    @Test
    @DisplayName("GET: /api/offers?title=tele")
    void shouldGetSearchResultAsList() {
        final String url = "/api/offers?title=tele";
        final List<String> expectedObjectProperties = Arrays.asList("id",
                "id",
                "title",
                "description",
                "imgUrl",
                "price",
                "category");
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        //w przypadku braku danych testowych zawierających "tele..." poniższe mogą się wysypać
        List<Map<String, Object>> body = response.getBody();
        assertFalse(body.isEmpty());
        Map<String, Object> properties = (Map<String, Object>) body.get(0);
        assertTrue(properties.keySet().containsAll(expectedObjectProperties));
    }

    @Test
    @DisplayName("GET: /api/categories/names")
    void shouldGetCategoryNames() {
        final String url = "/api/categories/names";
        String[] categoryNames = restTemplate.getForObject(url, String[].class);
        assertTrue(categoryNames.length > 0);
    }

    @Test
    @DisplayName("POST: /api/offers")
    void shouldCreateOfferInFirstCategory() {
        final String categoryNamesUrl = "/api/categories/names";
        String[] categoryNames = restTemplate.getForObject(categoryNamesUrl, String[].class);
        final String url = "/api/offers";
        String body = """
                {
                  "title": "Telewizor",
                  "description": "Super telewizor o przekątnej 55 cali",
                  "imgUrl": "http://blabla2.jpg",
                  "price": "1299",
                  "category": "%s"
                }
                """.formatted(categoryNames[0]);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, httpEntity, Map.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Map bodyMap = response.getBody();
        final List<String> expectedObjectProperties = Arrays.asList("id",
                "id",
                "title",
                "description",
                "imgUrl",
                "price",
                "category");
        assertTrue(bodyMap.keySet().containsAll(expectedObjectProperties));
    }

    @Test
    @DisplayName("GET: /api/categories")
    void shouldGetCategories() {
        final String url = "/api/categories";
        final List<String> expectedObjectProperties = Arrays.asList(
                "name",
                "description",
                "offers"
        );
        List<Map<String, Object>> list = restTemplate.getForObject(url, List.class);
        assertFalse(list.isEmpty());
        Map<String, Object> firstObjectProperties = list.get(0);
        assertTrue(firstObjectProperties.keySet().containsAll(expectedObjectProperties));
    }

    @Test
    @DisplayName("GET: /api/offers/1")
    void shouldGetFirstOffer() {
        final String url = "/api/offers/1";
        final List<String> expectedObjectProperties = Arrays.asList("id",
                "id",
                "title",
                "description",
                "imgUrl",
                "price",
                "category");
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Map<String, Object> firstObjectProperties = responseEntity.getBody();
        assertTrue(firstObjectProperties.keySet().containsAll(expectedObjectProperties));
    }

    @Test
    @DisplayName("GET: /api/offers/9582")
    void should404ForNonExistingOffer() {
        final String url = "/api/offers/9582";
        final List<String> expectedObjectProperties = Arrays.asList("id",
                "id",
                "title",
                "description",
                "imgUrl",
                "price",
                "category");
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCode().value());
    }

    @Test
    @DisplayName("POST: /api/categories")
    void shouldCreateCategory() {
        final String url = "/api/categories";
        String body = """
                {
                    "name": "nowa kategoria",
                    "description": "opis nowej kategorii"
                }
                """;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, httpEntity, Map.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Map<String, Object> responseBody = response.getBody();
        assertNotNull(responseBody.get("id"));
    }

    @Test
    @DisplayName("DELETE /api/offers/1")
    void shouldDeleteOffer() {
        final String url = "/api/offers/1";
        restTemplate.delete(url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
    }
}
