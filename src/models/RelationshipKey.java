package models;

import java.io.Serializable;

import javax.persistence.Embeddable;

//複合キーの定義は　@Embeddable　serializabl　を忘れずに
@Embeddable
public class RelationshipKey implements Serializable {

/*    @Column(name = "following_id");*/
    private Integer followingId;

/*    @Column(name = "followed_id");*/
    private Integer followedId;

    public Integer getFollowingId() {
        return this.followingId;
    }

    public void setFollowingId(Integer followingId) {
        this.followingId = followingId;
    }

    public Integer getFollowedId() {
        return this.followedId;
    }

    public void setFollowedId(Integer followedId) {
        this.followedId = followedId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((followedId == null) ? 0 : followedId.hashCode());
        result = prime * result + ((followingId == null) ? 0 : followingId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RelationshipKey other = (RelationshipKey) obj;
        if (followedId == null) {
            if (other.followedId != null)
                return false;
        } else if (!followedId.equals(other.followedId))
            return false;
        if (followingId == null) {
            if (other.followingId != null)
                return false;
        } else if (!followingId.equals(other.followingId))
            return false;
        return true;
    }

}
