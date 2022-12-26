package com.example.location.service;


import com.example.location.GeometryUtil;
import com.example.location.dto.PortDTO;
import com.example.location.entity.JpaPort;
import com.example.location.mapper.PortMapper;
import com.example.location.repository.PortRepository;
import org.geolatte.geom.crs.Unit;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Service
public class PortService {

    @Autowired
    PortRepository portRepository;


    public Object getAllNearByLocationPorts(PortDTO portDTO) {
        List<JpaPort> jpaPortList = portRepository.findAll(filterWithinRadius(portDTO));
        return jpaPortList;
    }
    public Object getAllNearByNeighbourPorts(PortDTO portDTO) {
        List<JpaPort> jpaPortList = portRepository.findAll(getNearByNeighbours(portDTO));

        return jpaPortList;
    }


    public Object save(PortDTO portDTO) {

        Point point = GeometryUtil.parseLocation(portDTO.getLongitude(), portDTO.getLatitude());
        point.setSRID(8307);

        JpaPort jpaPort = new JpaPort();
        jpaPort.setLatitude(portDTO.getLatitude());
        jpaPort.setLongitude(portDTO.getLongitude());
        jpaPort.setLocation(point);
        jpaPort.setName(portDTO.getName());

        return portRepository.save(jpaPort);
    }


    public static Specification<JpaPort> getNearByNeighbours(PortDTO portDTO) {
        return new Specification<JpaPort>() {
            @Override
            public Predicate toPredicate(Root<JpaPort> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                GeometryFactory factory = new GeometryFactory();


                Geometry comparisonPoint = factory.createPoint(new Coordinate(portDTO.getLongitude(), portDTO.getLatitude()));
                comparisonPoint.setSRID(8307);

                Expression<String> expression = builder.function("SDO_NN", String.class,
                        root.get("location"),
                        builder.literal(comparisonPoint),
                        builder.literal("SDO_NUM_RES="+portDTO.getNoOfResults()),
                        builder.literal(1)

                );
                return builder.equal(expression, "TRUE");

            }
        };
    }


    public static Specification<JpaPort> filterWithinRadius(PortDTO portDTO) {
        return new Specification<JpaPort>() {
            @Override
            public Predicate toPredicate(Root<JpaPort> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                GeometryFactory factory = new GeometryFactory();

//                Geometry comparisonPoint = factory.createPoint(new Coordinate(latitude,longitude));
//                comparisonPoint.setSRID(8307);
//                SDOParameterMap sdoParameterMap = new SDOParameterMap();
//                sdoParameterMap.setDistance(radius);
//                sdoParameterMap.setUnit("KM");

                Geometry comparisonPoint = factory.createPoint(new Coordinate(portDTO.getLongitude(), portDTO.getLatitude()));
                comparisonPoint.setSRID(8307);


//                String dbGeometry = "SDO_GEOMETRY(" +
//                        "    2001," +
//                        "    8307," +
//                        "    SDO_POINT_TYPE("
//                        + 24.817768 +
//                        46.599417 +
//                        ", NULL)," +
//                        "    NULL," +
//                        "    NULL" +
//                        "   )";



                //     Expression<Geometry> dbGeometry = builder.function("geography", Geometry.class, root.get("location"));

//                SDOParameterMap sdoParameterMap = new SDOParameterMap();
//                sdoParameterMap.setDistance((double) portDTO.getRangeInMeters());
//                sdoParameterMap.setUnit("KM");

                Expression<String> expression = builder.function("SDO_WITHIN_DISTANCE", String.class,
                        root.get("location"),
                        builder.literal(comparisonPoint)
                        , builder.literal("DISTANCE="+portDTO.getRangeInMeters()+" UNIT="+ Unit.METER.getName())
                );
                return builder.equal(expression, "TRUE");

            }
        };
    }


    public Object getAll() {
        List<JpaPort> portList= portRepository.findAll();

       return PortMapper.INSTANCE.toDto(portList);
    }
}
