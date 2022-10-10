package com.rea.app.common.model.status;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CategoryStatus {
    PUBLISHED("PUBLISHED"),
    UNPUBLISHED("UNPUBLISHED"),
    DELETE("DELETE");

    public final String name;
}
