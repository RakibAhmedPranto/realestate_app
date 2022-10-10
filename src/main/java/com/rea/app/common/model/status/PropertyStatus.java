package com.rea.app.common.model.status;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PropertyStatus {
    PUBLISHED("PUBLISHED"),
    UNPUBLISHED("UNPUBLISHED"),
    DELETED("DELETED");

    public final String name;
}
