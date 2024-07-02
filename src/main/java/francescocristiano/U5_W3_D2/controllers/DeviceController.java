package francescocristiano.U5_W3_D2.controllers;


import francescocristiano.U5_W3_D2.entities.Device;
import francescocristiano.U5_W3_D2.exceptions.BadRequestException;
import francescocristiano.U5_W3_D2.payloads.NewAssignmentDTO;
import francescocristiano.U5_W3_D2.payloads.NewDeviceDTO;
import francescocristiano.U5_W3_D2.payloads.NewUpdateDeviceDTO;
import francescocristiano.U5_W3_D2.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Device> getDevices(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return deviceService.findAllDevices(page, size, sortBy);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Device saveDevice(@RequestBody @Validated NewDeviceDTO devicePayload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return deviceService.saveDevice(devicePayload);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Device getDeviceById(@PathVariable UUID id) {
        return deviceService.findDeviceById(id);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeviceById(@PathVariable UUID id) {
        deviceService.deleteDeviceById(id);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Device updateDeviceById(@PathVariable UUID id, @RequestBody @Validated NewUpdateDeviceDTO devicePayload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return deviceService.findDeviceByIdAndUpdate(id, devicePayload);
    }

    @PatchMapping("/{id}/assignment")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Device assignDeviceToEmployee(@PathVariable UUID id, @RequestBody NewAssignmentDTO assignmentPayload) {
        return deviceService.assignDeviceToEmployee(id, assignmentPayload);
    }


}
