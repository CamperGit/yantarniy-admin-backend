package ru.ds.yantarniy.admin.backend.telegram.service;

import org.springframework.transaction.annotation.Transactional;
import ru.ds.yantarniy.admin.backend.telegram.model.SendingReport;

public interface SenderService {

    @Transactional
    SendingReport sendMessageToCustomers(byte[] image, String description, boolean onlyAdmins);
}
