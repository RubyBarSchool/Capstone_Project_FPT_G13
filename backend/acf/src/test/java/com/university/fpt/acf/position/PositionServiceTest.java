package com.university.fpt.acf.position;


import com.university.fpt.acf.entity.Position;
import com.university.fpt.acf.form.AddPositionForm;
import com.university.fpt.acf.form.PositionForm;

import com.university.fpt.acf.form.UpdatePositionForm;
import com.university.fpt.acf.repository.PositionCustomRepository;
import com.university.fpt.acf.repository.PositionRespository;

import com.university.fpt.acf.service.PositionService;


import com.university.fpt.acf.vo.PositionResponseVO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@SpringBootTest
class PositionServiceTest {
    @Autowired
    private PositionService underTest;

    @Test
    void searchPosition() {
        //given
        PositionForm positionForm = new PositionForm();
        positionForm.setName("a");
        positionForm.setPageIndex(1);
        positionForm.setPageSize(10);
        //when
        List<PositionResponseVO> listExpect =underTest.searchPosition(positionForm);
        // then
        assertThat(listExpect.size()!=0).isTrue();
    }

    @Test
    void totalSearchPosition() {
        //given
        PositionForm positionForm = new PositionForm();
        positionForm.setName("a");
        positionForm.setPageIndex(1);
        positionForm.setPageSize(10);
        //when
        Integer totalExpected =underTest.totalSearchPosition(positionForm);
        // then
        assertThat(totalExpected!=0).isTrue();
    }

    @Test
    void addPosition() {
        //given
        AddPositionForm positionForm = new AddPositionForm();
        positionForm.setCode("BV");
        positionForm.setName("Bảo vệ");
        //when
        Boolean expected = underTest.addPosition(positionForm);
        //then
        assertThat(expected).isTrue();
    }

    @Test
    void updatePosition() {
        //given
        UpdatePositionForm updatePositionForm = new UpdatePositionForm();
        updatePositionForm.setId(321l);
        updatePositionForm.setCode("BV");
        updatePositionForm.setName("Bao Ve");
        //when
        Boolean expected = underTest.updatePosition(updatePositionForm);
        //then
        assertThat(expected).isTrue();

    }

    @Test
    void deletePosition() {
        //given
        Long id = 321l;
        //when
        Boolean expected = underTest.deletePosition(id);
        // then
        assertThat(expected).isTrue();

    }
}