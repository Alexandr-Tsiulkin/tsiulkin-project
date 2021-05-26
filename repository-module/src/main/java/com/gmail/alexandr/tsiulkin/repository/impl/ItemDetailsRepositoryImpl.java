package com.gmail.alexandr.tsiulkin.repository.impl;

import com.gmail.alexandr.tsiulkin.repository.ItemDetailsRepository;
import com.gmail.alexandr.tsiulkin.repository.model.ItemDetails;
import org.springframework.stereotype.Repository;

@Repository
public class ItemDetailsRepositoryImpl extends GenericRepositoryImpl<Long, ItemDetails> implements ItemDetailsRepository {
}
