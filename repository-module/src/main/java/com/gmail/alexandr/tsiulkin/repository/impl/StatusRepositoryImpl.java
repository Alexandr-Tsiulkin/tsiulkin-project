package com.gmail.alexandr.tsiulkin.repository.impl;

import com.gmail.alexandr.tsiulkin.repository.StatusRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Status;
import org.springframework.stereotype.Repository;

@Repository
public class StatusRepositoryImpl extends GenericRepositoryImpl<Long, Status> implements StatusRepository {
}
