package wooteco.subway.maps.map.documentation;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import wooteco.security.core.TokenResponse;
import wooteco.subway.common.documentation.Documentation;
import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.maps.map.ui.MapController;
import wooteco.subway.maps.station.domain.Station;
import wooteco.subway.maps.station.dto.StationResponse;

@WebMvcTest(controllers = {MapController.class})
public class PathDocumentation extends Documentation {
    @Autowired
    private MapController MapController;
    @MockBean
    private MapService mapService;

    protected TokenResponse tokenResponse;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
        tokenResponse = new TokenResponse("token");
    }

    @Test
    void findPath() {
        StationResponse 교대역 = StationResponse.of(new Station(1L, "교대역"));
        StationResponse 강남역 = StationResponse.of(new Station(2L, "강남역"));
        StationResponse 양재역 = StationResponse.of(new Station(3L, "양재역"));
        List<StationResponse> stations = Lists.newArrayList(교대역, 강남역, 양재역);
        PathResponse pathResponse = new PathResponse(stations, 40, 13, 1350);
        when(mapService.findPath(anyLong(), anyLong(), any())).thenReturn(pathResponse);

        given().
            log().all().
            header("Authorization", "Bearer " + tokenResponse.getAccessToken()).
            when().
            get("/paths?source={sourceId}&target={targetId}&type={type}", 교대역.getId(), 양재역.getId(), "DISTANCE").
            then().
            log().all().
            apply(document("paths/find",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer auth credentials")),
                requestParameters(
                    parameterWithName("source").description("출발역 아이디"),
                    parameterWithName("target").description("도착역 아이디"),
                    parameterWithName("type").description("도착역 아이디")),
                responseFields(
                    fieldWithPath("stations.[]").type(JsonFieldType.ARRAY).description("경로"),
                    fieldWithPath("stations.[].id").type(JsonFieldType.NUMBER).description("역 아이디"),
                    fieldWithPath("stations.[].name").type(JsonFieldType.STRING).description("역 이름"),
                    fieldWithPath("distance").type(JsonFieldType.NUMBER).description("총 거리"),
                    fieldWithPath("duration").type(JsonFieldType.NUMBER).description("총 시간"),
                    fieldWithPath("fare").type(JsonFieldType.NUMBER).description("총 요금")))).
            extract();
    }
}
