package com.navi.notes.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "note")
public class Note {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
    @Column(name = "status")
    private char status;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.status = 'A';
    }

    public void setStatus(char status) {
        this.status = status;
    }
}

