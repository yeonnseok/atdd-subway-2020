package wooteco.subway.maps.map.ui;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.domain.PathType;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.members.member.domain.LoginMember;

public class MapControllerTest {
    @Test
    void findPath() {
        MapService mapService = mock(MapService.class);
        MapController controller = new MapController(mapService);
        when(mapService.findPath(anyLong(), anyLong(), any(), any())).thenReturn(new PathResponse());

        LoginMember member = new LoginMember(1L, "test@test.com", "tester", 16);
        ResponseEntity<PathResponse> entity = controller.findPath(1L, 2L, PathType.DISTANCE, member);

        assertThat(entity.getBody()).isNotNull();
    }

    @Test
    void findMap() {
        MapService mapService = mock(MapService.class);
        MapController controller = new MapController(mapService);

        ResponseEntity entity = controller.findMap();

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(mapService).findMap();
    }
}
