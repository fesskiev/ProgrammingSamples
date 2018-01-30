package com.fesskiev.architecturecomponents.domain.entity;


import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

public abstract class DBEntity {

    @NonNull
    @PrimaryKey()
    protected String id = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
