package com.university.fpt.acf.productionorder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import com.university.fpt.acf.form.AddProductForm;
import com.university.fpt.acf.service.impl.ProductServiceImpl;
import com.university.fpt.acf.vo.ProductVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class ProductServiceTest {
    private ProductServiceImpl underTest;
    @Test
    void deleteProductInContact() {
        //given
        Long id =1l;
        //when
        Boolean expected = underTest.deleteProductInContact(id);
        //then
        assertThat(expected).isTrue();
    }
    @Test
    void deleteProductInContactFail() {
        //given
        Long id =1l;
        //when
        Boolean expected = underTest.deleteProductInContact(id);
        //then
        assertThat(expected).isFalse();
    }

    @Test
    void getProductInContact() {
        //given
        Long id =1l;
        //when
        List<ProductVO> expectedList =underTest.getProductInContact(id);
        //then
        assertThat(expectedList.size()!=0).isTrue();
    }

    @Test
    void getProductInContactAll() {
        //given
        Long id =1l;
        //when
        List<ProductVO> expectedList =underTest.getProductInContactAll(id);
        //then
        assertThat(expectedList.size()!=0).isTrue();
    }
}