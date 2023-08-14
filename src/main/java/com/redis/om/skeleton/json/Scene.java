package com.redis.om.skeleton.json;

import org.springframework.data.annotation.Id;

import com.opencsv.bean.CsvBindByName;
import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;

import lombok.Data;
import lombok.NonNull;

//@RequiredArgsConstructor(staticName = "of")
// @AllArgsConstructor(access = AccessLevel.PUBLIC)
// @Builder
@Data // lombok getters and setter and allArgs ctor
@Document // persist models as JSON documents using RedisJSON
public class Scene {

    // Id Field, also indexed
    @Id
    @Indexed
    private String id;

    public enum ScenarioType {
        BACK_TEST, FORWARD_TEST, CONTINUOUS_LIVE, INTERVAL_LIVE
    }

    @NonNull
    public ScenarioType type = ScenarioType.BACK_TEST;

    @NonNull
    @Indexed
    @CsvBindByName(column = "Short Name")
    private String shortName = "Short Name"; // defines the Scenario instance together with symbol, timeframe, runName

    @NonNull
    @Indexed
    @CsvBindByName(column = "Symbol")
    private String symbol = "EURUSD"; // Symbol,FX_IDC:GBPEUR

    @NonNull
    @Indexed
    @CsvBindByName(column = "TimeFrame")
    private String timeFrame = "5m";

    @NonNull
    @Indexed
    @CsvBindByName(column = "Run Name")
    private String runName = "BACK 01"; // can have several attempts at this particular
    // strategy implementation. Or just
    // start and stop a run
}
