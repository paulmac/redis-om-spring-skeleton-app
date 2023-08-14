package com.redis.om.skeleton.json;

import com.opencsv.bean.CsvBindByPosition;
import com.redis.om.spring.annotations.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data // lombok getters and setter and allArgs ctor
@Document // persist models as JSON documents using RedisJSON
public class SimplePositionBean {
    @CsvBindByPosition(position = 0)
    private String exampleColOne;

    @CsvBindByPosition(position = 1)
    private String exampleColTwo;

}
