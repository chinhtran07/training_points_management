package com.tps.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "content")
    private String content;
//
//    @Size(max = 255)
//    @Column(name = "image")
//    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    private Instant createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private Instant updatedDate;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private Set<Like> likes = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
    private List<Image> images;
}