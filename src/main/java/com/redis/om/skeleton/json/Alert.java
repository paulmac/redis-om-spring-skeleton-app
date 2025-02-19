package com.redis.om.skeleton.json;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import com.ib.client.OrderType;
import com.ib.client.Types;
import com.redis.om.spring.annotations.Indexed;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/* logs information regarding the trading Signal itself.
 * Chosen as a seperate Entity because it can serve as a placeholder for more data latter on
 */
//@RequiredArgsConstructor(staticName = "of")
@Data
@Builder
public class Alert {

    @Id
    @Indexed
    private String id; // replaces the conventional UUID primary key strategy generation with a ULID
    @NonNull
    private String strategy; // from TV e.g "BHSL"
    @NonNull
    private Scenario.TimeFrame timeFrame; // e.g 4 hours
    @NonNull
    private Scenario.Direction direction; // it can be repeated or have several attempts with same params
    @NonNull
    private String symbol; // from TV ticker
    @NonNull
    private Types.SecType secType; // IBKR "CFD"
    @NonNull
    private Types.Action action; // from TV strategy.order.action
    @NonNull
    private OrderType orderType; // from TV "MIDPRICE" or "LMT"
    @NonNull
    private String tif; // from TV "GTC"

    private int quantity; // from TV strategy.position_size
    @NonNull
    // @JsonDeserialize(using = InstantTypeAdapter.Deserializer.class)
    // @JsonSerialize(using = InstantTypeAdapter.Serializer.class)
    private Instant timeStamp; // from TV timestamp
    @NonNull
    private BigDecimal price; // from TV strategy.order.price
    @NonNull
    private BigDecimal close; // from TV strategy.order.close
    @NonNull
    private BigDecimal open; // from TV strategy.order.open
    @NonNull
    private BigDecimal high; // from TV strategy.order.high
    @NonNull
    private BigDecimal low; // from TV strategy.order.low
    @NonNull
    private String time; // from TV strategy.order.time
    @NonNull
    private String description; // msg added with the Signal from Source (i.e TradingView)
    @NonNull
    private String comment; // strategy.order.comment from Source (i.e TradingView)

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;
}
