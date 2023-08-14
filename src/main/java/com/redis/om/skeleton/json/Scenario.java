package com.redis.om.skeleton.json;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import com.opencsv.bean.CsvBindByName;
import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;

import lombok.Data;
import lombok.NonNull;

//@RequiredArgsConstructor(staticName = "of")
//@AllArgsConstructor(access = AccessLevel.PUBLIC)
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
// lombok getters and setter and allArgs ctor
// @AllArgsConstructor(access = AccessLevel.PUBLIC)
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
//@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PROTECTED)
// @AllArgsConstructor(access = AccessLevel.PUBLIC)
// @Builder
@Data // lombok getters and setter and allArgs ctor
@Document // persist models as JSON documents using RedisJSON
public class Scenario {
    // Id Field, also indexed
    @Id
    @Indexed
    private String id;

    public enum TimeFrame {
        M5("5m"),
        M15("15m"),
        H1("1h"),
        H4("4h"),
        D1("1d");

        public final String tf;

        private TimeFrame(String tf) {
            this.tf = tf;
        }
    }

    public enum Direction {
        BACK("B"),
        FORWARD("F"),
        // CONTINUOUS, INTERVAL
        ALL("A");

        public final String d;

        private Direction(String d) {
            this.d = d;
        }
    }
    // public Scenario(String shortName, String symbol, String timeFrame, String
    // runName) {
    // this.shortName = shortName;
    // this.symbol = symbol;
    // this.timeFrame = timeFrame;
    // this.runName = runName;
    // }

    // @NonNull
    // @Indexed
    // // @CsvBindByName(column = "Short Name")
    // private String longName = "Long Name"; // defines the Scenario instance
    // together with symbol, timeframe, runName

    @NonNull
    @Indexed
    @CsvBindByName(column = "Long Name")
    private String longName = "Strategy Long Name"; // defines the Scenario instance together with direction, symbol,
                                                    // timeframe

    @NonNull
    @Indexed
    @CsvBindByName(column = "Symbol")
    private String symbol = "EURUSD"; // Symbol,FX_IDC:GBPEUR

    @NonNull
    @Indexed
    @CsvBindByName(column = "TimeFrame")
    private TimeFrame timeFrame = TimeFrame.M5;

    @NonNull
    @Indexed
    public Direction direction = Direction.BACK;

    @NonNull
    @CsvBindByName(column = "Narrative")
    private String narrative = "My First Backtest"; // can have several attempts at this particular
    // strategy implementation. Or just
    // start and stop a run

    @NonNull
    public Trade current = new Trade(); // check trade.status to determine whether open or not

    public LinkedList<String> trades = new LinkedList<>(); // List of IDs sufficient for now

    private String secType; // e.g "FX"

    private Instant start = Instant.now(); // Trading range,2010-01-05 13:00 — 2023-05-17 17:00

    private Instant end = Instant.now(); // Trading range,2010-01-05 13:00 — 2023-05-17 17:00

    private String chartType = "Candles"; // Chart type,Candles

    private int pointValue; // 1

    private String currency; // Currency; USD, EUR

    private BigDecimal tickSize; // Tick Size,0.0001

    private String precision = "Default"; // Precision,Default

    @CsvBindByName(column = "Net Profit")
    private BigDecimal netProfit;

    // @NonNull
    private BigDecimal netProfitPercentage; // calculated, can be Null

    @CsvBindByName(column = "Gross Profit")
    private BigDecimal grossProfit; // Cum. Profit in 'strategy.currency'

    private BigDecimal grossProfitPercentage; // Cum. Profit in

    // @NonNull
    @CsvBindByName(column = "Gross Loss")
    private BigDecimal grossLoss; // Cum. Profit in 'strategy.currency'

    private BigDecimal grossLossPercentage; // Cum. Profit in 'strategy.currency'

    // @NonNull
    @CsvBindByName(column = "Max Run-up")
    private BigDecimal maxRunUp; // updated by Trade

    private BigDecimal maxRunUpPercentage;

    // @NonNull
    @CsvBindByName(column = "Max Drawdown")
    private BigDecimal maxDrawdown; // updated by Trade

    private BigDecimal maxDrawdownPercentage;

    // @NonNull
    @CsvBindByName(column = "Buy & Hold Return")
    private BigDecimal buyAndHoldReturn; // updated by Trade

    private BigDecimal buyAndHoldReturnPercentage;

    // @NonNull
    @CsvBindByName(column = "Sharpe Ratio")
    private BigDecimal sharpeRatio; // calculated in this

    @CsvBindByName(column = "Sortino Ratio")
    private BigDecimal sortinoRatio; // calculated

    // @NonNull
    @CsvBindByName(column = " Profit Factor")
    private BigDecimal profitFactor; // calculated in this

    @CsvBindByName(column = "Max Contracts Held")
    private int maxContractsHeld; // updated in Trade

    // @NonNull
    @CsvBindByName(column = "Open PL")
    private BigDecimal openPnL; // updated in Trade

    private BigDecimal openPnLPercentage; // updated in Trade

    @CsvBindByName(column = "Commission Paid")
    private BigDecimal commissionPaid; // updated in Trade

    @CsvBindByName(column = "Total Closed Trades")
    private int totalClosedTrades; // updated in Trade

    @CsvBindByName(column = "Total Open Trades")
    private int totalOpenTrades; // updated in Trade

    @CsvBindByName(column = "Number Winning Trades")
    private int numberWinningTrades; // updated in Trade

    @CsvBindByName(column = "Number Losing Trades")
    private int numberLosingTrades; // updated in Trade

    @CsvBindByName(column = "Percent Profitable")
    private BigDecimal percentProfitable; // updated in Trade

    @CsvBindByName(column = "Avg Trade")
    private BigDecimal avgTrade; // updated in this

    private BigDecimal avgTradePercentage;

    @CsvBindByName(column = "Avg Winning Trade")
    private BigDecimal avgWinningTrade; // updated in this

    private BigDecimal avgWinningTradePercentage;

    // @NonNull
    @CsvBindByName(column = "Avg Losing Trade")
    private BigDecimal avgLosingTrade; // updated in this

    private BigDecimal avgLosingTradePercentage;

    // @NonNull
    @CsvBindByName(column = "Ratio Avg Win / Avg Loss")
    private BigDecimal ratioAvgWinAvgLoss; // updated in this

    // @NonNull
    @CsvBindByName(column = "Largest Winning Trade")
    private BigDecimal largestWinningTrade; // updated in this

    private BigDecimal largestWinningTradePercentage;

    // @NonNull
    @CsvBindByName(column = "Largest Losing Trade")
    private BigDecimal largestLosingTrade; // updated in this

    private BigDecimal largestLosingTradePercentage;

    @CsvBindByName(column = "Avg # Bars in Trades")
    private int avgNoBarsInTrades; // updated in this

    @CsvBindByName(column = "Avg # Bars in Winning Trades")
    private int avgNoBarsInWinningTrades; // updated in this

    @CsvBindByName(column = "Avg # Bars in Losing Trades")
    private int avgNoBarsInLosingTrades; // updated in this

    // @NonNull
    @CsvBindByName(column = "Margin Calls")
    private BigDecimal marginCalls; // updated in this

    private Map<String, String> properties;

    // audit fields
    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    // Return (Ann.) [%] 25.42
    // Volatility (Ann.) [%] 38.43
    // Calmar Ratio 0.77
    // Profit Factor 2.13
    // Expectancy [%] 6.91
    // SQN 1.78
    // Kelly Criterion 0.6134
}
