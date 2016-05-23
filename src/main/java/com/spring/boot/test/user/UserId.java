package com.spring.boot.test.user;


import com.spring.boot.test.domain.util.AbstractEntityId;

import java.util.UUID;

public class UserId extends AbstractEntityId<UUID> {

    protected UserId() {
    }

    public UserId(UUID id) {
        super(id);
    }
}
