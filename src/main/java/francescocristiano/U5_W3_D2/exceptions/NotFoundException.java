package francescocristiano.U5_W3_D2.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {

    public NotFoundException(UUID id) {
        super("Entity with id " + id + " not found");
    }

    public NotFoundException(String username) {
        super("Record with username " + username + " not found");
    }
}
