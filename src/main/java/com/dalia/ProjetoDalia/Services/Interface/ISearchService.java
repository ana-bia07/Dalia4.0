package com.dalia.ProjetoDalia.Services.Interface;

import com.dalia.ProjetoDalia.Model.DTOS.Users.SearchDTO;

import java.util.Optional;

public interface ISearchService {
    public String createSearch(String idUser, SearchDTO searchDTO);
    public Optional<SearchDTO> updateSearch(String idUser, SearchDTO searchDTO);
    public Optional<SearchDTO> getSearchByIdUsers(String idUser);
    public boolean deleteSearch(String idUser);
}
