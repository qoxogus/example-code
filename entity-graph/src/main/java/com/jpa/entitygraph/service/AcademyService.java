package com.jpa.entitygraph.service;

import com.jpa.entitygraph.entity.Academy;
import com.jpa.entitygraph.repository.AcademyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcademyService {

    private final AcademyRepository academyRepository;

    @Transactional(readOnly = true)
    public List<String> findAllSubjectNames(){
        return extractSubjectNames(academyRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<String> findAllSubjectNamesByJoinFetch(){
        return extractSubjectNames(academyRepository.findAllJoinFetch());
    }

    @Transactional(readOnly = true)
    public List<String> findAllSubjectNamesByEntityGraph() {
        return extractSubjectNames(academyRepository.findAllEntityGraph());
    }

    @Transactional(readOnly = true)
    public List<String> findAllSubjectNamesByJoinFetchDistinct(){
        return extractSubjectNames(academyRepository.findAllJoinFetchDistinct());
    }

    @Transactional(readOnly = true)
    public List<String> findAllSubjectNamesByEntityGraphDistinct() {
        return extractSubjectNames(academyRepository.findAllEntityGraphDistinct());
    }

    /**
     * Lazy Load를 수행하기 위해 메소드를 별도로 생성
     */
    private List<String> extractSubjectNames(List<Academy> academies){
        log.info(">>>>>>>>[모든 과목을 추출한다]<<<<<<<<<");
        log.info("Academy Size : {}", academies.size());

        return academies.stream()
                .map(a -> a.getSubjects().get(0).getName())
                .collect(Collectors.toList());
    }
}
