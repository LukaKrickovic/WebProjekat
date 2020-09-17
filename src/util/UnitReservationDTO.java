package util;

import model.Id;

public class UnitReservationDTO {
    private Id id;
    private int count;

    public UnitReservationDTO(Id id, int count) {
        this.id = id;
        this.count = count;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
