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

    private Stance stance = Stance.NEUTRAL; // for initialization

    @NonNull
    public State state = State.BLANK;

    @CsvBindByName(column = "Trade #")
    private int no = 1; // Trade #, starts at 1

    @CsvBindByName(column = "Date/Time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @CsvDate(value = "yyyy-MM-dd'T'HH:mm:ssX", writeFormatEqualsReadFormat = true)
    private Instant entryAlertDateTime;

    private BigDecimal entryExecutionPrice; //

    @CsvBindByName(column = "Price")
    private BigDecimal entryAlertPrice; //

    @CsvBindByName(column = "Type")
    private String signalType; //

    @CsvBindByName(column = "Signal")
    private String entryNarrative; // msg added with the Signal from Source (i.e TradingView)

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @CsvDate(value = "yyyy-MM-dd'T'HH:mm:ssX", writeFormatEqualsReadFormat = true)
    private Instant exitAlertDateTime;

    private BigDecimal exitExecutionPrice;

    private BigDecimal exitAlertPrice;

    private String exitNarrative; // msg added with the Signal from Source (i.e TradingView)

    @CsvBindByName(column = "Profit")
    private BigDecimal profit; //

    @CsvBindByName(column = "Profit %")
    private BigDecimal profitPercentage; // calculated, can be Null

    @CsvBindByName(column = "Cum. Profit")
    private BigDecimal cumulativeProfit; // Cum. Profit in 'strategy.currency'

    @CsvBindByName(column = "Cum. Profit %")
    private BigDecimal cumulativeProfitPercentage; // calculated, can be Null

    @CsvBindByName(column = "Contracts")
    private int contracts; // no of contracts traded with 'strategy.initialCapital' and
                           // 'strategy.orderSize'

    @CsvBindByName(column = "Run-up")
    private BigDecimal runUp; // max price increase while trade open

    @CsvBindByName(column = "Run-up %")
    private BigDecimal runUpPercentage; // calculated, can be Null

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
}
