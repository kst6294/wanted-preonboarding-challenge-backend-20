package market.market.global.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate createdAt = LocalDate.now();
}
