package org.dogcat.healinghands.service;

import org.dogcat.healinghands.entity.DailyVisitor;
import org.dogcat.healinghands.repository.DailyVisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;

@Service
public class DailyVisitorServiceImpl implements DailyVisitorService {

    @Autowired
    private DailyVisitorRepository dailyVisitorRepository;

    @Override
    @Transactional
    public void incrementVisitorCount(String ipAddress) {
        LocalDate today = LocalDate.now();
        DailyVisitor dailyVisitor = dailyVisitorRepository.findByDate(today);

        if (dailyVisitor == null) {
            // 오늘 날짜의 레코드가 없으면 새로 생성
            dailyVisitor = DailyVisitor.builder()
                    .date(today)
                    .visitorCount(1)
                    .visitorIps(new HashSet<>())
                    .build();
            dailyVisitor.getVisitorIps().add(ipAddress);
        } else {
            // 오늘 날짜의 레코드가 있으면 IP 주소 추가
            dailyVisitor.addVisitorIp(ipAddress);
        }

        dailyVisitorRepository.save(dailyVisitor);
    }

    @Override
    public int getVisitorCount(LocalDate date) {
        DailyVisitor dailyVisitor = dailyVisitorRepository.findByDate(date);
        return dailyVisitor != null ? dailyVisitor.getVisitorCount() : 0;
    }
}
