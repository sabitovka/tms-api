package io.sabitovka.tms.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * Сущность Комментарий
 */
@Getter
@Setter
@Entity
@Table(name = "comments")
@DynamicInsert
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "task_id")
    private Long taskId;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @MapsId("authorId")
    private User author;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @MapsId("taskId")
    private Task task;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Comment comment = (Comment) o;
        return getId() != null && Objects.equals(getId(), comment.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
