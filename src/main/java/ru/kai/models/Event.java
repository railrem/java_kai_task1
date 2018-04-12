package ru.kai.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 14.03.2018
 * User
 *
 * @version v1.0
 */

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;
}