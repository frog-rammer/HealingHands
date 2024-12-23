package org.dogcat.healinghands.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DailyVisitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private int visitorCount;

    @ElementCollection
    @CollectionTable(name = "visitor_ips", joinColumns = @JoinColumn(name = "daily_visitor_id"))
    @Column(name = "ip_address")
    private Set<String> visitorIps = new HashSet<>();


    public void addVisitorIp(String ipAddress) {
        if (!visitorIps.contains(ipAddress)) {
            visitorIps.add(ipAddress);
            visitorCount++;
        }
    }

    @Override
    public String toString() {
        return "DailyVisitor{" +
                "id=" + id +
                ", date=" + date +
                ", visitorCount=" + visitorCount +
                ", visitorIps=" + visitorIps +
                '}';
    }
}
