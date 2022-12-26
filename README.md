# Oracle-Spatial-API
Sample project for test the spatial APIs using Oracle database



## Endpoints

- `/port/add` -> to add the location 

Request method - POST 

Request body: 
{
    "latitude": 24.8092928,
    "longitude": 46.6187138,
    "name": "Your location name"
}



- `/port/near-by-ports` -> to get the nearby location data 


Request method - POST

Request body: 

{
    "latitude": 24.8092928,
    "longitude": 46.6187138,
    "rangeInMeters": 100
}


