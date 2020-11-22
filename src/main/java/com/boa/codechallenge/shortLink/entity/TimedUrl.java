package com.boa.codechallenge.shortLink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class TimedUrl {
    public String url;
    public LocalTime time;
}
