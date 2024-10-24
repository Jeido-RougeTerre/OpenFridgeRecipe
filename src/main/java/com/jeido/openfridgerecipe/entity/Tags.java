package com.jeido.openfridgerecipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="tags")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "tag_id")
    private UUID id;

    @ManyToMany(mappedBy = "dieteticAlignment")
    private List<Recipes> labelTags;
}
