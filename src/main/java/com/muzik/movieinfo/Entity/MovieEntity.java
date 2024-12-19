package com.muzik.movieinfo.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "movie")
public class MovieEntity {

    @Id     // 생략불가
    @GeneratedValue(strategy = GenerationType.SEQUENCE)    // 생성전략
    private Integer code;       // 일련번호

    private String name;        // 영화제목
    private String director;    // 감독
    private String nation;      // 국가
    private String genre;       // 장르
    private String actor;       // 주연배우
    private String opendate;    // 개봉일
    private String synopsis;    // 영화개요
    private String poster;      // 영화포스터

    // @Column 생략시 변수명과 동일한 이름으로 자동적용, 길이 생략시 기본값으로 자동지정

}
/*
    작업 후
    1. 프로그램(서버) 실행
    2. 브라우저에서 localhost:/8080/h2-condole 접속
    3. url은 application.properties에 datasource.url 주소를 입력
        spring.datasource.url = (jdbc:h2:~/db/moviedb)  ;MODE=MySQL
    4. Entity의 테이블이름과 동일한 테이블이 존재하는지 확인
        @Table(name="   (movie)    ")
*/