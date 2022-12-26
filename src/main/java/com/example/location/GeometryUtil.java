package com.example.location;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.util.GeometricShapeFactory;

public class GeometryUtil {

    public static final int SRID = 4326; //LatLng
    private static WKTReader wktReader = new WKTReader();


    // Example: Geometry geometry = GeometryUtil.wktToGeometry(String.format("POINT (13.0827 80.2707)"));
    private static Geometry wktToGeometry(String wellKnownText) {
        Geometry geometry = null;

        try {
            geometry = wktReader.read(wellKnownText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("###geometry :"+geometry);
        return geometry;
    }
    public static Point parseLocation(double x, double y) {
        Geometry geometry = GeometryUtil.wktToGeometry(String.format("POINT (%s %s)",x,y));
        Point p =(Point)geometry;
        p.setSRID(SRID);
        return p;
    }

    private static Geometry createCircle(double x, double y, final double RADIUS) {

        double latitude = 110.54;
        double longitude = 111.320;

        //TODO beter accuracy
        double radiusInDegreesLatitude = RADIUS / latitude;
        double radiusInDegreesLongitude = RADIUS / Math.abs((longitude * Math.cos(latitude)));

        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setNumPoints(1000);
        shapeFactory.setHeight(radiusInDegreesLatitude * 2);
        shapeFactory.setWidth(radiusInDegreesLongitude * 2);
        shapeFactory.setCentre(new Coordinate(x, y));//there are your coordinates
        return shapeFactory.createCircle();
    }

    private static Polygon getPolygonFromCenterPoint(double latitude, double longitude, double diameterInMeters ){

        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setNumPoints(64); // adjustable
        shapeFactory.setCentre(new Coordinate(latitude, longitude));
        // Length in meters of 1° of latitude = always 111.32 km
        shapeFactory.setWidth(diameterInMeters/111320d);
        // Length in meters of 1° of longitude = 40075 km * cos( latitude ) / 360
        shapeFactory.setHeight(diameterInMeters / (40075000 * Math.cos(Math.toRadians(latitude)) / 360));

        return shapeFactory.createEllipse();
    }
}
