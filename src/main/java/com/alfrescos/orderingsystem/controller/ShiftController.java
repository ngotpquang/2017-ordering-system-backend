package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.entity.Shift;
import com.alfrescos.orderingsystem.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Liger on 19-Mar-17.
 */
@RestController
@RequestMapping(value = "/api/shift")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Shift shift){
        Shift newShift = this.shiftService.create(shift);
        if (newShift != null){
            return new ResponseEntity<Object>("Shift was created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<Object>("Can't created due to some error", HttpStatus.NOT_ACCEPTABLE);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@Valid @RequestBody Shift shift){
        System.out.println(shift.getId());
        Shift newShift = this.shiftService.findById(shift.getId());
        if (newShift != null){
            newShift = this.shiftService.update(shift);
            return new ResponseEntity<Object>("Shift has id: " + newShift.getId() + " was updated successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<Object>("Can't created due to some error", HttpStatus.NOT_ACCEPTABLE);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping(value = "/delete/{shiftId}")
    public ResponseEntity<?> delete(@PathVariable Long shiftId){
        Shift newShift = this.shiftService.findById(shiftId);
        if (newShift != null){
            this.shiftService.delete(shiftId);
            return new ResponseEntity<Object>("Shift has id: " + shiftId + " was deleted successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<Object>("Can't delete due to some error", HttpStatus.NOT_ACCEPTABLE);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'STAFF')")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllShifts(){
        return new ResponseEntity<Object>(this.shiftService.findAll(), HttpStatus.OK);
    }

}
