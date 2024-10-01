package com.geo.geolocation.service;

import com.geo.geolocation.model.GeoLocationRequest;
import com.geo.geolocation.model.GeoLocationResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GeoLocationServiceImplTest {

    @Autowired
    private GeoLocationServiceImpl service;

    @Test
    void getResultTest() {
        GeoLocationRequest request
                = new GeoLocationRequest(new double[]{45.062609, 41.923656}, new double[]{57.165054, 65.498056});

        GeoLocationResponse response = service.getGeoLocation(request);

        Assertions.assertEquals(response.getStartPos(), request.getStartPos());
        Assertions.assertEquals(response.getEndPos(), request.getEndPos());
        Assertions.assertEquals(response.getStartAddress(),
                "29Д, проспект Кулакова, Северо-Западный, Промышленный район, Ставрополь, городской округ Ставрополь, Ставропольский край, Северо-Кавказский федеральный округ, 355000, Россия");
        Assertions.assertEquals(response.getEndAddress(),
                "84, улица Свободы, Калининский административный округ, Тюмень, городской округ Тюмень, Тюменская область, Уральский федеральный округ, 625001, Россия");
        Assertions.assertEquals(response.getDistance(), 2106981);
    }

    @Test
    void errorCordsTest() {
        GeoLocationRequest request
                = new GeoLocationRequest(new double[]{81.062609, 81.062609}, new double[]{57.165054, 65.498056});

        GeoLocationResponse response = service.getGeoLocation(request);

        Assertions.assertEquals(response.getStartPos(), request.getStartPos());
        Assertions.assertEquals(response.getEndPos(), request.getEndPos());
        Assertions.assertEquals(response.getStartAddress(),
                "Address not found");
        Assertions.assertEquals(response.getEndAddress(),
                "84, улица Свободы, Калининский административный округ, Тюмень, городской округ Тюмень, Тюменская область, Уральский федеральный округ, 625001, Россия");
        Assertions.assertEquals(response.getDistance(), 2705457);

        request.setEndPos(new double[]{81.062609, 81.062609});

        response = service.getGeoLocation(request);

        Assertions.assertEquals(response.getStartPos(), request.getStartPos());
        Assertions.assertEquals(response.getEndPos(), request.getEndPos());
        Assertions.assertEquals(response.getStartAddress(),
                "Address not found");
        Assertions.assertEquals(response.getEndAddress(),
                "Address not found");
        Assertions.assertEquals(response.getDistance(), 0);
    }

}