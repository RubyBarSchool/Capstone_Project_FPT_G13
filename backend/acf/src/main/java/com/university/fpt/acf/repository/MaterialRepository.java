package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Material;
import com.university.fpt.acf.vo.MaterialVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material,Long> {
    @Query("select new com.university.fpt.acf.vo.MaterialVO( m.id,m.name,p.id,concat(f.frameLength,'x',f.frameWidth,'x',h.frameHeight),g.id,g.name,c.id,c.name,u.id,u.name,p.price ) from Material m left join m.priceMaterials p inner join m.groupMaterial g inner join " +
            "m.company c inner join p.heightMaterial h inner join p.unitMeasure u inner join p.frameMaterial f where m.checkMaterial=true ")
    MaterialVO get();
}
