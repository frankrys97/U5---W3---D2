package francescocristiano.U5_W3_D2.payloads;

import jakarta.validation.constraints.NotBlank;

public record NewDeviceDTO(
        @NotBlank(message = "Device type cannot be blank")
        String deviceType
) {
}
