package com.example.location.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;
import javax.persistence.*;


@Entity
@Table(name = "ports")
@Getter @Setter
@NoArgsConstructor
public class JpaPort {
    @Id
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //@Column(columnDefinition = "geometry(Point,4326)")
   //@Type(type="org.hibernate.spatial.GeometryType")
   //@Column(name = "location",columnDefinition="Point")

   // @Type(type="org.hibernate.spatial.GeolatteGeometryType")

    //@JsonSerialize(using = GeometrySerializer.class)
    //@JsonDeserialize(contentUsing = GeometryDeserializer.class)
    private double latitude;
    private double longitude;

    @JsonIgnore
    @Column(name = "location",columnDefinition="SDO_GEOMETRY")
    private Geometry location;

    private String name;

//    private double latitude;
//    private double longitude;

}
