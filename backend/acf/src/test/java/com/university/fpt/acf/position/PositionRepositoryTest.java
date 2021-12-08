package com.university.fpt.acf.position;


import com.university.fpt.acf.entity.Position;
import com.university.fpt.acf.repository.PositionRespository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PositionRepositoryTest {
    @Autowired
    private PositionRespository underTest;

    @Test
    void itShouldCheckIfPositionCheckExitPosition() {
        //given
        String name = "Nhân Viên";
        //when
       String expected = underTest.checkExitPosition(name);
        //then
        assertThat(expected).isEqualTo(name);
    }

    @Test
    void checkExitPositionById() {
        // given
        Long id = 3l;
        String result ="Nhân Viên";
        // when
        String expected = underTest.CheckExitPositionById(id);
        // then
        assertThat(expected).isEqualTo(result);
    }

    @Test
    void getPositionById() {
        // given
        Long id = 3l;
        // when
        Position expected = underTest.getPositionById(id);
        // then
        assertThat(expected!=null).isTrue();
    }

    @Test
    void checkDeletePositionById() {
        // given
        Long id = 110l;
        // when
        String expected = underTest.checkDeletePositionById(id);
        // then
        assertThat(!expected.isEmpty()).isTrue();
    }
}