package com.gmail.alexandr.tsiulkin.service.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class DeleteUserDTO {
    @NotNull
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
