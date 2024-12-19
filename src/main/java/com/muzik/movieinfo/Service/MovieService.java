package com.muzik.movieinfo.Service;

import com.muzik.movieinfo.DTO.MovieDTO;
import com.muzik.movieinfo.Entity.MovieEntity;
import com.muzik.movieinfo.Repository.MovieRepository;
import com.muzik.movieinfo.Util.FileUpload;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log
public class MovieService {

    @Value("${imgUploadLocation}")      // 이미지가 저장될 위치
    private String imgLocation;

    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;
    private final FileUpload fileUpload;                // 파일업로드 클래스 상속

    /* ----------------------------------------------------------
        함수명 : Page<MovieDTO> list(Pagealbe page)
        인수 : 조회할 페이지 정보
        출력 : 해당 데이터들(list)과 page정보를 전달
        설명 : 요청한 페이지번호에 해당하는 데이터를 조회해서 전달
    ---------------------------------------------------------- */
    public Page<MovieDTO> list(Pageable page) {

        // 1. 페이지정보를 재가공
        int currentPage = page.getPageNumber()-1;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(currentPage, pageSize,
                Sort.by(Sort.Direction.DESC, "code"));

        // 2. 조회
        Page<MovieEntity> movieEntities = movieRepository.findAll(pageable);
        /*
            검색 findByName, findByDirector, findByActor => 이렇게 여러개를 검색해야할 때
                 => if문으로 분류따라 조회해야함 / 검색옵션 중 "제목", "감독", "배우" 으로 검색
            검색 findByNameOrDirectorOrActor => 검색어가 셋중에 하나라도 포함되면 조회 /
                     검색옵션 중 "제목+감독+배우" 으로 검색
        */

        // 3. 변환
        Page<MovieDTO> movieDTOS = movieEntities.map(entity ->
                modelMapper.map(entity, MovieDTO.class));

        return movieDTOS;
    }



    /* ----------------------------------------------------------
        함수명 : MovieDTO read(Integer code)
        인수 : 조회할 영화 코드번호
        출력 : 조회된 데이터
        설명 : 해당 영화 코드번호의 데이터(레코드)를 조회해서 전달
    ---------------------------------------------------------- */
    public MovieDTO read(Integer code) {
        Optional<MovieEntity> movie = movieRepository.findById(code);
        MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);

        return movieDTO;
    }



    /* --------------------------------------------------------------------------
        함수명 : void insert(MovieDTO movieDTO, MultipartFile poster)
                MovieEntity insert(MovieDTO movieDTO, MultipartFile poster)
                 - 컨트롤에서 저장성공을 확인해서 html메세지 출력할 때
        인수 : 저장할 데이터(레코드)
        출력 : 없음, 만약 MovieEntity를 사용한 경우 저장한 레코드 전달
        설명 : 전달받은 데이터를 데이터베이스에 저장
    -------------------------------------------------------------------------- */
    public void insert(MovieDTO movieDTO, MultipartFile poster) {

        // 1. 변환
        MovieEntity movieEntity = modelMapper.map(movieDTO, MovieEntity.class);

        // 2. 이미지 파일 저장
        String newImageName = fileUpload.FileUpload(imgLocation, poster);
        movieEntity.setPoster(newImageName);        // 새로운 파일명으로 변경

        movieRepository.save(movieEntity);

    }



    /* --------------------------------------------------------------------------
        함수명 : void update(MovieDTO movieDTO, MultipartFile poster)
                MovieEntity update(MovieDTO movieDTO, MultipartFile poster)
                 - 컨트롤에서 저장성공을 확인해서 html메세지 출력할 때
        인수 : 수정할 데이터(레코드), 이미지 파일
        출력 : 없음, 만약 MovieEntity를 사용한 경우 수정한 레코드 전달
        설명 : 전달받은 데이터를 데이터베이스에 저장
    -------------------------------------------------------------------------- */
    public void update(MovieDTO movieDTO, MultipartFile poster) {

        // 1. 변환
        MovieEntity movieEntity = modelMapper.map(movieDTO, MovieEntity.class);

        // 2. 이미지 파일 저장
        if (!poster.isEmpty()) {                        // 수정할 이미지 파일이 존재하면
            // 3. 기존에 존재하는 이미지 파일이 있는지 확인 후 삭제
            if (movieEntity.getPoster() != null) {
                fileUpload.FileDelete(imgLocation, movieEntity.getPoster());
            }
            // 4. 새로운 이미지 파일을 저장
            String newImageName = fileUpload.FileUpload(imgLocation, poster);
            movieEntity.setPoster(newImageName);        // 새로운 파일명으로 변경
        }
        movieRepository.save(movieEntity);
    }

    // 응용 (수정 성공여부)
    public MovieEntity updateCheck(MovieDTO movieDTO, MultipartFile poster) {

        // 1. 변환
        MovieEntity movieEntity = modelMapper.map(movieDTO, MovieEntity.class);

        // 2. 이미지 파일 저장
        if (!poster.isEmpty()) {                        // 수정할 이미지 파일이 존재하면
            // 3. 기존에 존재하는 이미지 파일이 있는지 확인 후 삭제
            if (movieEntity.getPoster() != null) {
                fileUpload.FileDelete(imgLocation, movieEntity.getPoster());
            }
            // 4. 새로운 이미지 파일을 저장
            String newImageName = fileUpload.FileUpload(imgLocation, poster);
            movieEntity.setPoster(newImageName);        // 새로운 파일명으로 변경
        }
        return movieRepository.save(movieEntity);
    }



    /* ----------------------------------------------------------
        함수명 : void delete(Integer code)
        인수 : 삭제할 영화 코드번호
        출력 : 없음
        설명 : 해당 영화 코드번호의 레코드를 데이터베이스에서 삭제
    ---------------------------------------------------------- */
    public void delete(Integer code) {
        movieRepository.deleteById(code);
    }

    // 응용
    public boolean deleteCheck(Integer code) {
        movieRepository.deleteById(code);

        // 삭제된 레코드를 조회
        Optional<MovieEntity> read = movieRepository.findById(code);

        if (read.isPresent()) {         // 존재하면
            return false;
        } else {
            return true;                // 삭제 성공
        }
    }



}
/*
    서비스의 주요기능
    1. 검증 및 예외처리 : 데이터베이스 처리전 올바른값인지 판단, 데이터베이스 처리실패에 대한 처리
    2. 트랜잭션 관리 : 데이터베이스 작업 모아서 한번에 처리(데이터베이스 과부하를 방지)
    3. 비니지스 로직 수행 : 수행할 작업을 작성
    4. 보안 관련 기능 : 로그인 처리
    5. 이메일 발송, 알림 전송
    6. 외부 서비스와 통합(Util의 내용을 서비스에서 작성)
    7. 일정한 주기로 수행되는 작업(반복작업, 스케줄링)
*/