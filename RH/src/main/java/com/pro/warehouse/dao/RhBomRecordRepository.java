package com.pro.warehouse.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pro.warehouse.pojo.RhBomRecord;

@Repository
public interface RhBomRecordRepository extends JpaRepository<RhBomRecord, Long> {
    /*List<RhBom> findRhBomBymaterialCode(String materialCode);
    List<RhBom> findRhBomByEnterCode(String enterCode);
    List<RhBom> findRhBomByid(Long id);
   // List<RhBom> findByEnterCodeAndMaterialCode(String enterCode,String materialCode);
    @Query("SELECT entrepot FROM RhBom entrepot where entrepot.enterCode = :enterCode and materialCode like :materialCode")
    List<RhBom> findByEnterCodeAndMaterialCode(@Param("enterCode") String enterCode,@Param("materialCode") String materialCode);

    @Query("SELECT entrepot FROM RhBom entrepot where entrepot.entranceDate <= :entranceDate and goodsStatus='良品'")
    List<RhBom> findBeforeDate(@Param("entranceDate") Date date);
     */
    @Query("select sum(record.num) from RhBomRecord record where record.code = :code and record.type = :type ")
    Integer getSum(@Param("code")String code, @Param("type")String type);
}
