package com.example.location.controller;

import com.example.location.dto.PortDTO;
import com.example.location.service.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/port")
public class PortController {
    @Autowired
    PortService portService;


    @PostMapping("/add")
    public Object add(@RequestBody PortDTO portDTO){
        return portService.save(portDTO);
    }

    @PostMapping("/near-by-ports")
    public Object getAllNearByLocationPorts(@RequestBody PortDTO portDTO){
      return portService.getAllNearByLocationPorts(portDTO);
    }

    @PostMapping("/near-by-neighbour-ports")
    public Object getAllNeighbourNearByLocationPorts(@RequestBody PortDTO portDTO){
      return portService.getAllNearByNeighbourPorts(portDTO);
    }

    @GetMapping("/all")
    public Object getAll(){
        return portService.getAll();
    }
}
