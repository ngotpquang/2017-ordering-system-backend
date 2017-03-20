package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.common.UserUtil;
import com.alfrescos.orderingsystem.entity.Shift;
import com.alfrescos.orderingsystem.entity.User;
import com.alfrescos.orderingsystem.entity.WorkingTime;
import com.alfrescos.orderingsystem.service.ShiftService;
import com.alfrescos.orderingsystem.service.UserService;
import com.alfrescos.orderingsystem.service.WorkingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Liger on 20-Mar-17.
 */
@RestController
@RequestMapping(value = "/api/working-time")
public class WorkingTimeController {

    @Autowired
    private WorkingTimeService workingTimeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShiftService shiftService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, String> data) {
//        User user = this.userService.findById(UserUtil.getIdByAuthorization())
        try {
            Long userId = Long.parseLong(data.get("userId"));
            System.out.println(userId);
            Long shiftId = Long.parseLong(data.get("shiftId"));
            Long date = Long.parseLong(data.get("date"));
            Date workingDate = new Date(date);
            if (UserUtil.checkAdminOrManagerAccount()){
                WorkingTime workingTime = this.workingTimeService.create(new WorkingTime(new Long(1), this.userService.findById(userId), this.shiftService.findById(shiftId), workingDate));
                if (workingTime != null){
                    return new ResponseEntity<Object>("Created successfully!", HttpStatus.CREATED);
                }
            } else {
                WorkingTime workingTime = this.workingTimeService.create(new WorkingTime(new Long(1), this.userService.findById(UserUtil.getIdByAuthorization()), this.shiftService.findById(shiftId), workingDate));
                if (workingTime != null){
                    return new ResponseEntity<Object>("Created successfully!", HttpStatus.CREATED);
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>("Can't create due to some error.", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<Object>("Can't create due to some error.", HttpStatus.NOT_ACCEPTABLE);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody Map<String, String> data){
        try {
            Long workingTimeId = Long.parseLong(data.get("workingTimeId"));
            Long userId = Long.parseLong(data.get("userId"));
            Long shiftId = Long.parseLong(data.get("shiftId"));
            Long date = Long.parseLong(data.get("date"));
            WorkingTime workingTime = this.workingTimeService.findById(workingTimeId);
            User user = this.userService.findById(userId);
            Shift shift = this.shiftService.findById(shiftId);
            if (workingTime != null && user !=  null && shift != null){
                workingTime.setUser(user);
                workingTime.setShift(shift);
                workingTime.setDate(new Date(date));
                workingTime = this.workingTimeService.update(workingTime);
                return new ResponseEntity<Object>("Working time with id: " + workingTime.getId() + " of user has account code: " + user.getAccountCode() + " has been updated successfully.", HttpStatus.OK);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>("Can't update due to some error.", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>("Can't update due to some error.", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping(value = "delete/{workingTimeId}")
    public ResponseEntity<?> delete(@PathVariable Long workingTimeId){
        WorkingTime workingTime = this.workingTimeService.findById(workingTimeId);
        if (workingTime != null){
            this.workingTimeService.delete(workingTimeId);
            return new ResponseEntity<Object>("Working time has id: " + workingTime.getId() + " of user has account code: " + workingTime.getUser().getAccountCode() + " has been deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Can't delete due to some error.", HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/all/user/{userId}")
    public ResponseEntity<?> getAllWorkingTimesByUserId(@PathVariable Long userId){
        User user;
        if (UserUtil.checkAdminOrManagerAccount()){
            user = this.userService.findById(userId);
        } else {
            user = this.userService.findById(UserUtil.getIdByAuthorization());
        }
        if (user != null){
            List<WorkingTime> workingTimeList = this.workingTimeService.findAllByUserId(user.getId());
            return new ResponseEntity<Object>(workingTimeList, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Can't find due to some error.", HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping(value = "/all/date/{date}")
    public ResponseEntity<?> getAllWorkingTimesByDate(@PathVariable String date){
        try {
            System.out.println(date);
            List<WorkingTime> workingTimeList = this.workingTimeService.findAllByDate(date);
            return new ResponseEntity<Object>(workingTimeList, HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>("Can't find due to some error.", HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping(value = "/all/date/{date}/shift/{shiftId}")
    public ResponseEntity<?> getAllWorkingTimesByDateAndShiftId(@PathVariable String date, @PathVariable Long shiftId){
        try {
            List<WorkingTime> workingTimeList = this.workingTimeService.findAllByShiftIdAndDate(shiftId, date);
            return new ResponseEntity<Object>(workingTimeList, HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>("Can't find due to some error.", HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping(value = "/all/shift/{shiftId}")
    public ResponseEntity<?> getAllWorkingTimesByShiftId(@PathVariable Long shiftId){
        try {
            List<WorkingTime> workingTimeList = this.workingTimeService.findAllByShiftId(shiftId);
            return new ResponseEntity<Object>(workingTimeList, HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>("Can't find due to some error.", HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/all/user/{userId}/date/{date}")
    public ResponseEntity<?> getAllWorkingTimesByUserIdAndDate(@PathVariable Long userId, @PathVariable String date){
        System.out.println(userId + " - " + date);
        User user;
        if (UserUtil.checkAdminOrManagerAccount()){
            user = this.userService.findById(userId);
        } else {
            user = this.userService.findById(UserUtil.getIdByAuthorization());
        }
        System.out.println(user.getId() + " - " + date);
        if (user != null){
            List<WorkingTime> workingTimeList = this.workingTimeService.findAllByUserIdAndDate(user.getId(), date);
            return new ResponseEntity<Object>(workingTimeList, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Can't find due to some error.", HttpStatus.NO_CONTENT);
        }
    }
}
