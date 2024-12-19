package com.muzik.movieinfo.Repository;

import com.muzik.movieinfo.Entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {



}
/*
    [기본 메소드]
    save() : 삽입, 수정
        사옹 방법 // Entity 변수명 = repository.save(entity 값)

    findById() : 기본키로 조회
        사용 방법 // Optional<Entity> 변수명 = repository.findById(기본키 값)

    deleteById() : 기본키로 삭제
        사용 방법 // repository.deleteById(기본키 값)

    deleteAll() : 모두 삭제
        사용 방법 // repository. deleteAll()

    [사용자가 쿼리메소드] - 수정 또는 조회
    List<Entity> findBy변수명(인수 변수명) : 변수명과 인수변수는 1:1 매치
        사용 방법 // List<Entity> findBySubject(String subject)

    모두 만족하는 값을 조회
    List<Entity> findBy변수명1And변수명2..(인수 변수명1, 인수 변수명2)
        사용 방법 // List<Entity> findBySubjectAndAge(String subject, Integer age)

    조건중 하나라도 만족하면 조회
    List<Entity> findBy변수명1Or변수명2..(인수 변수명1, 인수 변수명2)
        사용 방법 // List<Entity> findBySubjectOrAge(String subject, Integer age)

    조회한 내용을 정렬
    List<Entity> findBy검색할변수명OrderBy정렬할변수명DESC(인수 변수명)        (DESC/ASC 정렬방식)
        사용 방법 // List<Entity> findByAgeOrderBySubjectDESC(int age)  정렬하는 변수는 안받음

    페이지 단위로 조회
    Page<Entity> findBy~
*/