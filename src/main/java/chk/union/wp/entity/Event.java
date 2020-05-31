package chk.union.wp.entity;

import chk.union.wp.enums.CableType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private String userName;

    @Column
    private Instant dateStart;

    @Column
    private Instant dateEnd;

    @Column
    private String comment;

    @Column
    private String telephone;

    @Column
    @Enumerated
    private CableType cable;

    @Column
    private Boolean approved = false;
}
