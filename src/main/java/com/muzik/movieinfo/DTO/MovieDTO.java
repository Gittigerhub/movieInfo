package com.muzik.movieinfo.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {

    private Integer code;       // 일련번호
    private String name;        // 영화제목
    private String director;    // 감독
    private String nation;      // 국가
    private String genre;       // 장르
    private String actor;       // 주연배우
    private String opendate;    // 개봉일
    private String synopsis;    // 영화개요
    private String poster;      // 영화포스터

}