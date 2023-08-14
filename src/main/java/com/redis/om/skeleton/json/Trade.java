package com.redis.om.skeleton.json;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;

import lombok.Data;
import lombok.NonNull;

// Contract and Execution details

//@RequiredArgsConstructor(staticName = "of")
// @AllArgsConstructor(access = AccessLevel.PUBLIC)
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data // lombok getters and setter and allArgs ctor
@Document // persist models as JSON documents using RedisJSON
public class Trade {

    @Id
    @Indexed
    private String id; // replaces the conventional UUID primary key strategy generation with a ULID

    public enum Stance {
        LONG, SHORT, NEUTRAL
    }

    public enum State { // State of the Trade, partial to be implemented latter
        OPEN, CLOSED, PARTIAL, CANCELLED, BLANK
    }

    // @NonNull
    // @Indexed
    // private String scenarioId; // to save resources and also a circular
    // referenceas Scenario holds a list of
    // Trades

    private Stance stance = Stance.NEUTRAL; // for initialization

    @NonNull
    public State state = State.BLANK;

    @CsvBindByName(column = "Trade #")
    private int no = 1; // Trade #, starts at 1

    // Option: Use a Signal Surrogate Object which can be made richer with more data
    // to do with

    @CsvBindByName(column = "Date/Time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @CsvDate(value = "yyyy-MM-dd'T'HH:mm:ssX", writeFormatEqualsReadFormat = true)
    private Instant entryDateTime;

    private BigDecimal entryExecutionPrice; //

    // @NonNull
    @CsvBindByName(column = "Price")
    private BigDecimal entrySignalPrice; //

    @CsvBindByName(column = "Type")
    private String signalType; //

    @CsvBindByName(column = "Signal")
    private String entryNarrative; // msg added with the Signal from Source (i.e TradingView)

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @CsvDate(value = "yyyy-MM-dd'T'HH:mm:ssX", writeFormatEqualsReadFormat = true)
    private Instant exitDateTime;

    private BigDecimal exitExecutionPrice;

    private BigDecimal exitSignalPrice;

    private String exitNarrative; // msg added with the Signal from Source (i.e TradingView)

    @CsvBindByName(column = "Profit")
    private BigDecimal profit; //

    @CsvBindByName(column = "Profit %")
    private BigDecimal profitPercentage; // calculated, can be Null

    @CsvBindByName(column = "Cum. Profit")
    private BigDecimal cumulativeProfit; // Cum. Profit in 'strategy.currency'

    @CsvBindByName(column = "Cum. Profit %")
    private BigDecimal cumulativeProfitPercentage; // calculated, can be Null

    // @NonNull
    @CsvBindByName(column = "Contracts")
    private int contracts; // no of contracts traded with 'strategy.initialCapital' and
                           // 'strategy.orderSize'

    // @NonNull
    @CsvBindByName(column = "Run-up")
    private BigDecimal runUp; // max price increase while trade open

    @CsvBindByName(column = "Run-up %")
    private BigDecimal runUpPercentage; // calculated, can be Null

    // @NonNull
    @CsvBindByName(column = "Drawdown")
    private BigDecimal drawdown; // max price decline while trade open

    @CsvBindByName(column = "Drawdown %")
    private BigDecimal drawdownPercentage; // calculated, can be Null

    private BigDecimal slippage; // calculated

    private BigDecimal commision; // calculated

    // audit fields

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;
    // Pojo Services
    // private final Double getProfit() ; // Profit,
    // private final Double getProfitPercentage() ; // ... %,

    // private final Double getCumulativeProfit() ; // Sum Total up to and including
    // this trade Profit
    // private final Double getCumulativeProfitPercentage() ; // ... %

    // private Double getDrawdownPercentage() ; // calculated
    // private void updateDrawdown() ; // gets recalculated on each tick

    // private Double getRunupPercentage() ; // calculated
    // private void updateRunup() ; // gets recalculated on each tick
}
