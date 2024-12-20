package com.muzik.movieinfo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {

    @Schema(description = "영화정보 일련번호", example = "1")
    private Integer code;       // 일련번호
    @Schema(description = "영화제목", example = "범죄도시")
    private String name;        // 영화제목
    @Schema(description = "영화감독", example = "이상용")
    private String director;    // 감독
    @Schema(description = "제작국가", example = "한국")
    private String nation;      // 국가
    @Schema(description = "장르", example = "액션")
    private String genre;       // 장르
    @Schema(description = "주연", example = "마동석")
    private String actor;       // 주연배우
    @Schema(description = "개봉일", example = "2024-05-01")
    private String opendate;    // 개봉일
    @Schema(description = "영화소개", example = "재밌다.")
    private String synopsis;    // 영화개요
    @Schema(description = "영화포스터파일", example = "sample.jpg")
    private String poster;      // 영화포스터

}