package com.company.musicstorerecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "label_recommendations")
public class LabelRecommendations {

    @Id
    @Column(name = "labelRecommendations_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "labelid cannot be null.")
    @Column(name = "label_id")
    private Integer labelId;
    @NotNull(message = "user is cannot be null.")
    @Column(name = "user_id")
    private Integer userId;
    @NotNull(message = "boolean cannot be null.")
    private boolean liked;

    public LabelRecommendations() {
    }

    public LabelRecommendations(Integer labelId, Integer userId, boolean liked) {
        this.labelId = labelId;
        this.userId = userId;
        this.liked = liked;
    }

    public LabelRecommendations(Integer id, Integer labelId, Integer userId, boolean liked) {
        this.id = id;
        this.labelId = labelId;
        this.userId = userId;
        this.liked = liked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabelRecommendations that = (LabelRecommendations) o;
        return liked == that.liked && Objects.equals(id, that.id) && Objects.equals(labelId, that.labelId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, labelId, userId, liked);
    }

    @Override
    public String toString() {
        return "LabelRecommendations{" +
                "id=" + id +
                ", labelId=" + labelId +
                ", userId=" + userId +
                ", liked=" + liked +
                '}';
    }
}
