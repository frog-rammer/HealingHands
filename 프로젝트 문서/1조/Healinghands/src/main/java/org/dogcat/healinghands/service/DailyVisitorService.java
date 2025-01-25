package org.dogcat.healinghands.service;

import java.time.LocalDate;

public interface DailyVisitorService {
    void incrementVisitorCount(String ipAddress);
    int getVisitorCount(LocalDate date);
}
