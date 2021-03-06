package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "relationships")
@NamedQueries({
    @NamedQuery(
            name = "getFolloewdByMe",
            query = "SELECT r.followed FROM Relationship AS r WHERE r.following = :following"
            ),
    @NamedQuery(
            name = "followingJudgement",
            query = "SELECT r from Relationship AS r WHERE r.following = :following AND r.followed = :followed"
            ),
})
@Entity
public class Relationship {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private Employee following;

    @ManyToOne
    @JoinColumn(name =  "followed_id")
    private Employee followed;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name="updated_at")
    private Timestamp updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getFollowing() {
        return following;
    }

    public void setFollowing(Employee following) {
        this.following = following;
    }

    public Employee getFollowed() {
        return followed;
    }

    public void setFollowed(Employee followed) {
        this.followed = followed;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }


}
