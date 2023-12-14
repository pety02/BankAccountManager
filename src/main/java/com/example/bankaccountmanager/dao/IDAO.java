package com.example.bankaccountmanager.dao;

public interface IDAO<Entity, DTO> {
    Entity fromDtoToEntity(final DTO dto);
    DTO fromEntityToDTO(final Entity entity);
}