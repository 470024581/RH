package com.pro.warehouse.dao;

import com.pro.warehouse.pojo.RhBom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RhBomRepository extends JpaRepository<RhBom, Long> {
    /*List<RhBom> findRhBomBymaterialCode(String materialCode);
    List<RhBom> findRhBomByEnterCode(String enterCode);
    List<RhBom> findRhBomByid(Long id);
   // List<RhBom> findByEnterCodeAndMaterialCode(String enterCode,String materialCode);
    @Query("SELECT entrepot FROM RhBom entrepot where entrepot.enterCode = :enterCode and materialCode like :materialCode")
    List<RhBom> findByEnterCodeAndMaterialCode(@Param("enterCode") String enterCode,@Param("materialCode") String materialCode);

    @Query("SELECT entrepot FROM RhBom entrepot where entrepot.entranceDate <= :entranceDate and goodsStatus='良品'")
    List<RhBom> findBeforeDate(@Param("entranceDate") Date date);

    @Query("select entrepot from RhBom entrepot")
    List<RhBom> getTotalSize();*/
}
