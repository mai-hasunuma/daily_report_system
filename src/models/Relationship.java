package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "relationships")
@NamedQueries({
    @NamedQuery(
            name = "getFolloewdByMe",
            query = "SELECT r.followed FROM Relationship AS r WHERE r.following = :following"
            ),
})
@Entity
public class Relationship {
    //複合型でのプライマリーキーの指定
    // RelationshipKeyのidを引っ張る

    @EmbeddedId
    @Column(name = "id")
    private RelationshipKey id;

    @ManyToOne
    // MapsIdで複合キーのカラムを指定し、連携している
    @MapsId("followingId")
    // JoinColumnでEmployeeのカラムおの連携
    @JoinColumn(name = "following_id")
    private Employee following;

    @ManyToOne
    @MapsId("followedId")
    @JoinColumn(name =  "followed_id")
    private Employee followed;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name="updated_at")
    private Timestamp updated_at;

    public RelationshipKey getId() {
        return id;
    }

    public void setId(RelationshipKey id) {
        this.id = id;
    }

    public Employee getFollowing() {
        return following;
    }

    public void setFollowing(Employee follwing) {
        this.following = follwing;
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
