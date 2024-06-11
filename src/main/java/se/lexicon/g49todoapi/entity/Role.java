package se.lexicon.g49todoapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;
    @Column(nullable = false)
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
