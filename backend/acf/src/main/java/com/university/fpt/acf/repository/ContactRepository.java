package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Contact;
import com.university.fpt.acf.vo.ContactVO;
import com.university.fpt.acf.vo.GetCreateContactVO;
import com.university.fpt.acf.vo.MaterialInContactDetailVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {

    @Query(" select new com.university.fpt.acf.vo.MaterialInContactDetailVO(prm.id,m.name,CONCAT(fm.frameLength,'x',fm.frameWidth,'x',hm.frameHeight) " +
            " ,gm.name,um.name,cm.name,prm.price,pm.priceAtCreateContact,pm.note,pm.count)  from Contact c " +
            " inner join c.products p  inner join p.productMaterials pm inner join  pm.priceMaterial prm " +
            " inner join prm.material m inner join m.groupMaterial gm inner join m.company cm inner join prm.frameMaterial fm " +
            " inner join prm.heightMaterial hm inner join prm.unitMeasure um where c.deleted = false and  p.id = :idProduct ")
    List<MaterialInContactDetailVO> getMaterialInProduct(@Param("idProduct") Long idProduct);

    @Query(" select new com.university.fpt.acf.vo.ContactVO(c.id,c.name)  from Contact c where c.deleted = false and c.statusDone in (-2,-1,0)")
    List<ContactVO> getContactNotDone();

    @Query("select c from  Contact c where c.id =:id")
    Contact getContactByID(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("delete from Contact c where c.id =:id")
    void deleteContact(@Param("id") Long id);

}
