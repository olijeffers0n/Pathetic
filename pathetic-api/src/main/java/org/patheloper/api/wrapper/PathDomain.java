package org.patheloper.api.wrapper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;

import java.util.UUID;

@Value
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class PathDomain {

    UUID uuid;
    String name;
    Integer minHeight;
    Integer maxHeight;
}

