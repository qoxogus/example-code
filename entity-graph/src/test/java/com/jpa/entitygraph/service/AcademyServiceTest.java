package com.jpa.entitygraph.service;

import com.jpa.entitygraph.entity.Academy;
import com.jpa.entitygraph.entity.Subject;
import com.jpa.entitygraph.entity.Teacher;
import com.jpa.entitygraph.repository.AcademyRepository;
import com.jpa.entitygraph.repository.TeacherRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

//@Transactional
@Commit
@SpringBootTest
class AcademyServiceTest {

    @Autowired private AcademyRepository academyRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private AcademyService academyService;

    @BeforeEach
    public void setup() {
        List<Academy> academies = new ArrayList<>();

        Teacher teacher = teacherRepository.save(new Teacher("선생님"));

        for(int i=0;i<10;i++){
            Academy academy = Academy.builder()
                    .name("강남스쿨"+i)
                    .build();

            academy.addSubject(Subject.builder().name("자바웹개발" + i).teacher(teacher).build());
            academy.addSubject(Subject.builder().name("파이썬자동화" + i).teacher(teacher).build()); // Subject를 추가
            academies.add(academy);
        }
        academyRepository.saveAll(academies);
        System.out.println("====================save all====================");
    }

    @Test
    public void Academy여러개를_조회시_Subject가_N1_쿼리가발생한다() throws Exception {
        //given
        List<String> subjectNames = academyService.findAllSubjectNames();

        //then
        assertEquals(10, subjectNames.size());
    }

    @Test
    public void Academy여러개를_joinFetch로_가져온다() throws Exception {
        //given
        List<Academy> academies = academyRepository.findAllJoinFetch();
        List<String> subjectNames = academyService.findAllSubjectNamesByJoinFetch();

        //then
        assertEquals(20, academies.size()); // 20개가 조회!?
        assertEquals(20, subjectNames.size()); // 20개가 조회!?

        // JoinFetch는 InnerJoin, Entity Graph는 Outer Join
        // 공통적으로 카테시안 곱(Cartesian Product)이 발생하여 Subject의 수 만큼 Academy가 중복발생하게 됩니다.
        // 그래서 20개가 조회되는 것 입니다.
    }

    @Test
    public void Academy여러개를_EntityGraph로_가져온다() throws Exception {
        //given
        List<Academy> academies = academyRepository.findAllEntityGraph();
        List<String> subjectNames = academyService.findAllSubjectNamesByEntityGraph();

        //then
        assertEquals(20, academies.size()); // 20개가 조회!?
        assertEquals(20, subjectNames.size()); // 20개가 조회!?

        // JoinFetch는 InnerJoin, Entity Graph는 Outer Join
        // 공통적으로 카테시안 곱(Cartesian Product)이 발생하여 Subject의 수 만큼 Academy가 중복발생하게 됩니다.
        // 그래서 20개가 조회되는 것 입니다.
    }

    @Test
    public void Academy여러개를_distinct해서_가져온다 () throws Exception {
        //given
        System.out.println("조회 시작");
        List<Academy> academies = academyRepository.findAllJoinFetchDistinct();

        //then
        System.out.println("조회 끝");
        assertEquals(10, academies.size());
    }

    @Test
    public void Academy_Subject_Teacher를_한번에_가져온다() throws Exception {
        //given
        System.out.println("조회 시작");
        List<Teacher> teachers = academyRepository.findAllWithTeacher().stream()
                .map(a -> a.getSubjects().get(0).getTeacher())
                .collect(Collectors.toList());

        //then
        System.out.println("조회 끝");
        assertEquals(10, teachers.size());
    }

    @Test
    public void Academy_Subject_Teacher를_EntityGraph한번에_가져온다() throws Exception {
        //given
        System.out.println("조회 시작");
        List<Teacher> teachers = academyRepository.findAllEntityGraphWithTeacher().stream()
                .map(a -> a.getSubjects().get(0).getTeacher())
                .collect(Collectors.toList());

        //then
        System.out.println("조회 끝");
        assertEquals(10, teachers.size());
        assertEquals("선생님", teachers.get(0).getName());

    }
}