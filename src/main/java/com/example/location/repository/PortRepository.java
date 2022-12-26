package com.example.location.repository;

import com.example.location.entity.JpaPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortRepository extends JpaRepository<JpaPort,Long>, JpaSpecificationExecutor<JpaPort> {


//    @Query(value = "SELECT se FROM ports se WHERE within(se.location, :bounds) = true AND se.latitude LIKE :filter")
//    public List<JpaPort> findAllWithin(@Param("bounds") Geometry bounds, @Param("filter") String titleFilter);

    @Query(value ="SELECT *, ( 6371 * acos( cos( radians(?1) ) * cos( radians(  latitude ) ) * cos( radians( longitude ) - radians(?2) ) + sin(     radians(?1) ) * sin( radians( latitude ) ) ) ) AS distance FROM ports HAVING distance < ?3 ORDER BY distance LIMIT 0 , 20",nativeQuery = true)
    List<JpaPort> findByLocationNear(double lat, double longi, double distance);

}
