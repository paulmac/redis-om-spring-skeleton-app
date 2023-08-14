package com.redis.om.skeleton.json;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

//@ApiModel(description = "Representation of a Strategy. Can be sourced from YouTube. Need to save offlien the video and all the source in case it is deleted")
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Data // lombok getters and setter and allArgs ctor
@Document // persist models as JSON documents using RedisJSON
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Strategy {
    @Id
    @Indexed
    private String id; // replaces the conventional UUID primary key strategy generation with a ULID
                       // (Universally Unique Lexicographically Sortable Identifier) which is faster to
                       // generate and easier on the eyes
    @NonNull // lombok - not enforced by the Java language
    @Indexed
    private String longName; // deemed Unique
    // Indexed for exact text matching

    @NonNull
    @Indexed
    private String shortName; // may not be Unique

    @NonNull
    private String source; // e.g youtube

    @NonNull
    private String description; // e.g youtube

    @NonNull
    @Searchable
    private String youtubeId; // unique youtube identifier

    @NonNull
    private List<String> indicators = new ArrayList<>(); // parameters in Scenario

    @NonNull
    public LinkedList<String> backs = new LinkedList<>();

    @NonNull
    public LinkedList<String> forwards = new LinkedList<>();

    @NonNull
    public LinkedList<String> lives = new LinkedList<>();

    // audit fields

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;
}
